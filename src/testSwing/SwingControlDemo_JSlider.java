package testSwing;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

/**
 * JSlider 类是一个组件，它可以让用户以图形方式选择一个值，有界区间内通过滑动旋钮。
 */
public class SwingControlDemo_JSlider {

    private JFrame mainFrame;
    private JLabel headerLabel;
    private JLabel statusLabel;
    private JPanel controlPanel;

    public SwingControlDemo_JSlider() {
        prepareGUI();
    }

    public static void main(String[] args) {
        SwingControlDemo_JSlider swingControlDemo = new SwingControlDemo_JSlider();
        swingControlDemo.showSliderDemo();
    }

    private void prepareGUI() {
        mainFrame = new JFrame("Java Swing Examples");
        mainFrame.setSize(400, 400);
        mainFrame.setLayout(new GridLayout(3, 1));
        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });
        headerLabel = new JLabel("", JLabel.CENTER);
        statusLabel = new JLabel("", JLabel.CENTER);

        statusLabel.setSize(350, 100);

        controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

        mainFrame.add(headerLabel);
        mainFrame.add(controlPanel);
        mainFrame.add(statusLabel);
        mainFrame.setVisible(true);
    }

    private void showSliderDemo() {
        headerLabel.setText("Control in action: JSlider");
        JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 100, 10);
        slider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                statusLabel.setText("Value : "
                        + ((JSlider) e.getSource()).getValue());
            }
        });
        controlPanel.add(slider);
        mainFrame.setVisible(true);
    }
}