package com.app.controller.category.v1;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.config.constant.url.BaseURL;
import com.app.controller.category.CategoryController;
import com.app.helper.Filter;
import com.app.helper.pagination.Pagination;
import com.app.model.Category;
import com.app.model.tag.Tag;
import com.app.service.category.CategoryService;

@RestController
@RequestMapping(BaseURL.CATEGORY_BASE_URL_V1)
public class CategoryControllerV1 implements CategoryController {
	@Autowired
	private CategoryService service;

	@Override
	public ResponseEntity<?> getAllObjects(Map<String, String> filters) {
		Filter<Category> filter = new Filter<>(filters);
		Pagination categories = service.getAllObjects(filter.getSpecification(), filter.getPageable());
		return ResponseEntity.status(HttpStatus.OK).body(categories);
	}

	@Override
	public ResponseEntity<?> getObject(Long id) {
		Category category = service.getObjectById(id);
		return ResponseEntity.status(HttpStatus.OK).body(category);
	}

	@Override
	public ResponseEntity<?> createObject(Category dto) {
		Category category = service.createObject(dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(category);
	}

	@Override
	public ResponseEntity<?> updateObject(Long id, Category dto) {
		Category updateObject = service.updateObject(id, dto);
		return ResponseEntity.status(HttpStatus.OK).body(updateObject);
	}

	@Override
	public ResponseEntity<?> deleteObject(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<?> getCategoryTags(Long id, Map<String, String> filters) {
		Filter<Tag> filter = new Filter<>(filters);
		Pagination tags = service.getCategoryTags(id, filter.getSpecification(), filter.getPageable());
		return ResponseEntity.status(HttpStatus.OK).body(tags);
	}

}
