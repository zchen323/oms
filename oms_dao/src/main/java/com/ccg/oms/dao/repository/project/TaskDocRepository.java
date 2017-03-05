package com.ccg.oms.dao.repository.project;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ccg.oms.dao.entiry.project.TaskDocEntity;

public interface TaskDocRepository extends CrudRepository<TaskDocEntity, Integer>{
	List<TaskDocEntity> findByTaskIdOrderByIdDesc(Integer taskId);
	List<TaskDocEntity> findByDocumentId(Integer documentId);
}
