package com.app.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.app.model.post.Post;
import com.app.model.tag.Tag;
import com.app.serialize.CategorySerialize;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@JsonIgnoreProperties({ "posts" })
@JsonSerialize(using = CategorySerialize.class)
@NoArgsConstructor
public class Category extends AbstractModel {
	private String name;
	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
	private Set<Post> posts;
	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
	private Set<Tag> tags;

	@JsonCreator
	public Category(String name, Set<Tag> tags) {
		super();
		this.name = name;
		this.tags = tags;
	}
}
