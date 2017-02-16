package com.ccg.oms.service.mapper;

import java.sql.Timestamp;

import com.ccg.oms.common.data.project.Project;
import com.ccg.oms.common.data.project.Task;
import com.ccg.oms.common.data.project.TaskDoc;
import com.ccg.oms.dao.entiry.project.ProjectEntity;
import com.ccg.oms.dao.entiry.project.TaskDocEntity;
import com.ccg.oms.dao.entiry.project.TaskEntity;

public class ProjectMapper {
	
	public static Project fromEntity(ProjectEntity entity){
		Project project = new Project();
		project.setContactoffice(entity.getPrimeContactInfo());
		project.setDueDate(entity.getDueDate());
		project.setIsPrimeProject(entity.getPrime());
		project.setPrimeName(entity.getPrimeName());
		project.setProjAgency(entity.getAgency());
		project.setProjcategory(entity.getCategory());
		project.setProjId(entity.getId());
		project.setProjloc(entity.getLocation());
		project.setProjName(entity.getName());
		project.setProjOrg(entity.getOrganization());
		//project.setProjTempId(projTempId);
		
		
		return project;
	}
	
	public static ProjectEntity toEntity(Project project){
		ProjectEntity entity = new ProjectEntity();
		entity.setAgency(project.getProjAgency());
		entity.setCategory(project.getProjcategory());
		//entity.setCompletedDate();
		//entity.setCreatedBy(createdBy);
		entity.setCreatedDate(new Timestamp(System.currentTimeMillis()));
		//entity.setDescription(description);
		entity.setDueDate(project.getDueDate());
		//entity.setId(id);
		entity.setLastUpdateDate(new Timestamp(System.currentTimeMillis()));
		entity.setLocation(project.getProjloc());
		entity.setName(project.getProjName());
		entity.setOrganization(project.getProjOrg());
		entity.setPrime(project.getIsPrimeProject());
		entity.setPrimeContactInfo(project.getContactoffice());
		entity.setPrimeName(project.getPrimeName());
		//entity.setStartDate(project.getp);
		//entity.setStatus(status);
		//entity.setUsers(users);
		return entity;
	}
	
	public static TaskEntity toEntity(Task task){
		TaskEntity entity = new TaskEntity();
		entity.setId(task.getId());
		entity.setName(task.getName());
		entity.setDescription(task.getDescription());
		entity.setStatus(task.getStatus());	
		return entity;
	}
	
	public static Task fromEntity(TaskEntity entity){
		Task task = new Task();
		task.setDescription(entity.getDescription());
		task.setName(entity.getName());
		task.setStatus(entity.getStatus());
		task.setId(entity.getId());
		return task;
	}
	
	public static TaskDocEntity toEntity(TaskDoc taskDoc){
		TaskDocEntity entity = new TaskDocEntity();
		entity.setDocType(taskDoc.getDoctype());
		entity.setId(taskDoc.getId());
		return entity;
	}
	
	public static TaskDoc fromEntity(TaskDocEntity entity){
		TaskDoc taskDoc = new TaskDoc();
		taskDoc.setId(entity.getId());
		taskDoc.setDoctype(entity.getDocType());
		return taskDoc;
	}
	
}
