package com.app.controller.post;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.app.controller.CRUDController;
import com.app.model.post.Comment;
import com.app.model.post.Post;
import com.app.model.user.User;

public interface PostController extends CRUDController<Post, Long> {
	@GetMapping("/{id}/comments")
	public ResponseEntity<?> getPostComments(@PathVariable Long id, @RequestParam Map<String, String> filters);

	@PostMapping("/{id}/comments")
	public ResponseEntity<?> addPostComment(@PathVariable Long id, @RequestBody Comment comment);

	@PostMapping("/{id}/voters")
	public ResponseEntity<?> addPostVoter(@PathVariable Long id, @RequestBody User voter);

	@GetMapping("/{id}/voters")
	public ResponseEntity<?> getPostVoters(@PathVariable Long id, @RequestParam Map<String, String> filters);

	@DeleteMapping
	public ResponseEntity<?> deleteAllByIds(@RequestBody Map<String, List<Long>> map);
}
