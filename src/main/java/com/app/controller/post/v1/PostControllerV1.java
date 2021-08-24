package com.app.controller.post.v1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.config.constant.url.BaseURL;
import com.app.controller.post.PostController;
import com.app.model.post.Comment;
import com.app.model.post.Post;
import com.app.model.user.User;
import com.app.model.user.User_;
import com.app.service.post.PostService;

@RequestMapping(BaseURL.POST_BASE_URL_V1)
@RestController
public class PostControllerV1 implements PostController {
	@Autowired
	private PostService service;

	@Override
	public ResponseEntity<?> getAllObjects(Map<String, String> filters) {
		List<Post> posts = service.getAllObjects(filters);
		return ResponseEntity.status(HttpStatus.OK).body(posts);
	}

	@Override
	public ResponseEntity<?> getObject(Long id) {
		Post post = service.getObjectById(id);
		return ResponseEntity.status(HttpStatus.OK).body(post);
	}

	@Override
	public ResponseEntity<?> createObject(Post dto) {
		Post post = service.createObject(dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(post);
	}

	@Override
	public ResponseEntity<?> updateObject(Long id, Post dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<?> deleteObject(Long id) {
		service.deleteObjectById(id);
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}

	@Override
	public ResponseEntity<?> getPostComments(Long id, Map<String, String> filters) {
		List<Comment> comments = service.getPostComments(id, filters);
		return ResponseEntity.status(HttpStatus.OK).body(comments);
	}

	@Override
	public ResponseEntity<?> addPostComment(Long id, Comment comment) {
		Comment result = service.addPostComment(id, comment);
		return ResponseEntity.status(HttpStatus.CREATED).body(result);
	}

	@Override
	public ResponseEntity<?> addPostVoter(Long id, User voter) {
		User result = service.addPostVoter(id, voter);
		return ResponseEntity.status(HttpStatus.CREATED).body(result);
	}

	@Override
	public ResponseEntity<?> getPostVoters(Long id, Map<String, String> filters) {
		List<User> voters = service.getPostVoters(id, filters);
		List<Map<String, Object>> result = new ArrayList<>();
		voters.forEach(voter -> {
			Map<String, Object> map = new HashMap<>();
			map.put(User_.ID, voter.getId());
			map.put("name", voter.getFirstName() + " " + voter.getLastName());
			result.add(map);
		});
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	
	@Override
	public ResponseEntity<?> deleteAllByIds(Map<String, List<Long>> map) {
		service.deleteAllById(map.get("ids"));
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}
}
