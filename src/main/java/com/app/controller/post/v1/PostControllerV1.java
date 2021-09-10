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
import com.app.helper.Filter;
import com.app.helper.pagination.Pagination;
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
		Filter<Post> filter = new Filter<>(filters);
		Pagination allObjects = service.getAllObjects(filter.getSpecification(), filter.getPageable());
		return ResponseEntity.status(HttpStatus.OK).body(allObjects);
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
		Filter<Comment> filter = new Filter<>(filters);
		Pagination comments = service.getPostComments(id, filter.getSpecification(), filter.getPageable());
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
	public ResponseEntity<?> removePostVoter(Long id, User voter) {
		User result = service.removePostVoter(id, voter);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

	@Override
	public ResponseEntity<?> getPostVoters(Long id, Map<String, String> filters) {
		Filter<User> filter = new Filter<>(filters);
		Pagination voters = service.getPostVoters(id, filter.getSpecification(), filter.getPageable());
		List<Map<String, Object>> result = new ArrayList<>();
		voters.getData().forEach(voter -> {
			User user = (User) voter;
			Map<String, Object> map = new HashMap<>();
			map.put(User_.ID, user.getId());
			map.put("name", user.getFirstName() + " " + user.getLastName());
			result.add(map);
		});
		voters.setData(result);
		return ResponseEntity.status(HttpStatus.OK).body(voters);
	}

	@Override
	public ResponseEntity<?> getPostSharers(Long id, Map<String, String> filters) {
		Filter<User> filter = new Filter<>(filters);
		Pagination voters = service.getPostSharers(id, filter.getSpecification(), filter.getPageable());
		List<Map<String, Object>> result = new ArrayList<>();
		voters.getData().forEach(voter -> {
			User user = (User) voter;
			Map<String, Object> map = new HashMap<>();
			map.put(User_.ID, user.getId());
			map.put("name", user.getFirstName() + " " + user.getLastName());
			result.add(map);
		});
		voters.setData(result);
		return ResponseEntity.status(HttpStatus.OK).body(voters);
	}

	@Override
	public ResponseEntity<?> deleteAllByIds(Map<String, List<Long>> map) {
		service.deleteAllById(map.get("ids"));
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}

	@Override
	public ResponseEntity<?> updateCountViews(Long id) {
		final Integer COUNT_VALUE = 1;
		service.updateCountViews(id, COUNT_VALUE);
		return ResponseEntity.status(HttpStatus.OK).body(COUNT_VALUE);
	}
}
