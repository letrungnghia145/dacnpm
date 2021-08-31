package com.app.service.tag;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.app.helper.pagination.Pagination;
import com.app.model.post.Post;
import com.app.model.tag.Tag;
import com.app.service.CRUDService;

public interface TagService extends CRUDService<Tag, Long> {
	public Pagination getTagPosts(Long id, Specification<Post> specification, Pageable pagination);
}
