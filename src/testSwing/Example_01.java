package testSwing;

import javax.swing.*;
import java.awt.*;

/**
 * 一个最简单的JFrame项目。
 */
public class Example_01 extends JFrame {

    public void CreateJFrame(String title) {
        JFrame jFrame = new JFrame(title); // 顶级窗口。
        Container container = jFrame.getContentPane();
        JLabel jLabel = new JLabel("这是一个JFrame窗体。");
        jLabel.setHorizontalAlignment(SwingConstants.CENTER); // 标签居中。
        container.add(jLabel);
        container.setBackground(Color.white);
        jFrame.setVisible(true); // 显示可见，必须。
        jFrame.setSize(400, 350);
        jFrame.setDefaultCloseOperation(jFrame.EXIT_ON_CLOSE); // 右上角关闭，没有好像也行。
    }

    public static void main(String[] args) {
        new Example_01().CreateJFrame("窗口。");
    }
}
