package com.app.model;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public abstract class AbstractModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	protected Long id;
	@Setter(value = AccessLevel.NONE)
	protected Date createdDate;
	@Setter(value = AccessLevel.NONE)
	protected Date modifiedDate;

	@PrePersist
	protected void setWhenPersist() {
		this.createdDate = new Date();
	}

	@PreUpdate
	protected void setWhenUpdate() {
		this.modifiedDate = new Date();
	}
}
