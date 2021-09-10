package com.app.service.post;

import java.util.List;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.app.helper.pagination.Pagination;
import com.app.model.post.Comment;
import com.app.model.post.Post;
import com.app.model.post.Post_;
import com.app.model.user.User;
import com.app.repository.comment.CommentRepository;
import com.app.repository.post.PostRepository;
import com.app.repository.tag.TagRepository;
import com.app.repository.user.UserRepository;

@Service
public class PostServiceImpl implements PostService {
	@Autowired
	private PostRepository postRepository;
	@Autowired
	private CommentRepository commentRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private TagRepository tagRepository;

	@Override
	public Post createObject(Post object) {
		Post post = postRepository.save(object);
		post.getTags().forEach(tag -> {
			tagRepository.inscreaseCurrentPostIndex(tag);
		});
		return post;
	}

	@Override
	public Pagination getAllObjects(Specification<Post> specification, Pageable pagination) {
		List<Post> data = postRepository.findAll(specification, pagination).getContent();
		long page = pagination.getPageNumber();
		long limit = pagination.getPageSize();
		long total = postRepository.count(specification);
		return new Pagination(data, page, limit, total);
	}

	@Override
	public Post getObjectById(Long id) {
		return postRepository.findById(id).orElseThrow();
	}

	@Override
	public Post updateObject(Long id, Post object) {
		return postRepository.save(object);
	}

	@Override
	public void deleteObjectById(Long id) {
		Post post = postRepository.findPostToDeleteById(id).orElseThrow();
		post.getTags().forEach(tag -> {
			tagRepository.decreaseCurrentPostIndex(tag);
		});
		postRepository.delete(post);
	}

	@Override
	public Pagination getPostComments(Long id, Specification<Comment> specification, Pageable pagination) {
		Specification<Comment> spec = (comment, cq, cb) -> {
			Root<Post> post = cq.from(Post.class);
			Expression<List<Comment>> postComments = post.get(Post_.COMMENTS);
			return cb.and(cb.equal(post.get(Post_.ID), id), cb.isMember(comment, postComments));
		};
		Specification<Comment> combine = spec.and(specification);
		List<Comment> data = commentRepository.findAll(combine, pagination).getContent();
		long page = pagination.getPageNumber();
		long limit = pagination.getPageSize();
		long total = commentRepository.count(combine);
		return new Pagination(data, page, limit, total);
	}

	@Override
	public Comment addPostComment(Long id, Comment comment) {
		Post post = postRepository.findPostWithCommentsById(id).orElseThrow();
		post.getComments().add(comment);
		comment.setPost(post);
		return comment;
	}

	@Override
	public User addPostVoter(Long id, User voter) {
		Post post = postRepository.findPostWithVotersById(id).orElseThrow();
		post.getVoters().add(voter);
		voter.getVotedPosts().add(post);
		return voter;
	}

	@Override
	public Pagination getPostVoters(Long id, Specification<User> specification, Pageable pagination) {
		Specification<User> spec = (user, query, cb) -> {
			Root<Post> post = query.from(Post.class);
			Expression<List<User>> postVoters = post.get(Post_.VOTERS);
			return cb.and(cb.equal(post.get(Post_.ID), id), cb.isMember(user, postVoters));

		};
		Specification<User> combine = spec.and(specification);
		List<User> data = userRepository.findAll(combine, pagination).getContent();
		long page = pagination.getPageNumber();
		long limit = pagination.getPageSize();
		long total = userRepository.count(combine);
		return new Pagination(data, page, limit, total);
	}
	
	
	@Override
	public Pagination getPostSharers(Long id, Specification<User> specification, Pageable pagination) {
		Specification<User> spec = (user, query, cb) -> {
			Root<Post> post = query.from(Post.class);
			Expression<List<User>> postSharers = post.get(Post_.SHARERS);
			return cb.and(cb.equal(post.get(Post_.ID), id), cb.isMember(user, postSharers));
		};
		Specification<User> combine = spec.and(specification);
		List<User> data = userRepository.findAll(combine, pagination).getContent();
		long page = pagination.getPageNumber();
		long limit = pagination.getPageSize();
		long total = userRepository.count(combine);
		return new Pagination(data, page, limit, total);
	}

	@Override
	public void deleteAllById(List<Long> ids) {
		for (Long id : ids) {
			deleteObjectById(id);
		}
	}

	@Override
	public void updateCountViews(Long id, Integer count) {
		postRepository.updateCountViews(id, count);
	}
}
