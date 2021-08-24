package com.app.model.user;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.app.model.AbstractModel;
import com.app.model.post.Comment;
import com.app.model.post.Post;
import com.app.model.tag.Tag;
import com.app.serialize.UserSerialize;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@JsonIgnoreProperties({ "commentsWithPost", "postedPosts", "interestingTags", "sharedPosts", "votedPosts" })
@JsonSerialize(using = UserSerialize.class)
public class User extends AbstractModel {
//	private Account account;
	private String firstName;
	private String lastName;
	private Integer age;
	private String email;
	private String password;
	@Enumerated(EnumType.STRING)
	private Role role;

	
	
	@ManyToMany(mappedBy = "voters")
	private Set<Post> votedPosts = new HashSet<>();
//	@ManyToMany(mappedBy = "voters")
//	private Set<Comment> votedComments = new HashSet<>();
	

	@OneToMany(mappedBy = "author")
	private Set<Comment> commentsWithPost = new HashSet<>();
	@OneToMany(mappedBy = "author")
	private Set<Post> postedPosts = new HashSet<>();
	@ManyToMany
	@JoinTable(name = "user_interested", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
	private Set<Tag> interestingTags = new HashSet<>();
	@ManyToMany
	@JoinTable(name = "user_shared", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "post_id"))
	private Set<Post> sharedPosts = new HashSet<>();
	
	

	public User(Long id) {
		this.id = id;
	}
}
