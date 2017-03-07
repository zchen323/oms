package com.ccg.oms.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ccg.oms.common.data.project.Project;
import com.ccg.oms.common.data.project.ProjectInfo;
import com.ccg.oms.common.data.project.ProjectUser;
import com.ccg.oms.common.data.project.Task;
import com.ccg.oms.common.data.project.TaskDoc;
import com.ccg.oms.common.data.project.TaskNote;
import com.ccg.oms.dao.entiry.project.ProjectEntity;
import com.ccg.oms.dao.entiry.project.ProjectUserEntity;
import com.ccg.oms.dao.entiry.project.TaskDocEntity;
import com.ccg.oms.dao.entiry.project.TaskEntity;
import com.ccg.oms.dao.entiry.project.TaskNoteEntity;
import com.ccg.oms.dao.entiry.user.UserDetailEntity;
import com.ccg.oms.dao.repository.project.ProjectRepository;
import com.ccg.oms.dao.repository.project.ProjectUserRepository;
import com.ccg.oms.dao.repository.project.TaskDocRepository;
import com.ccg.oms.dao.repository.project.TaskNoteRepository;
import com.ccg.oms.dao.repository.project.TaskRepository;
import com.ccg.oms.dao.repository.user.UserDetailRepository;
import com.ccg.oms.service.ProjectAdminServices;
import com.ccg.oms.service.ProjectServices;
import com.ccg.oms.service.mapper.ProjectMapper;
import com.ccg.oms.service.mapper.UserMapper;

@Service
public class ProjectServicesImpl implements ProjectServices{

	@Autowired
	ProjectRepository projectRepository;
	
	@Autowired
	TaskRepository taskRepository;
	
	@Autowired
	ProjectAdminServices projectAdminServices;
	
	@Autowired
	TaskDocRepository taskDocRepository;
	
	@Autowired
	TaskNoteRepository taskNoteRepository;
	
	@Autowired
	ProjectUserRepository projectUserRepository;
	
	@Autowired
	UserDetailRepository userDetailRepository;
	
	@Override
	@Transactional
	public void createNewProject(ProjectInfo projectInfo) {
		Project project = projectInfo.getProjectInfo();
		ProjectEntity entity = ProjectMapper.toEntity(project);
		if(entity.getStatus() == null);
		{
			entity.setStatus("new");
		}
		projectRepository.save(entity);
		
		System.out.println(entity.getId());
		List<Task> tasks = projectInfo.getTasks();
		
		for(Task task: tasks){
			TaskEntity te = ProjectMapper.toEntity(task);
			te.setProjectId(entity.getId());
			te.setId(null);
			te.setCreatedDate(new Timestamp(System.currentTimeMillis()));
			taskRepository.save(te);
			
			List<TaskDoc> taskDocs = task.getDocs();
			for(TaskDoc taskDoc : taskDocs){
				TaskDocEntity tde = ProjectMapper.toEntity(taskDoc);
				tde.setDocType(taskDoc.getDoctype());
				tde.setTaskId(te.getId());
				taskDocRepository.save(tde);				
			}
		}
		
//		Integer projectTemplateId = project.getProjTempId();
//		projectAdminServices.getProjectTemplateById(projectTemplateId);
//		TaskEntity taskEntity = new TaskEntity();
//		taskEntity.
		
		
	}

	@Override
	public ProjectInfo findProjectInfo(Integer id) {
		ProjectEntity projEntity = projectRepository.findOne(id);
		return getProjectInfo(projEntity);
	}

