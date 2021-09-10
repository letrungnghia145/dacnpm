package com.app.repository.post;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.app.model.post.Post;

public interface PostRepository extends JpaRepository<Post, Long>, JpaSpecificationExecutor<Post> {
	@EntityGraph(attributePaths = { "tags", "author" })
	public Optional<Post> findById(Long id);

	@EntityGraph(attributePaths = { "tags", "author" })
	public Page<Post> findAll(Specification<Post> specification, Pageable pageable);

	@EntityGraph(attributePaths = { "comments", "comments.author" })
	public Optional<Post> findPostWithCommentsById(Long id);

	@EntityGraph(attributePaths = { "voters" })
	public Optional<Post> findPostWithVotersById(Long id);

	@EntityGraph(attributePaths = { "sharers" })
	public Optional<Post> findPostWithSharersById(Long id);

	@EntityGraph(attributePaths = { "tags", "comments", "comments.replies" })
	public Optional<Post> findPostToDeleteById(Long id);

	@Modifying
	@Query("UPDATE Post p SET p.countViews = (p.countViews + :count) WHERE p.id = :id")
	public void updateCountViews(Long id, Integer count);
	
}
