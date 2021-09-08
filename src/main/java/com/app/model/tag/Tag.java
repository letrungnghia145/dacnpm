package com.app.model.tag;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.app.model.AbstractModel;
import com.app.model.Category;
import com.app.model.post.Post;
import com.app.model.user.User;
import com.app.serialize.TagSerialize;
import com.fasterxml.jackson.annotation.JsonCreator;
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
	@ManyToOne(fetch = FetchType.LAZY)
	private Category category;

	public Tag(Long id) {
		this.id = id;
	}

	@JsonCreator
	public Tag(Long id, Category category) {
		this.id = id;
		this.category = category;
	};
}
