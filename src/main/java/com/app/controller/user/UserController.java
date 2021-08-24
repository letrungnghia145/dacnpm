package com.app.controller.user;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.app.controller.CRUDController;
import com.app.model.post.Post;
import com.app.model.user.User;

public interface UserController extends CRUDController<User, Long> {
	@GetMapping("/profile")
	public ResponseEntity<?> getUserProfile();

	@PostMapping("/{id}/posts/shared")
	public ResponseEntity<?> addUserSharedPost(@PathVariable Long id, @RequestBody Post post);

	@GetMapping("/{id}/posts/shared")
	public ResponseEntity<?> getUserSharedPosts(@PathVariable Long id, @RequestParam Map<String, String> filters);
}
