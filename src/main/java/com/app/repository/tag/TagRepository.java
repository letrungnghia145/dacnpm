package com.app.repository.tag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.app.model.tag.Tag;

public interface TagRepository extends JpaRepository<Tag, Long>, JpaSpecificationExecutor<Tag> {

	@Modifying
	@Query("update Tag t set t.currentPostIndex = t.currentPostIndex + 1 where t = ?1")
	public void inscreaseCurrentPostIndex(Tag tag);

	@Modifying
	@Query("update Tag t set t.currentPostIndex = t.currentPostIndex - 1 where t = ?1")
	public void decreaseCurrentPostIndex(Tag tag);

}
