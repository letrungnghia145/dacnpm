package com.app.config.constant;

import java.util.Optional;

public class AppConstant {
	public static final String VALIDATION_CODE = "code";
	public static final String TOKEN = "token";

	public static class TokenCodePayload {
		public Optional<String> token;
		public Optional<String> code;
	}
}
