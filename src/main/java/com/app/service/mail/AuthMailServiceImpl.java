package com.app.service.mail;

import javax.mail.MessagingException;

import org.springframework.stereotype.Service;

import com.app.config.mail.MailConfig.MailAction;
import com.app.helper.MailContentUtils;

@Service
public class AuthMailServiceImpl implements AuthMailService {

	@Override
	public void sendRegisterMail(String email, String code) throws MessagingException {
		MailAction action = MailAction.REGISTER_NEW_ACCOUNT;
		String content = MailContentUtils.createContent(action, code);
		send(email, action.subject, content);
	}

	@Override
	public void sendValidationCode(String email, String code) throws MessagingException {
		// TODO Auto-generated method stub
		
	}

}
