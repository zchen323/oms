package com.ccg.oms.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ccg.oms.common.data.document.DocType;
import com.ccg.oms.common.data.project.ProjectTemplate;
import com.ccg.oms.common.data.project.ProjectUserRoleType;
import com.ccg.oms.common.data.project.TaskTemplate;
import com.ccg.oms.dao.entiry.document.DocTypeEntity;
import com.ccg.oms.dao.entiry.project.ProjectEntity;
import com.ccg.oms.dao.entiry.project.ProjectTemplateEntity;
import com.ccg.oms.dao.entiry.project.ProjectUserRoleTypeEntity;
import com.ccg.oms.dao.entiry.project.TaskEntity;
import com.ccg.oms.dao.entiry.project.TaskTemplateEntity;
import com.ccg.oms.dao.entiry.user.UserProjectHistoryEntity;
import com.ccg.oms.dao.repository.document.DocTypeRepository;
import com.ccg.oms.dao.repository.project.ProjectRepository;
import com.ccg.oms.dao.repository.project.ProjectTemplateRepository;
import com.ccg.oms.dao.repository.project.ProjectUserRoleTypeRepository;
import com.ccg.oms.dao.repository.project.TaskRepository;
import com.ccg.oms.dao.repository.project.TaskTemplateDocTypeRepository;
import com.ccg.oms.dao.repository.project.TaskTemplateRepository;
import com.ccg.oms.dao.repository.user.UserProjectHistoryRepository;
import com.ccg.oms.service.ProjectAdminServices;
import com.ccg.oms.service.mapper.BeanAndEntityMapper;

@Service
public class ProjectAdminServicesImpl implements ProjectAdminServices{

	@Autowired
	ProjectRepository projectRepository;
	
	@Autowired
	TaskRepository taskRepository;
	@Autowired
	UserProjectHistoryRepository userProjectHistoryRepository;
	
	@Autowired
	ProjectTemplateRepository projectTemplateReposiroty;
	
	@Autowired
	ProjectUserRoleTypeRepository userRoleRepository;
	
	@Autowired
	TaskTemplateRepository taskTemplateRepository;
	
	@Autowired
	DocTypeRepository docTypeRepository;
	
	@Autowired
	TaskTemplateDocTypeRepository taskTemplateDocTypeRepository;
	
	//ProjectUserRoleTypeEntityMapper mapper = ProjectUserRoleTypeEntityMapper.INSTANCE;
	
