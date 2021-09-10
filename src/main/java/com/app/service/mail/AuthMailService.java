package com.app.service.mail;

import javax.mail.MessagingException;

public interface AuthMailService extends MailService {
	public void sendRegisterMail(String email, String code) throws MessagingException;

	public void sendValidationCode(String email, String code) throws MessagingException;
}
