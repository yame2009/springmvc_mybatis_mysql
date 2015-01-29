package com.hb.util.net;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
 
/**
 * 邮件发送工具类
 */
public class SendMail {
    private String hostName;//设置smtp服务器
    private String sendMailAddress;//设置发送地址
    private String mailPassword;//设置密码
    private boolean TLS = false;//设置是否需要TLS登录
    private String[] getMailAddress;//设置接收地址s
    private String mailTitle;//设置标题
    private String mailContent;//设置邮件内容
 
    public  void  send(){
        SimpleEmail email = new SimpleEmail();
        email.setTLS(TLS); //是否TLS校验，，某些邮箱需要TLS安全校验，同理有SSL校验  
        email.setHostName(hostName);
        try {
            email.setFrom(sendMailAddress, sendMailAddress);
            email.setAuthentication(sendMailAddress, mailPassword);
            email.setCharset("utf-8");//解决中文乱码问题
            email.setSubject(mailTitle); //标题       
            email.setMsg(mailContent);//内容  
            for(int i = 0; i < getMailAddress.length; ++i){
                email.addTo(getMailAddress[i]); //接收方
                email.send();
            }
                 
             
        } catch (EmailException e) {
        //  e.printStackTrace();
        }
    }
 
    public String getHostName() {
        return hostName;
    }
 
    public void setHostName(String hostName) {
        this.hostName = hostName;
    }
 
    public String getSendMailAddress() {
        return sendMailAddress;
    }
 
    public void setSendMailAddress(String sendMailAddress) {
        this.sendMailAddress = sendMailAddress;
    }
 
    public String getMailPassword() {
        return mailPassword;
    }
 
    public void setMailPassword(String mailPassword) {
        this.mailPassword = mailPassword;
    }
 
    public boolean isTLS() {
        return TLS;
    }
 
    public void setTLS(boolean tls) {
        TLS = tls;
    }
 
    public String[] getGetMailAddress() {
        return getMailAddress;
    }
 
    public void setGetMailAddress(String[] getMailAddress) {
        this.getMailAddress = getMailAddress;
    }
 
    public String getMailTitle() {
        return mailTitle;
    }
 
    public void setMailTitle(String mailTitle) {
        this.mailTitle = mailTitle;
    }
 
    public String getMailContent() {
        return mailContent;
    }
 
    public void setMailContent(String mailContent) {
        this.mailContent = mailContent;
    }
}