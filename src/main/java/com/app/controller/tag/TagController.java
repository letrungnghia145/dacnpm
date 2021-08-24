package com.app.controller.tag;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.app.controller.CRUDController;
import com.app.model.tag.Tag;

public interface TagController extends CRUDController<Tag, Long> {
	@GetMapping("/{id}/posts")
	public ResponseEntity<?> getTagPosts(@PathVariable Long id, @RequestParam Map<String, String> filters);
}
