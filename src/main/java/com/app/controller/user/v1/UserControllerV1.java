package com.app.controller.user.v1;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.config.constant.AppConstant;
import com.app.config.constant.url.BaseURL;
import com.app.controller.user.UserController;
import com.app.helper.AuthUtils;
import com.app.helper.Mapper;
import com.app.model.post.Post;
import com.app.model.user.User;
import com.app.model.user.User_;
import com.app.service.user.UserService;

@RestController
@RequestMapping(BaseURL.USER_BASE_URL_V1)
public class UserControllerV1 implements UserController {
	@Autowired
	private UserService service;
	private String[] userWithFields = new String[] { User_.FIRST_NAME, User_.LAST_NAME, User_.CREATED_DATE, User_.AGE,
			User_.EMAIL };

	@Override
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<?> getUserProfile() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = service.getByEmail(email);
		return ResponseEntity.status(HttpStatus.OK).body(user);
	}

	@Override
	public ResponseEntity<?> getObject(Long id) {
		User user = service.getObjectById(id);

		return ResponseEntity.status(HttpStatus.OK).body(Mapper.toMapValue(user, userWithFields));
	}

	@Override
	public ResponseEntity<?> addUserSharedPost(Long id, Post post) {
		Post sharedPost = service.addUserSharedPost(id, post);
		return ResponseEntity.status(HttpStatus.CREATED).body(sharedPost);
	}

	@Override
	public ResponseEntity<?> getUserSharedPosts(Long id, Map<String, String> filters) {
		List<Post> sharedPosts = service.getUserSharedPosts(id, filters);
		return ResponseEntity.status(HttpStatus.OK).body(sharedPosts);
	}

	@Override
	public ResponseEntity<?> getUserPosts(Long id, Map<String, String> filters) {
		List<Post> posts = service.getUserPosts(id, filters);
		return ResponseEntity.status(HttpStatus.OK).body(posts);
	}

	@Override
	public ResponseEntity<?> getAllObjects(Map<String, String> filters) {
		List<User> users = service.getAllObjects(filters);
		return ResponseEntity.status(HttpStatus.OK).body(Mapper.toMapValues(users, userWithFields));
	}

	@Override
	public ResponseEntity<?> createObject(User dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<?> updateObject(Long id, User dto) {
		String[] ignoreFields = new String[] { User_.PASSWORD, User_.EMAIL, User_.ROLE, User_.ID };
		User result = service.updateObject(id, Mapper.toObjectIgnoreFields(dto, ignoreFields));
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

	@Override
	public ResponseEntity<?> deleteObject(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<?> changeUserPassword(Map<String, Object> request) throws Exception {
		String token = AuthUtils.generateAuthInfo(request);
		return ResponseEntity.status(HttpStatus.OK).body(token);
	}

	@Override
	public ResponseEntity<?> confirmChangePassword(Map<String, Optional<String>> request) throws Exception {
		String password = request.get(User_.PASSWORD).get();
		String token = request.get(AppConstant.TOKEN).get();
		String code = request.get(AppConstant.VALIDATION_CODE).get();
		service.changePassword(token, code, password);
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}
}
