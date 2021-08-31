package com.app.controller.tag.v1;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.config.constant.url.BaseURL;
import com.app.controller.tag.TagController;
import com.app.helper.Filter;
import com.app.helper.pagination.Pagination;
import com.app.model.post.Post;
import com.app.model.tag.Tag;
import com.app.service.tag.TagService;

@RequestMapping(BaseURL.TAG_BASE_URL_V1)
@RestController
public class TagControllerV1 implements TagController {
	@Autowired
	private TagService service;

	@Override
	public ResponseEntity<?> getAllObjects(Map<String, String> filters) {
		Filter<Tag> filter = new Filter<>(filters);
		Pagination tags = service.getAllObjects(filter.getSpecification(), filter.getPageable());
		return ResponseEntity.status(HttpStatus.OK).body(tags);
	}

	@Override
	public ResponseEntity<?> getObject(Long id) {
		Tag tag = service.getObjectById(id);
		return ResponseEntity.status(HttpStatus.OK).body(tag);
	}

	@Override
	public ResponseEntity<?> createObject(Tag dto) {
		Tag tag = service.createObject(dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(tag);
	}

	@Override
	public ResponseEntity<?> updateObject(Long id, Tag dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<?> deleteObject(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<?> getTagPosts(Long id, Map<String, String> filters) {
		Filter<Post> filter = new Filter<>(filters);
		Pagination posts = service.getTagPosts(id, filter.getSpecification(), filter.getPageable());
		return ResponseEntity.status(HttpStatus.OK).body(posts);
	}
}