	@Transactional
	private ProjectInfo getProjectInfo(ProjectEntity projEntity){
		
		ProjectInfo projInfo = new ProjectInfo();
		Project project = null;
		List<Task> tasks = new LinkedList<Task>();

		//projEntity = projectRepository.findOne(id);
		project = ProjectMapper.fromEntity(projEntity);
		
		List<TaskEntity> taskEntities = taskRepository.findByProjectId(projEntity.getId());
		for(TaskEntity taskEntity : taskEntities){
			Task task = ProjectMapper.fromEntity(taskEntity);
			tasks.add(task);
			
			List<TaskDocEntity> taskDocEntities = taskDocRepository.findByTaskIdOrderByIdDesc(task.getId());
			for(TaskDocEntity taskDocEntity : taskDocEntities){
				TaskDoc taskDoc = ProjectMapper.fromEntity(taskDocEntity);
				task.getDocs().add(taskDoc);
			}
			
			List<TaskNoteEntity> taskNoteEntities = taskNoteRepository.findByTaskIdOrderByIdDesc(task.getId());
			for(TaskNoteEntity taskNoteEntity : taskNoteEntities){
				TaskNote taskNote = ProjectMapper.fromEntity(taskNoteEntity);
				task.getNotes().add(taskNote);
			}			
		}
		
		projInfo.setProjectUsers(findProjectUserByProjectId(projEntity.getId()));
		
		projInfo.setProjectInfo(project);
		projInfo.setTasks(tasks);
		return projInfo;
	}
	
	@Override
	public List<ProjectInfo> findAllProjectInfo() {
		List<ProjectInfo> info = new LinkedList<ProjectInfo>();
		
		Iterable<ProjectEntity> projectEntities = projectRepository.findAll();
		for(ProjectEntity projectEntity : projectEntities){
			info.add(this.getProjectInfo(projectEntity));
		}
		return info;
	}
	
	@Override
	public List<Project> searchByName(String nameContains) {
		List<Project> projects = new LinkedList<Project>();
		
		List<ProjectEntity> entities = projectRepository.findByNameContainingOrderByIdDesc(nameContains);
		for(ProjectEntity entity : entities){
			Project project = ProjectMapper.fromEntity(entity);
			projects.add(project);
		}
		return projects;
	}

	@Override
	public List<ProjectUser> findProjectUserByProjectId(Integer projectId){
		List<ProjectUser> users = new ArrayList<ProjectUser>();
		List<ProjectUserEntity> entities = projectUserRepository.findByProjectId(projectId);
		for(ProjectUserEntity entity : entities){
			users.add(UserMapper.fromEntity(entity));
		}
		return users;
	}
	
	@Override
	public void addTaskComment(TaskNote taskNote) {
		TaskNoteEntity entity = ProjectMapper.toEntity(taskNote);
		taskNoteRepository.save(entity);		
	}

	@Override
	public void updateTask(Task task) {	
		TaskEntity entity = taskRepository.findOne(task.getId());
		if(entity != null){
			entity.setOwner(task.getOwner());
			entity.setStatus(task.getStatus());
			entity.setDueDate(task.getTargetTimestamp());
			taskRepository.save(entity);
		}
	}

	@Override
	public void addProjectUser(ProjectUser user) {
		ProjectUserEntity entity = UserMapper.toEntity(user);
		if(entity.getUsername() == null){
			String userId = entity.getUserId();
			UserDetailEntity userEntity = userDetailRepository.findOne(userId);
			entity.setUsername(userEntity.getName());
		}
		projectUserRepository.save(entity);		
	}

	@Override
	public void removeUserFromProject(Integer projectId, String userId, String role) {
		List<ProjectUserEntity> entities = projectUserRepository.findByProjectIdAndUserIdAndRole(projectId, userId, role);
		projectUserRepository.delete(entities);	
	}

	@Override
	public List<Project> findFirst10() {
		
//		final PageRequest page = new PageRequest(
//				0, 20, Direction.DESC, "id");
//		
//		List<Project> projects = new ArrayList<Project>();
//		Iterable<ProjectEntity> projectRepository.findAll(page);
//		
//		ProjectEntity entities = projectRepository.findFirstByName();
//		//for(ProjectEntity entity : entities){
//			projects.add(ProjectMapper.fromEntity(entities));
//		//}
//		return projects;
		return null;
	}
}