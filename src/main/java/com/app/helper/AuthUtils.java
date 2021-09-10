package com.app.helper;

import java.util.HashMap;
import java.util.Map;

import com.app.config.constant.AppConstant;
import com.app.helper.token.TokenUtils;
import com.app.service.mail.MailService;
import com.fasterxml.jackson.core.JsonProcessingException;

public class AuthUtils {
	public static String generateAuthInfo(Map<String, Object> payload, String validationCode)
			throws JsonProcessingException {
		Map<String, Object> information = new HashMap<>();
		information.put("payload", payload);
		information.put(AppConstant.VALIDATION_CODE, validationCode);
		String token = TokenUtils.generateToken(information);
		return token;
	}
}
