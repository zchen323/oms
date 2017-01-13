package com.ccg.oms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ccg.oms.dao.entiry.Project;
import com.ccg.oms.dao.entiry.ProjectUser;
import com.ccg.oms.dao.repository.ProjectRepository;
import com.ccg.oms.service.ProjectServices;

@Service
public class ProjectServicesImpl implements ProjectServices{

	@Autowired
	ProjectRepository projectRepository;
	
	@Transactional
	public void findProjectById(int id) {
		
		Project proj = projectRepository.findOne(1);
		
		System.out.println(proj.getName());
		List<ProjectUser> users = proj.getUsers();
		for(ProjectUser user : users){
			System.out.println(user.getUsername());
		}
	}

}
