package com.app.controller.comment.v1;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.config.constant.url.BaseURL;
import com.app.controller.comment.CommentController;
import com.app.helper.Mapper;
import com.app.model.post.Comment;
import com.app.model.user.User;
import com.app.model.user.User_;
import com.app.service.comment.CommentService;

@RestController
@RequestMapping(BaseURL.COMMENT_BASE_URL_V1)
public class CommentControllerV1 implements CommentController {
	@Autowired
	private CommentService service;

	@Override
	public ResponseEntity<?> addCommentReply(Long id, Comment reply) {
		Comment result = service.addCommentReply(id, reply);
		return ResponseEntity.status(HttpStatus.CREATED).body(result);
	}

	@Override
	public ResponseEntity<?> getCommentReplies(Long id, Map<String, String> filters) {
		List<Comment> replies = service.getCommentReplies(id, filters);
		return ResponseEntity.status(HttpStatus.OK).body(replies);
	}

	@Override
	public ResponseEntity<?> addCommentVoter(Long id, User voter) {
		User result = service.addCommentVoter(id, voter);
		return ResponseEntity.status(HttpStatus.CREATED).body(result);
	}

	@Override
	public ResponseEntity<?> getCommentVoters(Long id, Map<String, String> filters) {
		List<User> voters = service.getCommentVoters(id, filters);
		List<Map<String, Object>> result = Mapper.toMapValues(voters, User_.ID, User_.FIRST_NAME, User_.LAST_NAME);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

	@Override
	public ResponseEntity<?> getAllObjects(Map<String, String> filters) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<?> getObject(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<?> createObject(Comment dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<?> updateObject(Long id, Comment dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<?> deleteObject(Long id) {
		service.deleteObjectById(id);
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}
}
