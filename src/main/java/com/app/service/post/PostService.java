package com.app.service.post;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import com.app.helper.pagination.Pagination;
import com.app.model.post.Comment;
import com.app.model.post.Post;
import com.app.model.user.User;
import com.app.service.CRUDService;

public interface PostService extends CRUDService<Post, Long> {
	public Pagination getPostComments(Long id, Specification<Comment> specification, Pageable pagination);

	@Transactional
	public Comment addPostComment(Long id, Comment comment);

	@Transactional
	public User addPostVoter(Long id, User voter);

	public Pagination getPostVoters(Long id, Specification<User> specification, Pageable pagination);

	@Transactional
	public void deleteAllById(List<Long> ids);
}
