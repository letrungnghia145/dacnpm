package com.app.service.tag;

import java.util.List;
import java.util.Map;

import com.app.model.post.Post;
import com.app.model.tag.Tag;
import com.app.service.CRUDService;

public interface TagService extends CRUDService<Tag, Long> {
	public List<Post> getTagPosts(Long id, Map<String, String> filters);
}
