package org.example;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Window extends JFrame {
    private Image icon;
    public Window(){
        this.setSize(Constants.WIDTH,Constants.HEIGHT);
        this.setLayout(null);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        setImage();
        this.setIconImage(this.icon);

        SendMessage sendMessagePanel = new SendMessage();
        this.add(sendMessagePanel);

        this.add(new Login(sendMessagePanel));


        this.setVisible(true);
    }

    private void setImage() {
        try {
            this.icon = ImageIO.read(new File(Constants.ICON));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
