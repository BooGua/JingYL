package source;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

public class DataQueue {

    private Deque<VariedDataColor> queue_color = new LinkedList<VariedDataColor>(); // 整合队列，将碎片数据合成整段以给井筒上色。

    private double max_volume = 0; // 井筒最大体积。
    private double current_color_volume = 0; // 颜色部分当前体积。
    private int pre_kind = 0; // 最新加入液体的类型。
    private double blankspace_color = 0; // 剩余空间。
    private int on_off_color = 0; // 循环开关。

    /*
     * 构造函数，构造指定体积的数据队列。
     */
    public DataQueue(double volume) {
        max_volume = volume;
    }

    /*
     * 将碎片化的信息整合为整块信息，方便上色。
     */
    public void attachDataColor(VariedData variedData) {
        // TODO: 需要一套数据完成测试正确性。

        double Q_now = variedData.getQ() / 60;
        int kind_now = variedData.getKind();

        if (Q_now != 0) {
            if (Q_now > max_volume) {
                on_off_color = 1;
                queue_color.clear();
                queue_color.offerFirst(new VariedDataColor(max_volume, kind_now));
            }
            while (on_off_color == 0) {
                if (queue_color.size() == 0) { // 当整合队列为空时。

                    blankspace_color = max_volume;

                    queue_color.offerFirst(new VariedDataColor(Q_now, kind_now)); // 数据加入队列。

                    pre_kind = kind_now; // 改变 pre_kind。

                    current_color_volume = current_color_volume + Q_now; // 改变当前空间。
                    blankspace_color = blankspace_color - Q_now; // 改变剩余空间。
                    on_off_color = 1; // 标识已经加入队列，跳出循环。

//					System.out.println(
//							"【井筒空】当前井筒体积：" + current_color_volume + "，最近加入段体积：" + Q_now + "，blankspace："
//									+ blankspace_color + "，最近加入段类型：" + queue_color.peekFirst().getKind());

                } else { // 当整合队列有数据时。
                    if (current_color_volume == max_volume) { // 井筒满的情况。
                        if (queue_color.peekLast().getVolume() > Q_now) { // 队头体积足够大，修改队头体积，压入数据。

                            queue_color.peekLast().setVolume(queue_color.peekLast().getVolume() - Q_now);
                            if (pre_kind == kind_now) {
                                queue_color.peekFirst()
                                        .setVolume(queue_color.peekFirst().getVolume() + Q_now);
                            } else {
                                queue_color.offerFirst(new VariedDataColor(Q_now, kind_now));
                            }

                            pre_kind = kind_now; // 改变 pre_kind。
                            on_off_color = 1; // 标识已经加入队列，跳出循环。

//							System.out.println("【井筒满，队头足够。】当前井筒体积：" + current_color_volume + "，最近加入段体积："
//									+ Q_now + "，blankspace：" + blankspace_color + "，最近加入段类型："
//									+ queue_color.peekFirst().getKind());

                        } else if (queue_color.peekLast().getVolume() == Q_now) { // 队头体积等于加入体积，弹出后压入。

                            queue_color.pollLast();
                            if (pre_kind == kind_now) {
                                queue_color.peekFirst()
                                        .setVolume(queue_color.peekFirst().getVolume() + Q_now);
                            } else {
                                queue_color.offerFirst(new VariedDataColor(Q_now, kind_now));
                            }

                            pre_kind = kind_now; // 改变 pre_kind。

//							System.out.println("【井筒满，队头等于加入体积。】当前井筒体积：" + current_color_volume
//									+ "，blankspace：" + blankspace_color);

                        } else { // 队头体积小于加入体积，弹出。

                            current_color_volume = current_color_volume - queue_color.peekLast().getVolume(); // 改变当前空间。
                            blankspace_color = blankspace_color + queue_color.peekLast().getVolume(); // 改变剩余空间。

                            queue_color.pollLast();

//							System.out.println("【井筒满，队头小于加入体积。】当前井筒体积：" + current_color_volume
//									+ "，blankspace：" + blankspace_color);

                        }

                    } else { // 当整合队列不满时。
                        if (blankspace_color >= Q_now) { // 空余空间足够。
                            // System.out.println("kindNow:" + kind_now + "，preKind:" + pre_kind);

                            current_color_volume = current_color_volume + Q_now; // 改变当前空间。
                            blankspace_color = blankspace_color - Q_now; // 改变剩余空间。

                            if (pre_kind == kind_now) {
                                queue_color.peekFirst()
                                        .setVolume(queue_color.peekFirst().getVolume() + Q_now);
                            } else {
                                queue_color.offerFirst(new VariedDataColor(Q_now, kind_now));
                            }

                            on_off_color = 1; // 标识已经加入队列，跳出循环。
                            pre_kind = kind_now; // 改变 pre_kind。

//							System.out.println("【井筒不满，剩余空间足够。】当前井筒体积：" + current_color_volume + "，最近加入段体积："
//									+ Q_now + "，blankspace：" + blankspace_color + "，最近加入段类型："
//									+ queue_color.peekFirst().getKind());

                        } else { // 空余空间不够。
                            if (queue_color.peekLast().getVolume() + blankspace_color == Q_now) { // 弹出队头后刚好够。

                                queue_color.pollLast();

                                if (queue_color.size() == 0) {
                                    queue_color.offerFirst(new VariedDataColor(Q_now, kind_now));

                                    pre_kind = kind_now; // 改变 pre_kind。
                                    current_color_volume = max_volume; // 改变当前空间。
                                    blankspace_color = 0; // 改变剩余空间。
                                    on_off_color = 1; // 标识已经加入队列，跳出循环。

//									System.out.println("【井筒不满，弹出队头后刚好够。】当前井筒体积：" + current_color_volume
//											+ "，最近加入段体积：" + Q_now + "，blankspace：" + blankspace_color
//											+ "，最近加入段类型：" + queue_color.peekFirst().getKind());

                                } else {
                                    if (pre_kind == kind_now) {
                                        queue_color.peekFirst()
                                                .setVolume(queue_color.peekFirst().getVolume() + Q_now);
                                    } else {
                                        queue_color.offerFirst(new VariedDataColor(Q_now, kind_now));
                                    }

                                    pre_kind = kind_now; // 改变 pre_kind。
                                    current_color_volume = max_volume; // 改变当前空间。
                                    blankspace_color = 0; // 改变剩余空间。
                                    on_off_color = 1; // 标识已经加入队列，跳出循环。

//									System.out.println("【井筒不满，弹出队头后刚好够。】当前井筒体积：" + current_color_volume
//											+ "，最近加入段体积：" + Q_now + "，blankspace：" + blankspace_color
//											+ "，最近加入段类型：" + queue_color.peekFirst().getKind());

                                }
                            } else if (queue_color.peekLast().getVolume() + blankspace_color > Q_now) {
                                // 完全弹出队头后所剩空间大于加入体积，所以只需弹出部分队头空间即可。

                                queue_color.peekLast().setVolume(
                                        queue_color.peekLast().getVolume() + blankspace_color - Q_now);

                                if (pre_kind == kind_now) {
                                    queue_color.peekFirst()
                                            .setVolume(queue_color.peekFirst().getVolume() + Q_now);
                                } else {
                                    queue_color.offerFirst(new VariedDataColor(Q_now, kind_now));
                                }

                                pre_kind = kind_now; // 改变 pre_kind。
                                current_color_volume = max_volume; // 改变当前空间。
                                blankspace_color = 0; // 改变剩余空间。
                                on_off_color = 1; // 标识已经加入队列，跳出循环。

//								System.out.println("【井筒不满，完全弹出队头后所剩空间大于加入体积。】当前井筒体积：" + current_color_volume
//										+ "，最近加入段体积：" + Q_now + "，blankspace：" + blankspace_color
//										+ "，最近加入段类型：" + queue_color.peekFirst().getKind());

                            } else { // 弹出空间后仍然不够。

                                current_color_volume = current_color_volume
                                        - queue_color.peekLast().getVolume(); // 改变当前空间。
                                blankspace_color = blankspace_color + queue_color.peekLast().getVolume(); // 改变剩余空间。

                                queue_color.pollLast();

//								System.out.println("【井筒不满，完全弹出队头后仍不够。】当前井筒体积：" + current_color_volume
//										+ "，最近加入段体积：" + Q_now + "，blankspace：" + blankspace_color);

                            }
                        }
                    }
                }
            }
            on_off_color = 0;
        } else {
//			System.out.println("加入空结点。");
        }
    }

    public Deque<VariedDataColor> getColorQueue() {
        return queue_color;
    }

    public double getCurrent_color_volume() {
        return current_color_volume;
    }

    public void printQ_color() { // 输出队列。
        Queue<VariedDataColor> queue_temp = new LinkedList<>();
        System.out.print("整合队列状态：");
        while (queue_color.size() != 0) {
            System.out.print("getVolume:" + queue_color.peek().getVolume() + ", getKind:"
                    + queue_color.peek().getKind() + "//");
            queue_temp.offer(queue_color.poll());
        }
        while (queue_temp.size() != 0) {
            queue_color.offer(queue_temp.poll());
        }
        System.out.println();
    }

}