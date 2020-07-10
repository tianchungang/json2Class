package com.ctx.lighting;

import com.intellij.openapi.ui.Messages;

import javax.swing.*;

public class JsonEditForm {
    private JButton confirm;
    private JTextField jsonClassNameTextField;
    private JPanel panel;
    private JFrame frame;
    private ICallBack callBack;

    public JsonEditForm() {

        confirm.addActionListener(e -> {
            if ((jsonClassNameTextField.getText() == null||jsonClassNameTextField.getText().trim().length()==0)){
                Messages.showMessageDialog("pls input the class name", "warn", Messages.getInformationIcon());
            }else{
                if(callBack != null){
                    callBack.generate(jsonClassNameTextField.getText());
                }
                if(this.frame != null){
                    this.frame.dispose();
                }
            }
        });
    }

    public void setCallBack(ICallBack callBack){
        this.callBack = callBack;
    }



    public void showFrame(){
        frame = new JFrame();
        frame.setContentPane(this.panel);
//        frame.setUndecorated(true); // 去掉窗口的装饰
//        frame.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG );
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setSize(350,150);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new JsonEditForm().showFrame();
    }

}
