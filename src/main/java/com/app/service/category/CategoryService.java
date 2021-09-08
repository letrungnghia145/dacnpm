package com.app.service.category;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.app.helper.pagination.Pagination;
import com.app.model.Category;
import com.app.model.tag.Tag;
import com.app.service.CRUDService;

public interface CategoryService extends CRUDService<Category, Long> {
	public Pagination getCategoryTags(Long id, Specification<Tag> specification, Pageable pagination);
}
