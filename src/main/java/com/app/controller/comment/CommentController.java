package com.app.controller.comment;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.app.controller.CRUDController;
import com.app.model.post.Comment;
import com.app.model.user.User;

public interface CommentController extends CRUDController<Comment, Long>{
	@PostMapping("/{id}/replies")
	public ResponseEntity<?> addCommentReply(@PathVariable Long id, @RequestBody Comment reply);

	@GetMapping("/{id}/replies")
	public ResponseEntity<?> getCommentReplies(@PathVariable Long id, @RequestParam Map<String, String> filters);

	@PostMapping("/{id}/voters")
	public ResponseEntity<?> addCommentVoter(@PathVariable Long id, @RequestBody User voter);

	@GetMapping("/{id}/voters")
	public ResponseEntity<?> getCommentVoters(@PathVariable Long id, @RequestParam Map<String, String> filters);
}
