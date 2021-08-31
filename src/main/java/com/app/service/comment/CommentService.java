package com.app.service.comment;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import com.app.helper.pagination.Pagination;
import com.app.model.post.Comment;
import com.app.model.user.User;
import com.app.service.CRUDService;

public interface CommentService extends CRUDService<Comment, Long> {
	@Transactional
	public Comment addCommentReply(Long id, Comment reply);

	public Pagination getCommentReplies(Long id, Specification<Comment> specification, Pageable pagination);

	@Transactional
	public User addCommentVoter(Long id, User voter);

	public Pagination getCommentVoters(Long id, Specification<User> specification, Pageable pagination);
}