	@Transactional
	public void findProjectById(int id) {
		
		ProjectEntity proj = projectRepository.findOne(1);
		
		System.out.println(proj.getName());
		//List<ProjectUserEntity> users = proj.getUsers();
		//for(ProjectUserEntity user : users){
		//	System.out.println(user.getUsername());
		//}
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
			entity.setCreatedTS(new Timestamp(System.currentTimeMillis()));
			userRoleRepository.save(entity);
		}else{
			BeanUtils.copyProperties(entity, record);
			userRoleRepository.save(record);
		}
	}
	
	@Override
	public void deleteProjectUserRoleType(String roleTypeId) {
		ProjectUserRoleTypeEntity record = userRoleRepository.findOne(roleTypeId);
		userRoleRepository.delete(record);	
	}


	public List<ProjectTemplate> getProjectTemplate() {
		Iterable<ProjectTemplateEntity> entityList = projectTemplateReposiroty.findAll();
		List<ProjectTemplate> results = new ArrayList<ProjectTemplate>();
		for(ProjectTemplateEntity entity : entityList){
			results.add(BeanAndEntityMapper.INSTANCE.entityToBean(entity));
		}
		return results;
	}

	public ProjectTemplate getProjectTemplateById(Integer id) {
		return BeanAndEntityMapper.INSTANCE.entityToBean(
				projectTemplateReposiroty.findOne(id));
	}


	public void saveProjectTemplate(ProjectTemplate projectTemplate) {
		ProjectTemplateEntity entity = BeanAndEntityMapper.INSTANCE.beanToEntity(projectTemplate);
		if(entity.getId() == null){
			// create new
			entity.setCreatedTS(new Timestamp(System.currentTimeMillis()));
			projectTemplateReposiroty.save(entity);
		}else{
			ProjectTemplateEntity record = projectTemplateReposiroty.findOne(entity.getId());
			if(record != null){
				// do update
				BeanUtils.copyProperties(entity, record);
				projectTemplateReposiroty.save(record);
				
			}else{
				// ??? create new
				entity.setCreatedTS(new Timestamp(System.currentTimeMillis()));
				projectTemplateReposiroty.save(entity);
			}
		}
	}


	public void deleteProjectTemplate(Integer id) {
		ProjectTemplateEntity entity = projectTemplateReposiroty.findOne(id);
		if(entity != null)
			projectTemplateReposiroty.delete(entity);		
	}


	public List<TaskTemplate> getTaskTemplate() {
		Iterable<TaskTemplateEntity> entities = taskTemplateRepository.findAll();
		List<TaskTemplate> results = new ArrayList<TaskTemplate>();
		for(TaskTemplateEntity entity : entities){
			results.add(BeanAndEntityMapper.INSTANCE.entityToBean(entity));
		}
		return results;
	}


	public TaskTemplate getTaskTemplateById(Integer id) {
		TaskTemplateEntity entity = taskTemplateRepository.findOne(id);
		if(entity != null){
			return BeanAndEntityMapper.INSTANCE.entityToBean(entity);
		}
		return null;
	}


	public void saveTaskTemplate(TaskTemplate taskTemplate) {
		TaskTemplateEntity entity = BeanAndEntityMapper.INSTANCE.beanToEntity(taskTemplate);
		if(entity.getId() == null){
			// create new
			entity.setCreatedTS(new Timestamp(System.currentTimeMillis()));
			taskTemplateRepository.save(entity);
		}else{
			TaskTemplateEntity record = taskTemplateRepository.findOne(entity.getId());
			if(record != null){
				BeanUtils.copyProperties(entity, record);
				// update
				taskTemplateRepository.save(record);
			}else{
				//?? create
				entity.setCreatedTS(new Timestamp(System.currentTimeMillis()));
				taskTemplateRepository.save(entity);
			}
		}
	}


	public void deleteTaskTemplate(Integer id) {
		taskTemplateRepository.delete(id);
	}


	@Override
	public List<DocType> getDocTypes() {
		Iterable<DocTypeEntity> entities = docTypeRepository.findAll();
		List<DocType> results = new ArrayList<DocType>();
		for(DocTypeEntity entity : entities){
			results.add(BeanAndEntityMapper.INSTANCE.entityToBean(entity));
		}
		return results;
	}


	@Override
	public void saveDoctype(DocType docType) {
		DocTypeEntity entity = BeanAndEntityMapper.INSTANCE.beanToEntity(docType);
		DocTypeEntity record = docTypeRepository.findOne(entity.getDoctype());
		if(record == null){
			// create new
			entity.setCreatedTS(new Timestamp(System.currentTimeMillis()));
			docTypeRepository.save(entity);
		}else{
			record.setDescription(docType.getDescription());
			record.setSampleURL(docType.getSampleURL());
			docTypeRepository.save(record);
		}		
	}
	
	@Override
	public void deleteDoctype(String docTypeId) {
		DocTypeEntity record = docTypeRepository.findOne(docTypeId);
		if(record != null){
			docTypeRepository.delete(record);
		}else{
			throw new RuntimeException("Doc type: " + docTypeId + " is not found");
		}
	}



	@Override
	public void saveProjectTtemplateConfig(Integer id, String configString) {
		ProjectTemplateEntity entity = projectTemplateReposiroty.findOne(id);
		if(entity != null){
			entity.setConfig(configString);
			projectTemplateReposiroty.save(entity);
		}else{
			throw new RuntimeException("ProjectTemplate with ID: " + id + " does not exist");
		}
	}


	@Override
	public void saveTaskTtemplateConfig(Integer id, String configString) {
		TaskTemplateEntity entity = taskTemplateRepository.findOne(id);
		if(entity != null){
			entity.setConfig(configString);
			taskTemplateRepository.save(entity);
		}else{
			throw new RuntimeException("TaskTemplate with ID: " + id + " does not exist");
		}
		
	}


	@Override
	public void deleteProject(Integer projectId) {
		ProjectEntity entity = projectRepository.findOne(projectId);
		if(entity != null){
			projectRepository.delete(entity);
		}
		
		List<TaskEntity> taskList = taskRepository.findByProjectIdOrderBySeq(projectId);
		for(TaskEntity task : taskList){
			taskRepository.delete(task);
		}
		
		List<UserProjectHistoryEntity> projectHistoryList = userProjectHistoryRepository.findByProjectId(projectId);
		for(UserProjectHistoryEntity projHis : projectHistoryList){
			userProjectHistoryRepository.delete(projHis);
		}
	}




}
