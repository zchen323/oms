package com.ccg.oms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccg.oms.common.data.project.Project;
import com.ccg.oms.dao.repository.project.ProjectRepository;
import com.ccg.oms.service.OmsService;
import com.ccg.oms.service.mapper.ProjectMapper;

@Service
public class OmsServiceImpl implements OmsService{
	
	@Autowired
	private ProjectRepository projectRepository;

	public Project getProjectById(int id) {
		
		com.ccg.oms.dao.entiry.project.ProjectEntity entity = projectRepository.findOne(id);
		
			
		Project project = ProjectMapper.fromEntity(entity);
		
		return project;
	}

}
