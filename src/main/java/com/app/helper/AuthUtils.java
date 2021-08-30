package com.app.helper;

import java.util.HashMap;
import java.util.Map;

import com.app.config.constant.AppConstant;
import com.app.helper.token.TokenUtils;
import com.app.service.MailService;
import com.fasterxml.jackson.core.JsonProcessingException;

public class AuthUtils {
	private MailService mailService;
	public static String generateAuthInfo(Map<String, Object> payload) throws JsonProcessingException {
		String email = (String) payload.get("email");
		Map<String, Object> information = new HashMap<>();
		String validationCode = RandomUtils.generateValidationCode();
		information.put("payload", payload);
		information.put(AppConstant.VALIDATION_CODE, validationCode);
		System.out.println(validationCode);
		String token = TokenUtils.generateToken(information);
		System.out.println("Send code to + " + email);
		return token;
	}
}
