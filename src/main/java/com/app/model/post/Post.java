package com.app.model.post;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.app.model.AbstractModel;
import com.app.model.tag.Tag;
import com.app.model.user.User;
import com.app.serialize.PostSerialize;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@JsonSerialize(using = PostSerialize.class)
public class Post extends AbstractModel {
	private String title;
	@ManyToMany
	@JoinTable(name = "post_tag", joinColumns = @JoinColumn(name = "post_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
	private Set<Tag> tags = new HashSet<>();
	@ManyToOne(fetch = FetchType.LAZY)
	private User author;
	@Column(columnDefinition = "text")
	private String content;
	private String keyword;
	private String notifyEmail;
	
	
	@ManyToMany
	@JoinTable(name = "post_voter", joinColumns = @JoinColumn(name = "post_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
	private Set<User> voters = new HashSet<>();
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "post")
	private Set<Comment> comments = new HashSet<>();
	@ManyToMany(mappedBy = "sharedPosts")
	private Set<User> sharers = new HashSet<>();

	@JsonCreator
	public Post(String title, String content, User user, Set<Tag> tags) {
		this.title = title;
		this.content = content;
		this.author = user;
		this.tags = tags;
	}
}
