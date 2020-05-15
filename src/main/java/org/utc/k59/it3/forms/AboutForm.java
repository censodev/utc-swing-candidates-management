package org.utc.k59.it3.forms;

import javax.swing.*;
import java.awt.*;

public class AboutForm {

    public JPanel Mainframe1;
    private JButton quyềnĐứcCôngButton;
    private JButton bùiViệtPhươngButton;
    private JButton nguyễnHảiĐăngButton;

    public  AboutForm(){


    }

    public static void main(String[] args) {
        JFrame jFrame = new JFrame("About us");
        jFrame.setContentPane(new AboutForm().Mainframe1);
        jFrame.pack();
        jFrame.setSize(450,200);
        jFrame.setVisible(true);
        jFrame.setLocationRelativeTo(null);
    }


    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
