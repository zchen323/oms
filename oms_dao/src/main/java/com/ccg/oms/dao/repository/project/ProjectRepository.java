package com.ccg.oms.dao.repository.project;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ccg.oms.dao.entiry.project.ProjectEntity;

public interface ProjectRepository extends CrudRepository<ProjectEntity, Integer>{
	List<ProjectEntity> findByNameContaining(String nameContains);
}


