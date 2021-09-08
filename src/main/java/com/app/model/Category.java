package com.app.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.app.model.post.Post;
import com.app.model.tag.Tag;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@JsonIgnoreProperties({ "tags", "posts" })
public class Category extends AbstractModel {
	private String name;
	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
	private Set<Post> posts;
	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
	private Set<Tag> tags;
}
