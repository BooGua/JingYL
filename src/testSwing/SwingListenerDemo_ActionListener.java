package testSwing;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SwingListenerDemo_ActionListener {
    private JFrame mainFrame;
    private JLabel headerLabel;
    private JLabel statusLabel;
    private JPanel controlPanel;

    public SwingListenerDemo_ActionListener() {
        prepareGUI();
    }

    public static void main(String[] args) {
        SwingListenerDemo_ActionListener swingListenerDemo = new SwingListenerDemo_ActionListener();
        swingListenerDemo.showActionListenerDemo();
    }

    private void prepareGUI() {
        mainFrame = new JFrame("Java SWING Examples");
        mainFrame.setSize(400, 400);
        mainFrame.setLayout(new GridLayout(3, 1));

        headerLabel = new JLabel("", JLabel.CENTER);
        statusLabel = new JLabel("", JLabel.CENTER);

        statusLabel.setSize(350, 100);
        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });
        controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

        mainFrame.add(headerLabel);
        mainFrame.add(controlPanel);
        mainFrame.add(statusLabel);
        mainFrame.setVisible(true);
    }

    private void showActionListenerDemo() {
        headerLabel.setText("Listener in action: ActionListener");

        JPanel panel = new JPanel();
        panel.setBackground(Color.magenta);

        JButton okButton = new JButton("OK");

        okButton.addActionListener(new CustomActionListener());
        panel.add(okButton);
        controlPanel.add(panel);
        mainFrame.setVisible(true);
    }

    class CustomActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            statusLabel.setText("Ok Button Clicked.");
        }
    }
}