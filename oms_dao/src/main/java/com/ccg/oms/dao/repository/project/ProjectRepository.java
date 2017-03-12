package com.ccg.oms.dao.repository.project;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ccg.oms.dao.entiry.project.ProjectEntity;


public interface ProjectRepository extends JpaRepository<ProjectEntity, Integer>{
	List<ProjectEntity> findByNameContainingOrderByIdDesc(String nameContains);
	//ProjectEntity findFirstByName();

}


