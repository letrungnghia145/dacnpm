package com.app.service.category;

import java.util.List;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.app.helper.pagination.Pagination;
import com.app.model.Category;
import com.app.model.Category_;
import com.app.model.tag.Tag;
import com.app.repository.category.CategoryRepository;
import com.app.repository.tag.TagRepository;

@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private TagRepository tagRepository;

	@Override
	public Category createObject(Category object) {
		return categoryRepository.save(object);
	}

	@Override
	public Pagination getAllObjects(Specification<Category> specification, Pageable pagination) {
		List<Category> data = categoryRepository.findAll(specification, pagination).getContent();
		long page = pagination.getPageNumber();
		long limit = pagination.getPageSize();
		long total = categoryRepository.count(specification);
		return new Pagination(data, page, limit, total);
	}

	@Override
	public Category getObjectById(Long id) {
		return categoryRepository.findById(id).orElseThrow();
	}

	@Override
	public Category updateObject(Long id, Category object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteObjectById(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Pagination getCategoryTags(Long id, Specification<Tag> specification, Pageable pagination) {
		Specification<Tag> spec = (tag, cq, cb) -> {
			Root<Category> post = cq.from(Category.class);
			Expression<List<Tag>> categoryTags = post.get(Category_.TAGS);
			return cb.and(cb.equal(post.get(Category_.ID), id), cb.isMember(tag, categoryTags));
		};
		Specification<Tag> combine = spec.and(specification);
		List<Tag> data = tagRepository.findAll(combine, pagination).getContent();
		long page = pagination.getPageNumber();
		long limit = pagination.getPageSize();
		long total = tagRepository.count(combine);
		return new Pagination(data, page, limit, total);
	}

}
