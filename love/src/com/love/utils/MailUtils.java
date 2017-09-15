package com.love.utils;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class MailUtils {
    private static Properties props=new Properties();
    private static String user="15869165643";
    private static String password="19960103tlh661";
    private static String from="15869165643@sina.cn";
    static{
    	props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.host", "smtp.sina.cn");
		props.put("mail.smtp.port", "25");
		props.put("mail.smtp.auth", "true");
		props.put("mail.debug", "true");
    }
    
    public static void sendMail(String to,String content) throws AddressException, MessagingException
    {
    	Session emailSession=Session.getDefaultInstance(props,
    			new Authenticator() {
    		@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(user, password);
			}
		});
    	
    	Message message=new MimeMessage(emailSession);
    	message.setFrom(new InternetAddress(from));
    	message.setRecipient(RecipientType.TO, new InternetAddress(to));

		MimeBodyPart body = new MimeBodyPart();
		body.setContent(content, "text/html;charset=utf-8");
		MimeMultipart m1 = new MimeMultipart();
		m1.addBodyPart(body);
		message.setContent(m1);

		Transport transport = emailSession.getTransport();
		transport.connect();
		transport.sendMessage(message, message.getAllRecipients());
    	
    }
    
}
