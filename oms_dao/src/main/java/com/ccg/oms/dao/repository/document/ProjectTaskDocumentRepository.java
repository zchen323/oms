package com.ccg.oms.dao.repository.document;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ccg.oms.dao.entiry.document.ProjectTaskDocumentEntity;

public interface ProjectTaskDocumentRepository extends CrudRepository<ProjectTaskDocumentEntity, Integer> {
	List<ProjectTaskDocumentEntity> findByProjectId(Integer projectId);
	List<ProjectTaskDocumentEntity> findByTaskId(Integer taskId);
}
