package com.app.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import com.app.helper.pagination.Pagination;

public interface CRUDService<O, ID> {
	@Transactional
	public O createObject(O object);

	public Pagination getAllObjects(Specification<O> specification, Pageable pagination);

	public O getObjectById(ID id);

	@Transactional
	public O updateObject(ID id, O object);

	@Transactional
	public void deleteObjectById(ID id);
}
