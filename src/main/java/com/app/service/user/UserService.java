package com.app.service.user;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.app.model.post.Post;
import com.app.model.user.User;
import com.app.service.CRUDService;

public interface UserService extends CRUDService<User, Long> {
	@Transactional
	public User doRegister(String token, String inputCode) throws Exception;

	public User getByEmail(String username);

	@Transactional
	public Post addUserSharedPost(Long id, Post post);

	public List<Post> getUserSharedPosts(Long id, Map<String, String> filters);

	public List<Post> getUserPosts(Long id, Map<String, String> filters);

	@Transactional
	public User changePassword(String token, String inputCode, String password) throws Exception;

	public boolean isExistUser(String email);
}
