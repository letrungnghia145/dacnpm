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

	public static void main(String[] args) {
		String input = "not:[a,b,c]";
		
		boolean check = input.contains("[")&&input.contains("not");
		if (check) {
			
		}
		System.out.println(check);
		
	}
}