package testSwing;

import javax.swing.*;

public class Example_02 extends JFrame {
    Example_02(int x, int y, int h, int w) {
        super("一个简单窗口。");
        setLocation(x, y);
        setSize(h, w);
        setResizable(false); // 窗口大小可以拖拽。
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JLabel jLabel = new JLabel("这是一个JFrame窗体。");
        jLabel.setHorizontalAlignment(SwingConstants.CENTER); // 标签居中。
        this.getContentPane().add(jLabel);

    }

    public static void main(String args[]) {
        //实例化类对象，提示设置参数
        Example_02 f = new Example_02(300, 300, 300, 200);
    }
}