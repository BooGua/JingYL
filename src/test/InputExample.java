package test;

import com.jme3.app.SimpleApplication;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;

import javax.swing.*;

/**
 * 通过Swing的对话框获得用户输入
 */
public class InputExample extends SimpleApplication implements ActionListener {

    @Override
    public void simpleInitApp() {
        // 按1键开始输入，键盘交互。
        inputManager.addMapping("Input", new KeyTrigger(KeyInput.KEY_1));
        inputManager.addListener(this, "Input");
    }

    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
        if (isPressed && "Input".equals(name)) {
            // 获得用户输入
            String result = JOptionPane.showInputDialog("请输入：");
            System.out.println(result);
        }
    }

    public static void main(String[] args) {
        InputExample app = new InputExample();
        app.start();
    }

}