package com.ccg.oms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccg.oms.common.data.project.Project;
import com.ccg.oms.dao.repository.project.ProjectRepository;
import com.ccg.oms.service.OmsService;

@Service
public class OmsServiceImpl implements OmsService{
	
	@Autowired
	private ProjectRepository projectRepository;

	public Project getProjectById(int id) {
		
		com.ccg.oms.dao.entiry.project.ProjectEntity proj = projectRepository.findOne(id);
		
		Project project = new Project();		
		project.setDescription(proj.getDescription());
		project.setId(id);
		project.setName(proj.getName());
		
		return project;
	}

}
