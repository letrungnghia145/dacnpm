package com.app.test;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import com.app.exception.ValidateTokenException;
import com.app.helper.token.TokenUtils;
import com.app.model.user.Role;
import com.app.model.user.User;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;

@Getter
public class Test {

	public static Object name(Function<Integer, ?> func) {
		return func.apply(1);
	}

	public static void test() {
		Object name = name((integer) -> {
			return "asds";
		});
		System.out.println(name);
	}

	public static User createUser() {
		return new User();
	}
//		User user = mapper.convertValue(information.get("user"), User.class);

	public static <R> R testFC(String payload, String inputCode, Function<Map<String, Object>, R> func)
			throws Exception {
		Map<String, Object> information = TokenUtils.getInfomationFromToken(payload);
		String validationCode = (String) information.get("validationCode");
		Long expiredDate = (Long) information.get("expiredDate");
		if (new Date().before(new Date(expiredDate)) && validationCode.equals(inputCode)) {
			return func.apply(information);
		} else {
			throw new ValidateTokenException(!inputCode.equals(validationCode) ? "Invalid code!" : "Expired code!");
		}
	}

	public static void main(String[] args) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		String token = "eyJleHRGF0ZSOTc4ODMCwidmdGlvbkOiI4ODLCJ1c2ImVtYWWm9yYTaG9vLmImZpcnZSI6Ilb24iLCTmFtZSeWVyIiIjo1NTbGUiOmInBhc3IjoiJDJE9sekaXNRbUTU92WSeWx5Ykd1V5M2cW51VmIiwiY3ZERhdGbGwsImaWVkRGbnVsbHBpcmVkI6MTYyM4NzgwFsaWRhNvZGUiI2ODQiVyIjp7lsIjoic0QHlhNvbSIsN0TmFtByZXN0JsYXN0I6Ik1hwiYWdlYsInJv51bGwsN3b3JkJhJDEwNaakxXduenZE4wakd6gwdU5GJ4cy5nRESjBxJlYXRlUiOm511vZGlmF0ZSI619";
		User testFC = Test.testFC(token, "882684", (information) -> {
			User value = mapper.convertValue(information.get("user"), User.class);
			return value;
		});
		System.out.println(testFC.getEmail());
	}
}