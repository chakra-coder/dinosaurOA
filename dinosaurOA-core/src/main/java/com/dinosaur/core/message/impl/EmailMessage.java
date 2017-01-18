package com.dinosaur.core.message.impl;

import com.dinosaur.core.message.IMessageFactory;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Date;
import java.util.Properties;

/**
 * 电子邮件消息
 * @Author Alcott Hawk
 * @Date 1/11/2017
 */
public class EmailMessage implements IMessageFactory{

    @Override
    public boolean sendMessage(String content) {
        MailSender();
        return false;
    }

    /**
     * 邮件发送器
     * @return
     */
    private Object MailSender(){
        try {
            Properties pro = new Properties();
            Session mailSession = Session.getInstance(new Properties());
            Message mailMessage = new MimeMessage(mailSession);
            Address from = new InternetAddress("");
            mailMessage.setFrom(from);
            Address to = new InternetAddress("");
            mailMessage.setRecipient(Message.RecipientType.TO,to);
            mailMessage.setSubject("");
            mailMessage.setSentDate(new Date());
            Multipart mainpart = new MimeMultipart();
            BodyPart html = new MimeBodyPart();
            html.setContent("","text/html; charset=utf-8");
            mainpart.addBodyPart(html);
            mailMessage.setContent(mainpart);
            Transport.send(mailMessage);
            return "send success";
        } catch (MessagingException e) {
            return e.getMessage();
        }
    }

}
