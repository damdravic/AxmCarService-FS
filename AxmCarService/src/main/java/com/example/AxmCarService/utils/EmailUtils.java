package com.example.AxmCarService.utils;



import com.sun.mail.smtp.SMTPTransport;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Properties;

import static com.example.AxmCarService.constants.EmailConstants.*;
@Service
public class EmailUtils {

    public void sendMailWithCode(String code , String email) throws MessagingException {
        Message message = createEmailWithCode(code,email);
        SMTPTransport smtpTransport = (SMTPTransport) getEmailSession().getTransport(SIMPLE_MAIL_TRANSFER_PROTOCOL);
         smtpTransport.connect(GMAIL_SMTP_SERVER,USERNAME,PASSWORD);
         smtpTransport.sendMessage(message,message.getAllRecipients());
         smtpTransport.close();


    }





    private Message createEmailWithCode(String code , String email) throws MessagingException {

        Message message = new MimeMessage(getEmailSession());
        message.setFrom(new InternetAddress(FROM_MAIL));
        message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(email,false));
        //message.setRecipients(Message.RecipientType.CC,InternetAddress.parse(email,false));
        message.setSubject(EMAIL_SUBJECT);
        message.setText("Your code is" + code + " !!!");
        return message;
    }






    private Session getEmailSession() {
        Properties properties = System.getProperties();
        properties.put(SMTP_HOST,GMAIL_SMTP_SERVER);
        properties.put("username",USERNAME);
        properties.put("password",PASSWORD);
        properties.put(SMTP_AUTH,true);
        properties.put(SMTP_PORT,DEFAULT_PORT);
        properties.put(SMTP_STARTTLS_ENABLE,true);
        properties.put(SMTP_STARTTLS_REQUIRED,true);

       return Session.getInstance(properties,null);
    }







}
