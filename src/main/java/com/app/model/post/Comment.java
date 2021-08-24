package com.app.model.post;

import java.util.HashSet;
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
import com.app.model.user.User;
import com.app.serialize.CommentSerialize;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@JsonIgnoreProperties({ "voters", "replies", "comment" })
@JsonSerialize(using = CommentSerialize.class)
public class Comment extends AbstractModel {
	@ManyToOne(fetch = FetchType.LAZY)
	private User author;
	@Column(columnDefinition = "text")
	private String content;
	@ManyToOne(fetch = FetchType.LAZY)
	private Post post;
	@ManyToMany
	@JoinTable(name = "comment_voter", joinColumns = @JoinColumn(name = "comment_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
	private Set<User> voters = new HashSet<>();

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "comment")
	private Set<Comment> replies = new HashSet<>();

	@JoinColumn(name = "comment_id")
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	private Comment comment;

	@JsonCreator
	public Comment(String content, Long userId) {
		super();
		this.content = content;
		this.author = new User(userId);
	}
}
