package com.ccg.oms.dao.repository.project;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ccg.oms.common.data.project.ProjectUser;
import com.ccg.oms.dao.entiry.project.ProjectUserEntity;

public interface ProjectUserRepository extends CrudRepository<ProjectUserEntity, Integer>{
	List<ProjectUserEntity> findByProjectId(Integer projectId);
}
