package com.app.service.post;

import java.util.List;
import java.util.Map;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.app.model.post.Comment;
import com.app.model.post.Post;
import com.app.model.post.Post_;
import com.app.model.user.User;
import com.app.repository.comment.CommentRepository;
import com.app.repository.post.PostRepository;
import com.app.repository.tag.TagRepository;
import com.app.repository.user.UserRepository;
import com.app.service.Filter;

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
			tagRepository.updateCurrentPostIndex(tag);
		});
		return post;
	}

	@Override
	public List<Post> getAllObjects(Map<String, String> filters) {
		Filter<Post> filter = new Filter<>(filters);
		return postRepository.findAll(filter.getSpecification());
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
		postRepository.deleteById(id);
	}

	@Override
	public List<Comment> getPostComments(Long id, Map<String, String> filters) {
		Filter<Comment> filter = new Filter<>(filters);
		Specification<Comment> specification = (comment, cq, cb) -> {
			Root<Post> post = cq.from(Post.class);
			Expression<List<Comment>> postComments = post.get(Post_.COMMENTS);
			return cb.and(cb.equal(post.get(Post_.ID), id), cb.isMember(comment, postComments));
		};
		List<Comment> comments = commentRepository.findAll(specification.and(filter.getSpecification()));
		return comments;
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
	public List<User> getPostVoters(Long id, Map<String, String> filters) {
		Filter<User> filter = new Filter<>(filters);
		Specification<User> specification = (user, query, cb) -> {
			Root<Post> post = query.from(Post.class);
			Expression<List<User>> postVoters = post.get(Post_.VOTERS);
			return cb.and(cb.equal(post.get(Post_.ID), id), cb.isMember(user, postVoters));

		};
		return userRepository.findAll(specification.and(filter.getSpecification()));
	}

	@Override
	public void deleteAllById(List<Long> ids) {
		postRepository.deleteAllByIdInBatch(ids);
	}
}
