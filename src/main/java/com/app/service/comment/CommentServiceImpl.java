package com.app.service.comment;

import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.app.helper.pagination.Pagination;
import com.app.model.post.Comment;
import com.app.model.post.Comment_;
import com.app.model.user.User;
import com.app.repository.comment.CommentRepository;
import com.app.repository.user.UserRepository;

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
	public Pagination getCommentReplies(Long id, Specification<Comment> specification, Pageable pagination) {
		Specification<Comment> spec = (reply, cq, cb) -> {
			Root<Comment> comment = cq.from(Comment.class);
			Expression<List<Comment>> commentReplies = comment.get(Comment_.REPLIES);
			return cb.and(cb.isMember(reply, commentReplies), cb.equal(comment.get(Comment_.ID), id));
		};

		Specification<Comment> combine = spec.and(specification);
		List<Comment> data = commentRepository.findAll(combine, pagination).getContent();
		long page = pagination.getPageNumber();
		long limit = pagination.getPageSize();
		long total = commentRepository.count(combine);
		return new Pagination(data, page, limit, total, Optional.of("comments"));
	}

	@Override
	public User addCommentVoter(Long id, User voter) {
		Comment comment = commentRepository.findCommentWithVotersById(id).orElseThrow();
		comment.getVoters().add(voter);
		return voter;
	}

	@Override
	public Pagination getCommentVoters(Long id, Specification<User> specification, Pageable pagination) {
		Specification<User> spec = (user, cq, cb) -> {
			Root<Comment> comment = cq.from(Comment.class);
			Expression<List<User>> commentVoters = comment.get(Comment_.VOTERS);
			return cb.and(cb.equal(comment.get(Comment_.ID), id), cb.isMember(user, commentVoters));
		};
		Specification<User> combine = spec.and(specification);
		List<User> data = userRepository.findAll(combine, pagination).getContent();
		long page = pagination.getPageNumber();
		long limit = pagination.getPageSize();
		long total = userRepository.count(combine);
		return new Pagination(data, page, limit, total, Optional.of("users"));
	}

	@Override
	public Comment createObject(Comment object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pagination getAllObjects(Specification<Comment> specification, Pageable pagination) {
		List<Comment> data = commentRepository.findAll(specification, pagination).getContent();
		long page = pagination.getPageNumber();
		long limit = pagination.getPageSize();
		long total = commentRepository.count(specification);
		return new Pagination(data, page, limit, total, Optional.of("comments"));
	}

	@Override
	public Comment getObjectById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Comment updateObject(Long id, Comment object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteObjectById(Long id) {
		commentRepository.deleteById(id);
	}

}
