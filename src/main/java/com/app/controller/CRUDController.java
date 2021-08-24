package com.app.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface CRUDController<BO, ID> {

	@GetMapping
	public ResponseEntity<?> getAllObjects(@RequestParam Map<String, String> filters);

	@GetMapping("/{id}")
	public ResponseEntity<?> getObject(@PathVariable ID id);

	@PostMapping
	public ResponseEntity<?> createObject(@RequestBody BO dto);

	@PutMapping("/{id}")
	public ResponseEntity<?> updateObject(@PathVariable ID id, @RequestBody BO dto);

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteObject(@PathVariable ID id);

}
