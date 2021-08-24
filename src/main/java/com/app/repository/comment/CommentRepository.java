package com.app.repository.comment;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.app.model.post.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long>, JpaSpecificationExecutor<Comment> {
//	@EntityGraph(attributePaths = { "replies", "replies.author", "replies.replies", "replies.voters" })
	@EntityGraph(attributePaths = { "replies", "author" })
	public Optional<Comment> findCommentWithRepliesById(Long id);

	@EntityGraph(attributePaths = { "author" })
	public List<Comment> findAll(Specification<Comment> specification);

	@EntityGraph(attributePaths = { "voters" })
	public Optional<Comment> findCommentWithVotersById(Long id);

	@EntityGraph(attributePaths = { "replies" })
	public void deleteById(Long id);
}
