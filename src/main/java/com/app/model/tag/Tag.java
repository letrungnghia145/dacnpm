package com.app.model.tag;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import com.app.model.AbstractModel;
import com.app.model.post.Post;
import com.app.model.user.User;
import com.app.serialize.TagSerialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@JsonSerialize(using = TagSerialize.class)
public class Tag extends AbstractModel {
	private String name;
	@ManyToMany(mappedBy = "tags")
	private Set<Post> posts = new HashSet<>();
	@ManyToMany(mappedBy = "interestingTags")
	private Set<User> interestedUsers = new HashSet<>();
	private Integer currentPostIndex = 0;

	public Tag(Long id) {
		this.id = id;
	}
}
