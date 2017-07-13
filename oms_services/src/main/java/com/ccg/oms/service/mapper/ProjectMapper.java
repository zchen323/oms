package com.ccg.oms.service.mapper;

import java.sql.Timestamp;

import com.ccg.oms.common.data.project.Project;
import com.ccg.oms.common.data.project.Task;
import com.ccg.oms.common.data.project.TaskDoc;
import com.ccg.oms.common.data.project.TaskNote;
import com.ccg.oms.dao.entiry.project.ProjectEntity;
import com.ccg.oms.dao.entiry.project.TaskDocEntity;
import com.ccg.oms.dao.entiry.project.TaskEntity;
import com.ccg.oms.dao.entiry.project.TaskNoteEntity;

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
		project.setProjStatus(entity.getStatus());
		project.setProjManager(entity.getManager());
		project.setDescription(entity.getDescription());
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
		entity.setDescription(project.getDescription());
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
		entity.setStatus(project.getProjStatus());
		entity.setManager(project.getProjManager());
		//entity.setUsers(users);
		return entity;
	}

	public static void copyToEntity(Project project, ProjectEntity entity){
		entity.setAgency(project.getProjAgency());
		entity.setCategory(project.getProjcategory());
		//entity.setCompletedDate();
		//entity.setCreatedBy(createdBy);
		entity.setCreatedDate(new Timestamp(System.currentTimeMillis()));
		entity.setDescription(project.getDescription());
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
		entity.setStatus(project.getProjStatus());
		entity.setManager(project.getProjManager());
		//entity.setUsers(users);
	}
	
	
	public static TaskEntity toEntity(Task task){
		TaskEntity entity = new TaskEntity();
		entity.setId(task.getId());
		entity.setName(task.getName());
		entity.setSeq(task.getSeq());
		entity.setDescription(task.getDescription());
		entity.setStatus(task.getStatus());	
		entity.setOwner(task.getOwner());
		entity.setDueDate(task.getTargetTimestamp());
		return entity;
	}
	
	public static Task fromEntity(TaskEntity entity){
		Task task = new Task();
		task.setDescription(entity.getDescription());
		task.setName(entity.getName());
		task.setSeq(entity.getSeq());
		task.setStatus(entity.getStatus());
		task.setId(entity.getId());
		task.setOwner(entity.getOwner());
		task.setTargetTimestamp(entity.getDueDate());
		task.setProjectId(entity.getProjectId());
		return task;
	}
	
	public static TaskDocEntity toEntity(TaskDoc taskDoc){
		TaskDocEntity entity = new TaskDocEntity();
		entity.setDocType(taskDoc.getDoctype());
		entity.setId(taskDoc.getId());
		entity.setName(taskDoc.getName());
		entity.setUploadDate(taskDoc.getUploadTimestamp());
		entity.setUser(taskDoc.getUser());
		entity.setDocumentId(taskDoc.getDocumentId());
		entity.setTaskId(taskDoc.getTaskId());
		
		return entity;
	}
	
	public static TaskDoc fromEntity(TaskDocEntity entity){
		TaskDoc taskDoc = new TaskDoc();
		taskDoc.setId(entity.getId());
		taskDoc.setDoctype(entity.getDocType());
		taskDoc.setUploadTimestamp(entity.getUploadDate());
		taskDoc.setUser(entity.getUser());
		taskDoc.setName(entity.getName());
		taskDoc.setTaskId(entity.getTaskId());
		taskDoc.setDocumentId(entity.getDocumentId());
		return taskDoc;
	}
	
	public static TaskNote fromEntity(TaskNoteEntity entity){
		TaskNote note = new TaskNote();
		note.setContent(entity.getContent());
		note.setTimestamp(entity.getCreatedTime());
		note.setId(entity.getId());
		note.setTaskId(entity.getTaskId());
		note.setUser(entity.getCreatedBy());
		
		return note;
	}
	
	public static TaskNoteEntity toEntity(TaskNote note){
		TaskNoteEntity entity = new TaskNoteEntity();
		entity.setContent(note.getContent());
		entity.setCreatedBy(note.getUser());
		entity.setCreatedTime(note.getTimestamp());
		entity.setTaskId(note.getTaskId());
		entity.setTitle(note.getTitle());
		return entity;
	}
	
}
