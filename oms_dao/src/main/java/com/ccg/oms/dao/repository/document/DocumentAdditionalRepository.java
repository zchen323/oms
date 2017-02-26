package com.ccg.oms.dao.repository.document;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ccg.oms.dao.entiry.document.DocumentAdditionalEntity;

public interface DocumentAdditionalRepository extends CrudRepository<DocumentAdditionalEntity, Integer>{
	List<DocumentAdditionalEntity> findByDocumentIdOrderById(Integer projectId);
}
