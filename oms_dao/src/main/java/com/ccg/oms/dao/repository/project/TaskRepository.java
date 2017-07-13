package com.ccg.oms.dao.repository.project;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ccg.oms.dao.entiry.project.TaskEntity;

public interface TaskRepository extends CrudRepository<TaskEntity, Integer>{
	List<TaskEntity> findByProjectIdOrderBySeq(Integer projectId);
}
