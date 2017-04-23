package com.ccg.oms.dao.repository.document;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ccg.oms.dao.entiry.document.DocumentCategoryEntity;

public interface DocumentCategoryRepository extends CrudRepository<DocumentCategoryEntity, Integer>{
	List<DocumentCategoryEntity> findByDocumentId(Integer documentnId);
}
