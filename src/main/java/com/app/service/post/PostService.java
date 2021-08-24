package com.app.service.post;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.app.model.post.Comment;
import com.app.model.post.Post;
import com.app.model.user.User;
import com.app.service.CRUDService;

public interface PostService extends CRUDService<Post, Long> {
	public List<Comment> getPostComments(Long id, Map<String, String> filters);

	@Transactional
	public Comment addPostComment(Long id, Comment comment);

	@Transactional
	public User addPostVoter(Long id, User voter);

	public List<User> getPostVoters(Long id, Map<String, String> filters);

	@Transactional
	public void deleteAllById(List<Long> ids);
}
