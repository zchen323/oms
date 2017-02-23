package com.ccg.oms.dao.repository.project;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ccg.oms.dao.entiry.project.TaskNoteEntity;

public interface TaskNoteRepository extends CrudRepository<TaskNoteEntity, Integer> {
	List<TaskNoteEntity> findByTaskIdOrderByIdDesc(Integer taskId);
}
