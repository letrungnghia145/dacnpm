package com.app.service.tag;

import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.app.helper.pagination.Pagination;
import com.app.model.post.Post;
import com.app.model.tag.Tag;
import com.app.model.tag.Tag_;
import com.app.repository.post.PostRepository;
import com.app.repository.tag.TagRepository;

@Service
public class TagServiceImpl implements TagService {
	@Autowired
	private TagRepository tagRepository;
	@Autowired
	private PostRepository postRepository;

	@Override
	public Tag createObject(Tag object) {
		return tagRepository.save(object);
	}

	@Override
	public Pagination getAllObjects(Specification<Tag> filters, Pageable pagination) {
		List<Tag> data = tagRepository.findAll(filters, pagination).getContent();
		long page = pagination.getPageNumber();
		long limit = pagination.getPageSize();
		long total = tagRepository.count(filters);
		return new Pagination(data, page, limit, total, Optional.of("tags"));
	}

	@Override
	public Tag getObjectById(Long id) {
		return tagRepository.findById(id).orElseThrow();
	}

	@Override
	public Tag updateObject(Long id, Tag object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteObjectById(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Pagination getTagPosts(Long id, Specification<Post> specification, Pageable pagination) {
		Specification<Post> spec = (post, cq, cb) -> {
			Root<Tag> tag = cq.from(Tag.class);
			Expression<List<Post>> tagPosts = tag.get(Tag_.POSTS);
			return cb.and(cb.equal(tag.get(Tag_.ID), id), cb.isMember(post, tagPosts));
		};
		Specification<Post> combine = spec.and(specification);
		List<Post> data = postRepository.findAll(combine, pagination).getContent();
		long page = pagination.getPageNumber();
		long limit = pagination.getPageSize();
		long total = postRepository.count(combine);
		return new Pagination(data, page, limit, total, Optional.of("posts"));
	}

}
