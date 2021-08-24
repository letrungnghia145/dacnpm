package com.app.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.exception.AuthenticatedException;
import com.app.helper.JwtUtils;
import com.app.helper.RandomUtils;
import com.app.helper.token.TokenUtils;
import com.app.model.user.User;
import com.app.service.user.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@RestController
public class MainController {
	@Autowired
	private AuthenticationManager authManager;
	@Autowired
	private UserService service;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Getter
	@Setter
	@ToString
	private static class TokenRequestBody {
		String email;
		String password;
	}

	@Getter
	@Setter
	@AllArgsConstructor
	private static class RegisterPayload {
		private String payload;
		private String validationCode;
	}
	@Getter
	@Setter
	@AllArgsConstructor
	private static class DetailsAuth {
		private User user;
		private String token;
	}
	@Getter
	@Setter
	@AllArgsConstructor
	private static class DTO {
		private Long id;
		private String email;
		private String role;
	}

	@PostMapping("/authenticate")
	public ResponseEntity<?> authenticate(@RequestBody TokenRequestBody request) throws AuthenticatedException {
		String email = request.getEmail();
		String password = request.getPassword();
		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(email, password);//wrapper
		try {
			authManager.authenticate(auth);
		} catch (AuthenticationException e) {
			throw new AuthenticatedException(e.getMessage());
		}
		User user = service.getByEmail(email);
		DetailsAuth details = new DetailsAuth(user, JwtUtils.generateToken(email));
		return ResponseEntity.status(HttpStatus.OK).body(details);
	}

	@PostMapping("/register")
	public ResponseEntity<?> doRegister(@RequestBody User user) throws JsonProcessingException {
		Map<String, Object> information = new HashMap<>();

		String validationCode = RandomUtils.generateValidationCode();
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		information.put("user", user);
		information.put("validationCode", validationCode);
		System.out.println(validationCode);
		String token = TokenUtils.generateToken(information);
		return ResponseEntity.status(HttpStatus.OK).body(token);
	}

	@PostMapping("/register/confirm")
	public void confirmRegister(@RequestBody RegisterPayload request) throws Exception {
		service.doRegister(request.getPayload(), request.getValidationCode());
	}
}
