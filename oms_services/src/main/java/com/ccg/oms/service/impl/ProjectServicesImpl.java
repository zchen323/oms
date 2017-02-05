package com.ccg.oms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccg.oms.common.data.project.Project;
import com.ccg.oms.dao.entiry.project.ProjectEntity;
import com.ccg.oms.dao.entiry.project.TaskEntity;
import com.ccg.oms.dao.repository.project.ProjectRepository;
import com.ccg.oms.dao.repository.project.TaskRepository;
import com.ccg.oms.service.ProjectAdminServices;
import com.ccg.oms.service.ProjectServices;
import com.ccg.oms.service.mapper.ProjectMapper;

@Service
public class ProjectServicesImpl implements ProjectServices{

	@Autowired
	ProjectRepository projectRepository;
	
	@Autowired
	TaskRepository taskRepository;
	
	@Autowired
	ProjectAdminServices projectAdminServices;
	
	@Override
	public void createNewProject(Project project) {
		ProjectEntity entity = ProjectMapper.toEntity(project);
		projectRepository.save(entity);
		
//		Integer projectTemplateId = project.getProjTempId();
//		projectAdminServices.getProjectTemplateById(projectTemplateId);
//		TaskEntity taskEntity = new TaskEntity();
//		taskEntity.
		
		
	}

}
