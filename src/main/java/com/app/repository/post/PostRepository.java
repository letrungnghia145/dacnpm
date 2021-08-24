package com.app.repository.post;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.app.model.post.Post;

public interface PostRepository extends JpaRepository<Post, Long>, JpaSpecificationExecutor<Post> {
	@EntityGraph(attributePaths = { "tags", "author" })
	public Optional<Post> findById(Long id);

	@EntityGraph(attributePaths = { "tags", "author" })
	public List<Post> findAll(Specification<Post> specification);

	@EntityGraph(attributePaths = { "comments", "comments.author" })
	public Optional<Post> findPostWithCommentsById(Long id);

	@EntityGraph(attributePaths = { "voters" })
	public Optional<Post> findPostWithVotersById(Long id);
	
	@EntityGraph(attributePaths = {"comments", "comments.replies"})
	public void deleteById(Long id);
}
