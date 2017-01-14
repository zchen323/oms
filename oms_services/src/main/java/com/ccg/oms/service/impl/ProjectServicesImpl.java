package com.ccg.oms.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ccg.oms.common.data.project.ProjectTemplate;
import com.ccg.oms.common.data.project.ProjectUserRoleType;
import com.ccg.oms.common.data.project.TaskTemplate;
import com.ccg.oms.dao.entiry.project.Project;
import com.ccg.oms.dao.entiry.project.ProjectUser;
import com.ccg.oms.dao.entiry.project.ProjectUserRoleTypeEntity;
import com.ccg.oms.dao.repository.project.ProjectRepository;
import com.ccg.oms.dao.repository.project.ProjectTemplateRepository;
import com.ccg.oms.dao.repository.project.ProjectUserRoleTypeRepository;
import com.ccg.oms.dao.repository.project.TaskTemplateRepository;
import com.ccg.oms.service.ProjectServices;
import com.ccg.oms.service.mapper.BeanAndEntityMapper;

@Service
public class ProjectServicesImpl implements ProjectServices{

	@Autowired
	ProjectRepository projectRepository;
	
	@Autowired
	ProjectTemplateRepository projectTemplateReposiroty;
	
	@Autowired
	ProjectUserRoleTypeRepository userRoleRepository;
	
	@Autowired
	TaskTemplateRepository taskTemplateRepository;
	
	//ProjectUserRoleTypeEntityMapper mapper = ProjectUserRoleTypeEntityMapper.INSTANCE;
	
	@Transactional
	public void findProjectById(int id) {
		
		Project proj = projectRepository.findOne(1);
		
		System.out.println(proj.getName());
		List<ProjectUser> users = proj.getUsers();
		for(ProjectUser user : users){
			System.out.println(user.getUsername());
		}
	}


	public List<ProjectUserRoleType> getProjectUserRoleTypes() {
		Iterable<ProjectUserRoleTypeEntity> roleTypes = userRoleRepository.findAll();
		
		List<ProjectUserRoleType> typeList = new ArrayList<ProjectUserRoleType>();
		
		for (ProjectUserRoleTypeEntity entity : roleTypes) {
			ProjectUserRoleType bean = BeanAndEntityMapper.INSTANCE.entityToBean(entity);
			typeList.add(bean);
		}
		return typeList;
	}

	public void saveProjectUserRoleType(ProjectUserRoleType roleType) {
		ProjectUserRoleTypeEntity entity = BeanAndEntityMapper.INSTANCE.beanToEntity(roleType);
		ProjectUserRoleTypeEntity record = userRoleRepository.findOne(entity.getRoletype());
		if(record == null){
			userRoleRepository.save(entity);
		}else{
			record = BeanAndEntityMapper.INSTANCE.copyEntity(entity);
			userRoleRepository.save(record);
		}
	}


	public List<ProjectTemplate> getProjectTemplate() {
		// TODO Auto-generated method stub
		return null;
	}


	public ProjectTemplate getProjectTemplateById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}


	public void saveProjectTemplate(ProjectTemplate projectTemplate) {
		// TODO Auto-generated method stub
		
	}


	public void deleteProjectTemplate(Integer id) {
		// TODO Auto-generated method stub
		
	}


	public List<TaskTemplate> getTaskTemplate() {
		// TODO Auto-generated method stub
		return null;
	}


	public TaskTemplate getTaskTemplateById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}


	public void saveTaskTemplate(TaskTemplate taskTemplate) {
		// TODO Auto-generated method stub
		
	}


	public void deleteTaskTemplate(Integer id) {
		// TODO Auto-generated method stub
		
	}



}
