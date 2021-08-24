package com.app.service.comment;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.app.model.post.Comment;
import com.app.model.user.User;
import com.app.service.CRUDService;

public interface CommentService extends CRUDService<Comment, Long> {
	@Transactional
	public Comment addCommentReply(Long id, Comment reply);

	public List<Comment> getCommentReplies(Long id, Map<String, String> filters);

	@Transactional
	public User addCommentVoter(Long id, User voter);

	public List<User> getCommentVoters(Long id, Map<String, String> filters);
}
