package com.ccg.oms.service.impl;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ccg.oms.common.data.project.Project;
import com.ccg.oms.common.data.project.ProjectInfo;
import com.ccg.oms.common.data.project.Task;
import com.ccg.oms.common.data.project.TaskDoc;
import com.ccg.oms.common.data.project.TaskNote;
import com.ccg.oms.dao.entiry.project.ProjectEntity;
import com.ccg.oms.dao.entiry.project.TaskDocEntity;
import com.ccg.oms.dao.entiry.project.TaskEntity;
import com.ccg.oms.dao.entiry.project.TaskNoteEntity;
import com.ccg.oms.dao.repository.project.ProjectRepository;
import com.ccg.oms.dao.repository.project.TaskDocRepository;
import com.ccg.oms.dao.repository.project.TaskNoteRepository;
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
	
	@Autowired
	TaskDocRepository taskDocRepository;
	
	@Autowired
	TaskNoteRepository taskNoteRepository;
	
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
		
		List<ProjectEntity> entities = projectRepository.findByNameContaining(nameContains);
		for(ProjectEntity entity : entities){
			Project project = ProjectMapper.fromEntity(entity);
			projects.add(project);
		}
		return projects;
	}
	
	
	
	
}