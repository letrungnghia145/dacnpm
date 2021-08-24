package com.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.repository.user.UserRepository;
import com.app.service.post.PostService;
import com.app.test.Test;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class TestController {
	@Autowired
	private ObjectMapper mapper;
	@Autowired
	private PostService service;
	@Autowired
	private UserRepository repository;
	
	

	@GetMapping("/test")
	public ResponseEntity<?> testGetWithId(@RequestBody Test test) {
		return ResponseEntity.status(HttpStatus.OK).body(test);
	};

//	pm.globals.set("randomUserId", _.random(1, 5));
//	pm.globals.set("randomTagId", _.random(1, 5));
}
