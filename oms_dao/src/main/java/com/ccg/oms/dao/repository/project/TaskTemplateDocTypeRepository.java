package com.ccg.oms.dao.repository.project;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ccg.oms.dao.entiry.project.TaskTemplateDocTypeEntity;

public interface TaskTemplateDocTypeRepository extends CrudRepository <TaskTemplateDocTypeEntity, Integer>{
	List<TaskTemplateDocTypeEntity> findByTaskTemplateId(Integer id);
}
