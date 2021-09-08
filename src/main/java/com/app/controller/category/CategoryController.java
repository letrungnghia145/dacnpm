package com.app.controller.category;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.app.controller.CRUDController;
import com.app.model.Category;

public interface CategoryController extends CRUDController<Category, Long> {
	@GetMapping("/{id}/tags")
	public ResponseEntity<?>  getCategoryTags(@PathVariable Long id, @RequestParam Map<String, String> filters);
}
