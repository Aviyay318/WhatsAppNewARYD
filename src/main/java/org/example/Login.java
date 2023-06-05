package org.example;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Login extends MainPanel{
    private JButton login;
    private BufferedImage loginIcon;
    private SendMessage sendMessage;

    public Login(SendMessage sendMessage){
        this.sendMessage = sendMessage;
        createLoginButton();
    }
    public void readImages(){
        super.readImages();
        try {
            this.loginIcon = ImageIO.read(new File("src/res/whatsAppIcon.png"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void createLoginButton() {
        this.login = new JButton("WhatsApp Web");
        this.login.setFont((new Font("arial",Font.BOLD,20)));
        this.login.setBackground(new Color(47,222,81));
        this.login.setForeground(Color.WHITE);
        this.login.setBounds(this.getWidth()/3+65,this.getHeight()/2,200,50);
        this.add(this.login);
        this.login.setVisible(true);
        this.login.addActionListener(e ->{
            initializeWebDriver();
            driver.openWebsite("https://web.whatsapp.com");
            driver.waitingForCodeScan();

            this.setVisible(false);
            this.sendMessage.setVisible(true);
        });
    }
    public void paintComponent(Graphics graphics){
        super.paintComponent(graphics);
        graphics.drawImage(this.loginIcon,this.login.getX(),this.login.getY()-200,200,200,null);

    }
}
