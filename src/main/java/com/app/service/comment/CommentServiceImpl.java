package com.app.service.comment;

import java.util.List;
import java.util.Map;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.app.model.post.Comment;
import com.app.model.post.Comment_;
import com.app.model.user.User;
import com.app.repository.comment.CommentRepository;
import com.app.repository.user.UserRepository;
import com.app.service.Filter;

@Service
public class CommentServiceImpl implements CommentService {
	@Autowired
	private CommentRepository commentRepository;
	@Autowired
	private UserRepository userRepository;

	@Override
	public Comment addCommentReply(Long id, Comment reply) {
		Comment comment = commentRepository.findCommentWithRepliesById(id).orElseThrow();
		comment.getReplies().add(reply);
		reply.setComment(comment);
		return reply;
	}

	@Override
	public List<Comment> getCommentReplies(Long id, Map<String, String> filters) {
		Filter<Comment> filter = new Filter<>(filters);
		Specification<Comment> specification = (reply, cq, cb) -> {
			Root<Comment> comment = cq.from(Comment.class);
			Expression<List<Comment>> commentReplies = comment.get(Comment_.REPLIES);
			return cb.and(cb.isMember(reply, commentReplies), cb.equal(comment.get(Comment_.ID), id));
		};
		return commentRepository.findAll(specification.and(filter.getSpecification()));
	}

	@Override
	public User addCommentVoter(Long id, User voter) {
		Comment comment = commentRepository.findCommentWithVotersById(id).orElseThrow();
		comment.getVoters().add(voter);
		return voter;
	}

	@Override
	public List<User> getCommentVoters(Long id, Map<String, String> filters) {
		Filter<User> filter = new Filter<>(filters);
		Specification<User> specification = (user, cq, cb) -> {
			Root<Comment> comment = cq.from(Comment.class);
			Expression<List<User>> commentVoters = comment.get(Comment_.VOTERS);
			return cb.and(cb.equal(comment.get(Comment_.ID), id), cb.isMember(user, commentVoters));
		};
		return userRepository.findAll(specification.and(filter.getSpecification()));
	}

	@Override
	public Comment createObject(Comment object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Comment> getAllObjects(Map<String, String> filters) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Comment getObjectById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Comment updateObject(Comment object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteObjectById(Long id) {
		commentRepository.deleteById(id);
	}

}
