package com.app.repository.category;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.app.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>, JpaSpecificationExecutor<Category> {
	@EntityGraph(attributePaths = { "tags" })
	public Optional<Category> findById(Long id);

	@EntityGraph(attributePaths = { "tags" })
	public Page<Category> findAll(Specification<Category> specification, Pageable pageable);
}
