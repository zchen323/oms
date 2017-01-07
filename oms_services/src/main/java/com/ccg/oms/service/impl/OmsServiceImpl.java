package com.ccg.oms.service.impl;

import org.springframework.stereotype.Service;

import com.ccg.oms.common.data.Project;
import com.ccg.oms.service.OmsService;

@Service
public class OmsServiceImpl implements OmsService{

	public Project getProjectById(int id) {
		
		Project project = new Project();
		project.setDescription("this is project description");
		project.setId(1);
		project.setName("Very Important Project");
		
		return project;
	}

}
