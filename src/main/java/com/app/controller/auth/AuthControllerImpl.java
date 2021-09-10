package com.app.controller.auth;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.config.constant.AppConstant;
import com.app.exception.AuthenticatedException;
import com.app.helper.AuthUtils;
import com.app.helper.JwtUtils;
import com.app.helper.RandomUtils;
import com.app.helper.token.TokenUtils;
import com.app.model.user.User;
import com.app.model.user.User_;
import com.app.service.mail.AuthMailService;
import com.app.service.user.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
public class AuthControllerImpl implements AuthController {
	@Autowired
	private AuthenticationManager authManager;
	@Autowired
	private UserService service;
	@Autowired
	private AuthMailService mailService;

	@PostMapping("/authenticate")
	public ResponseEntity<?> authenticate(@RequestBody Map<String, Optional<String>> request)
			throws AuthenticatedException {
		String email = request.get(User_.EMAIL).get();
		String password = request.get(User_.PASSWORD).get();
		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(email, password);
		try {
			authManager.authenticate(auth);
		} catch (AuthenticationException e) {
			throw new AuthenticatedException(e.getMessage());
		}
		Map<String, Object> result = new HashMap<>();
		result.put("user", service.getByEmail(email));
		result.put("token", JwtUtils.generateToken(email));
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

	@Override
	public ResponseEntity<?> authorize() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = service.getByEmail(email);
		return ResponseEntity.status(HttpStatus.OK).body(user);
	}

	@PostMapping("/register")
	public ResponseEntity<?> doRegister(@RequestBody Map<String, Object> request)
			throws JsonProcessingException, MessagingException {
		String code = RandomUtils.generateValidationCode();
		mailService.sendRegisterMail((String) request.get("email"), code);
		String token = AuthUtils.generateAuthInfo(request, code);
		return ResponseEntity.status(HttpStatus.OK).body(token);
	}

	@PostMapping("/register/confirm")
	public void confirmRegister(@RequestBody AppConstant.TokenCodePayload request) throws Exception {
		service.doRegister(request.token.orElseThrow(), request.code.orElseThrow());
	}

	@Override
	public ResponseEntity<?> checkIfNotRegistered(String email) {
		return ResponseEntity.status(HttpStatus.OK).body(!service.isExistUser(email));
	}
}
