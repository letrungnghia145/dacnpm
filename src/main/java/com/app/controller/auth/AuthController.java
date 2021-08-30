package com.app.controller.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.app.config.constant.url.BaseURL;

@RequestMapping(BaseURL.AUTH_BASE_URL)
public interface AuthController {
	@GetMapping("/authorize")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<?> authorize();

	@PostMapping("/check")
	public ResponseEntity<?> checkIfNotRegistered(@RequestBody String email);
}
