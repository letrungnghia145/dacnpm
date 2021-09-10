package com.app.service.mail;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;

import com.app.config.mail.MailConfig;

public interface MailService {
	default void send(String to, String subject, String content) throws MessagingException {
		System.out.println("Sending to " + to + "....");
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.host", MailConfig.HOST_NAME);
		properties.put("mail.smpt.port", MailConfig.SSL_PORT);
		properties.put("mail.smtp.socketFactory.port", MailConfig.SSL_PORT);
		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(MailConfig.APP_EMAIL, MailConfig.APP_PASSWORD);
			}
		});

		MimeMessage message = new MimeMessage(session);
		message.setRecipients(MimeMessage.RecipientType.TO, to);
		message.setSubject(subject);
		message.setContent(content + MailConfig.SIGNATURE, "text/html");

		Transport.send(message);
		System.out.println("Mail has sent to " + to + "!");
	}
}
