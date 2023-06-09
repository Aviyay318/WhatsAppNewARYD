package org.example;

import javax.swing.*;
import java.awt.*;


public class SendMessage extends MainPanel{
    private JLabel mainLabel;
    private JLabel phoneLabel;
    private JLabel status;
    private JTextArea phoneNumbersField;
    private JLabel messageLabel;
    private JTextArea messageField;
    private JTextArea replyField;
    private JButton send;
    private ImageIcon sendIcon;
    private String phoneNumber;
    private JLabel dialogMessage;
    public SendMessage(){
        this.dialogMessage = new JLabel();
        this.setVisible(false);
        addMainLabel();
        addPhoneField();
        addPhoneLabel();
        addMessageField();
        addMessageLabel();
        addSendButton();
        addStatus();
        addReplyFields();
    }

    private void addSendButton() {
        this.send = new JButton();
        this.send.setBounds(this.messageField.getX()-60,this.messageField.getY()+40,100,100);
        this.send.setFont(new Font("arial", Font.BOLD, 30));
        this.sendIcon = new ImageIcon("src/res/send.png");
        this.send.setIcon(this.sendIcon);
        this.send.setContentAreaFilled(false);
        this.send.setBorderPainted(false);
        this.add(this.send);
        this.send.addActionListener(event ->{
            sendMessage();
            setStatus();
        });
    }

    private void setStatus() {
        new Thread(()->{
            while (true){
                this.status.setText(driver.returnStatus());
            }
        }).start();
    }

    private void addStatus() {
        JLabel messageStatus = new JLabel("Message Status");
        messageStatus.setFont(new Font("Arial",Font.BOLD,30));
        messageStatus.setBounds(this.send.getX(),this.send.getY()+180,this.send.getWidth()+500,this.send.getHeight());
        this.add(messageStatus);
        this.status = new JLabel();
        this.status.setFont(new Font("Arial",Font.BOLD,30));
        this.setBackground(new Color(47,222,81));
        this.setForeground(new Color(255,255,255));
        this.status.setBounds(this.send.getX(),messageStatus.getY()+messageStatus.getHeight()+10,this.send.getWidth()+500,this.send.getHeight());
        this.add(this.status);
    }

    private boolean verification() {
        boolean isValid = false;
        this.phoneNumber = this.phoneNumbersField.getText();
        if (isAllDigits()){
            if (this.phoneNumber.length()==10){
                if (this.phoneNumber.substring(0,2).equals("05")){
                    this.phoneNumber="972"+this.phoneNumbersField.getText().substring(1);
                    isValid = true;
                }
            } else if (this.phoneNumber.length()==12) {
                if (this.phoneNumber.substring(0,4).equals("9725")){
                    isValid = true;
                }
            }
        }
        return isValid;
    }

    private boolean isAllDigits(){
        boolean isAllDigits = true;
        for (int i = 0; i < this.phoneNumber.length(); i++) {
            if (!Character.isDigit(this.phoneNumber.charAt(i))) {
                isAllDigits = false;
            }
        }
        return isAllDigits;
    }

    private void sendMessage() {
        if (verification()){
            if (!this.messageField.getText().equals("")){
                driver.redirectPhoneNumber(this.phoneNumber);
                driver.sendMessage(this.messageField.getText());
                applyReplyField();
            }else {
                showMessage("enter a text");
            }
        } else {
            this.phoneNumbersField.setText("");
            showMessage("enter phoneNumber begin in 05 \n only digits");
        }
    }
    private void showMessage(String popMessage){
        JOptionPane.showMessageDialog(this.dialogMessage,popMessage );

    }

    private void addMessageLabel() {
        this.messageLabel = new JLabel("Enter Your Message");
        this.messageLabel.setFont(new Font("arial", Font.BOLD, 20));
        this.messageLabel.setBounds(this.messageField.getX(), this.messageField.getY()-40,400,30);
        this.messageLabel.setForeground(Color.white);
        this.add(this.messageLabel);
        this.messageLabel.setVisible(true);
    }

    private void addMessageField() {
        this.messageField = new JTextArea();
        this.messageField.setFont(new Font("arial", Font.BOLD, 20));
        this.messageField.setBounds(this.phoneNumbersField.getX()+this.phoneNumbersField.getWidth()+20, this.phoneNumbersField.getY(),this.phoneNumbersField.getWidth(),this.phoneNumbersField.getHeight());
        this.add(this.messageField);
        this.messageField.setLineWrap(false);
        this.messageField.setWrapStyleWord(false);
        this.messageField.setVisible(true);
    }

    private void addPhoneLabel() {
        this.phoneLabel = new JLabel("Enter Your Phone");
        this.phoneLabel.setFont(new Font("arial", Font.BOLD, 20));
        this.phoneLabel.setBounds(this.phoneNumbersField.getX(), this.phoneNumbersField.getY()-40,400,30);
        this.phoneLabel.setForeground(Color.white);
        this.add(this.phoneLabel);
        this.phoneLabel.setVisible(true);
    }

    private void addPhoneField() {
        this.phoneNumbersField = new JTextArea();
        this.phoneNumbersField.setFont(new Font("arial", Font.BOLD, 20));
        this.phoneNumbersField.setBounds(this.mainLabel.getX(), this.mainLabel.getY()+this.mainLabel.getHeight(),300,30);
        this.add(this.phoneNumbersField);
        this.phoneNumbersField.setLineWrap(true);
        this.phoneNumbersField.setWrapStyleWord(true);
        this.phoneNumbersField.setVisible(true);
    }

    private void addMainLabel() {
        this.mainLabel = new JLabel("A.Y.R.D message interface");
        this.mainLabel.setFont(new Font("arial", Font.BOLD, 50));
        this.mainLabel.setBounds(Constants.WIDTH/6, Constants.WIDTH/30,700,200);
        this.mainLabel.setForeground(Color.WHITE);
        this.add(this.mainLabel);
        this.mainLabel.setVisible(true);
    }

    private void addReplyFields() {
        this.replyField = new JTextArea();
        this.replyField.setFont(new Font("arial", Font.BOLD, 20));
        this.replyField.setBounds(this.phoneNumbersField.getX(),this.getHeight()-100,600,50);
        this.add(this.replyField);
        this.replyField.setLineWrap(true);
        this.replyField.setWrapStyleWord(true);
        this.replyField.setVisible(false);

    }
    private void applyReplyField(){
        new Thread(()->{
            while (true){
                if(driver.getLast(driver)!=null){
                    this.replyField.setText(driver.returnReceivedMessage());
                    this.replyField.setVisible(true);
                    break;
                }
            }
        }).start();
    }
}
