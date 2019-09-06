package com.dozenx.common.mail;
/**
 * 发送邮件需要使用的基本信息
 * author by wangfun
 * http://www.5a520.cn 小说520
 */

import java.util.List;
import java.util.Properties;

public class MailSenderInfo {
    public MailSenderInfo (){

    }

    /**
     *
     * @param host server host
     * @param port server port
     * @param userName 邮件中的来者姓名
     * @param password 密码
     * @param fromAddress  来信地址
     */
    public MailSenderInfo (String host,int port,String userName,String password,String fromAddress){
        this.mailServerHost=host;
        this.mailServerPort = port+"";
        this.userName = userName;
        this.password = password;
        this.fromAddress = fromAddress;

    }


    // 发送邮件的服务器的IP和端口    
    private String mailServerHost;

    private String debug="true";

    private String protocol = "smtp";

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    private String mailServerPort = "25";
    // 邮件发送者的地址    
    private String fromAddress;
    // 邮件接收者的地址    
    private String toAddress;
    private List<String> toAddressList;

    public List<String> getToAddressList() {
        return toAddressList;
    }

    public void setToAddressList(List<String> toAddressList) {
        this.toAddressList = toAddressList;
    }

    // 登陆邮件发送服务器的用户名和密码
    private String userName;
    private String password;
    // 是否需要身份验证    
    private boolean validate = true;
    // 邮件主题    
    private String subject;
    // 邮件的文本内容    
    private String content;
    // 邮件附件的文件名    
    private String[] attachFileNames;

    /**
     * 获得邮件会话属性
     */
    public Properties getProperties() {
        Properties p = new Properties();
        p.setProperty("mail.transport.protocol", protocol);// 设置传输协议
        p.put("mail.smtp.host", this.mailServerHost);
        p.put("mail.smtp.port", this.mailServerPort);
        p.put("mail.smtp.auth", validate ? "true" : "false");
        p.put("mail.debug", debug);//便于调试

        if(isSsl()){
            p.put("mail.smtp.ssl.enable", true);//便于调试
            /*
            p.put("mail.smtp.socketFactory.port", "465");
            p.put("mail.smtp.socketFactory.class",
                    "javax.net.ssl.SSLSocketFactory");*/
        }
        return p;
    }

    public String getMailServerHost() {
        return mailServerHost;
    }
    public boolean ssl=false;

    public boolean isSsl() {
        return ssl;
    }

    public void setSsl(boolean ssl) {
        this.ssl = ssl;
    }

    public void setMailServerHost(String mailServerHost) {
        this.mailServerHost = mailServerHost;
    }

    public String getMailServerPort() {
        return mailServerPort;
    }

    public void setMailServerPort(String mailServerPort) {
        this.mailServerPort = mailServerPort;
    }

    public boolean isValidate() {
        return validate;
    }

    public void setValidate(boolean validate) {
        this.validate = validate;
    }

    public String[] getAttachFileNames() {
        return attachFileNames;
    }

    public void setAttachFileNames(String[] fileNames) {
        this.attachFileNames = fileNames;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String textContent) {
        this.content = textContent;
    }

    public String getDebug() {
        return debug;
    }

    public void setDebug(String debug) {
        this.debug = debug;
    }

    public MailSenderInfo clone(){

        MailSenderInfo mailSenderInfo = new MailSenderInfo();
        mailSenderInfo.setMailServerHost(this.getMailServerHost());
        mailSenderInfo.setUserName(this.getUserName());
        mailSenderInfo.setMailServerPort(this.getMailServerPort());
        mailSenderInfo.setPassword(this.getPassword());
        mailSenderInfo.setFromAddress(this.getFromAddress());
        mailSenderInfo.setSsl(this.isSsl());
        mailSenderInfo.setDebug( this.getDebug());

        return mailSenderInfo;
    }
}