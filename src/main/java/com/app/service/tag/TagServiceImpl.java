package com.app.service.tag;

import java.util.List;
import java.util.Map;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.app.model.post.Post;
import com.app.model.tag.Tag;
import com.app.model.tag.Tag_;
import com.app.repository.post.PostRepository;
import com.app.repository.tag.TagRepository;
import com.app.service.Filter;

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
	public List<Tag> getAllObjects(Map<String, String> filters) {
		Filter<Tag> filter = new Filter<>(filters);
		return tagRepository.findAll(filter.getSpecification());
	}

	@Override
	public Tag getObjectById(Long id) {
		return tagRepository.findById(id).orElseThrow();
	}

	@Override
	public Tag updateObject(Tag object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteObjectById(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Post> getTagPosts(Long id, Map<String, String> filters) {
		Filter<Post> filter = new Filter<>(filters);
		Specification<Post> specification = (post, cq, cb) -> {
			Root<Tag> tag = cq.from(Tag.class);
			Expression<List<Post>> tagPosts = tag.get(Tag_.POSTS);
			return cb.and(cb.equal(tag.get(Tag_.ID), id), cb.isMember(post, tagPosts));
		};
		return postRepository.findAll(specification.and(filter.getSpecification()));
	}

}
