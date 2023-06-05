package org.example;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class MainPanel extends JPanel {

    private BufferedImage backGround;
    private BufferedImage ayrdIcon;
    protected static WebDriver driver;

    public MainPanel(){
        this.setBounds(0,0,Constants.WIDTH,Constants.HEIGHT);
        this.setLayout(null);
        this.setVisible(true);
        readImages();
    }

    public void readImages() {
        try {
            this.backGround = ImageIO.read(new File(Constants.BACK_GROUND));
            this.ayrdIcon = ImageIO.read(new File(Constants.AYRD_ICON));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void initializeWebDriver(){
        driver = new WebDriver();
    }
    public void paintComponent(Graphics graphics){
        super.paintComponent(graphics);
        graphics.drawImage(this.backGround,0,0,Constants.WIDTH,Constants.HEIGHT,null);
        graphics.drawImage(this.ayrdIcon,0,Constants.HEIGHT-150,100,100,null);
    }

}
