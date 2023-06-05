package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;

public class WebDriver extends ChromeDriver {

    private JLabel dialogMessage;
    private WebElement search;
    private String messageText;
    private String phoneNumber;
    private String status;

    public WebDriver(){
        System.setProperty("webdriver.openqa.driver", "src/drivers/chromedriver.exe");
        this.dialogMessage = new JLabel();
        this.phoneNumber="";
    }

    public void openWebsite(String url){
        this.get(url);
        this.manage().window().maximize();
    }
    public void waitingForCodeScan(){
        while(true){
            try {
                this.search = this.findElement(By.xpath("//*[@id=\"side\"]/div[1]/div/div/div[2]/div/div[1]/p"));
                if (this.search != null){
                    System.out.println("ok");
                    break;
                }
            }catch (Exception e){
                e.getStackTrace();
            }
        }
        JOptionPane.showMessageDialog(this.dialogMessage,"The connection was made successfully");
    }
    public void redirectPhoneNumber(String phoneNumber){
        if (!this.phoneNumber.equals(phoneNumber)){
            this.openWebsite("https://web.whatsapp.com/send?phone="+phoneNumber);
        }
        this.phoneNumber=phoneNumber;
    }

    public boolean sendMessage(String message){
        boolean isSendMessage = false;
        this.messageText = message;
        try {
            WebElement  messageField = this.findElement(By.xpath("//*[@id=\"main\"]/footer/div[1]/div/span[2]/div/div[2]/div[1]"));
            messageField.sendKeys(message);
            messageField.sendKeys(Keys.ENTER);
            deliveredMessage(this);
            isSendMessage=true;
        }catch (Exception e){
            sendMessage(this.messageText);
        }
        return isSendMessage;
    }

    public void deliveredMessage(ChromeDriver chromeDriver) {
        new Thread(() -> {
            boolean read = false;
            boolean sent = false;
            while (!read) {
                List<WebElement> list = chromeDriver.findElements(By.cssSelector("span[data-icon='msg-dblcheck']"));
                try {
                    String lastMessage = getLast(chromeDriver);
                    if (lastMessage == null) {
                        this.status = "sent";
                    } else {
                        if (lastMessage.contains("נמסרה") || lastMessage.contains("Delivered") && !sent) {
                            this.status = "delivered";
                            sent = true;
                        } else if (lastMessage.contains("נקראה")|| lastMessage.contains("Read")) {
                            this.status = "read";
                            read = true;
                        }
                    }
                } catch (Exception e) {
                    e.getStackTrace();
                }
            }
        }).start();
    }
    public String returnStatus(){
        return this.status;
    }
    public String getLast(ChromeDriver chromeDriver) {
        String lastMessage = null;
        List<WebElement> list = null;
        try {
            List<WebElement>  divElements = chromeDriver.findElements(By.cssSelector("div[role='row']"));
            WebElement lastDivElement = divElements.get(divElements.size() - 1);
            list = lastDivElement.findElements(By.cssSelector("span[data-icon='msg-dblcheck']"));
            lastMessage = list.get(list.size() - 1).getAccessibleName();
        } catch (Exception e) {
            return lastMessage;
        }
        return lastMessage;
    }



    public String returnReceivedMessage(){
        String [] array = null;
        while (true) {
            WebElement check = this.findElement(By.xpath("//*[@id=\"main\"]/div[2]/div/div[2]/div[3]"));
            if (check!=null){
                array =  check.getText().split("\n");
                System.out.println(Arrays.toString(array));
                System.out.println(array[array.length-2]);
                break;
            }
        }
        return array[array.length-2];}
}
