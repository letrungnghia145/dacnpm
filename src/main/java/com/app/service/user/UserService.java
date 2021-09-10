package com.app.service.user;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import com.app.helper.pagination.Pagination;
import com.app.model.post.Post;
import com.app.model.user.User;
import com.app.service.CRUDService;

public interface UserService extends CRUDService<User, Long> {
	@Transactional
	public User doRegister(String token, String inputCode) throws Exception;

	public User getByEmail(String username);

	@Transactional
	public Post addUserSharedPost(Long id, Post post);

	@Transactional
	public Post removeUserSharedPost(Long id, Post post);

	public Pagination getUserSharedPosts(Long id, Specification<Post> specification, Pageable pagination);

	public Pagination getUserPosts(Long id, Specification<Post> specification, Pageable pagination);

	@Transactional
	public User changePassword(String token, String inputCode, String password) throws Exception;

	public boolean isExistUser(String email);
}
