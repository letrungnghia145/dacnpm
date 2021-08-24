package com.app.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

public interface CRUDService<O, ID> {
	@Transactional
	public O createObject(O object);

	public List<O> getAllObjects(Map<String, String> filters);

	public O getObjectById(ID id);

	@Transactional
	public O updateObject(O object);

	@Transactional
	public void deleteObjectById(ID id);
}
