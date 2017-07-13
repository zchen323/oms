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
import com.ccg.oms.dao.entiry.project.TaskTemplateEntity;
import com.ccg.oms.dao.entiry.user.UserDetailEntity;
import com.ccg.oms.dao.repository.project.ProjectRepository;
import com.ccg.oms.dao.repository.project.ProjectUserRepository;
import com.ccg.oms.dao.repository.project.TaskDocRepository;
import com.ccg.oms.dao.repository.project.TaskNoteRepository;
import com.ccg.oms.dao.repository.project.TaskRepository;
import com.ccg.oms.dao.repository.project.TaskTemplateRepository;
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
	TaskTemplateRepository taskTemplateRepository;
	
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
		project.setProjId(entity.getId());
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
		
		List<TaskEntity> taskEntities = taskRepository.findByProjectIdOrderBySeq(projEntity.getId());
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
	public void addTask(Task task) {
		Integer taskTempId = task.getTaskTempId();
		if(taskTempId != null){
			TaskTemplateEntity templateEntity = taskTemplateRepository.findOne(taskTempId);
			if(templateEntity != null){
				task.setName(templateEntity.getName());
				System.out.println(">>>> taskName: " + task.getName());
				task.setStatus("Not Started");
			}else{
				throw new RuntimeException("Task teamplate: " + taskTempId + "not found");
			}
		}
		
		TaskEntity entity = new TaskEntity();
		entity.setDueDate(task.getTargetTimestamp());
		entity.setCreatedDate(new Timestamp(System.currentTimeMillis()));
		entity.setDescription(task.getDescription());
		entity.setName(task.getName());
		entity.setOwner(task.getOwner());
		entity.setProjectId(task.getProjectId());
		entity.setStatus("Not Started");
		
		taskRepository.save(entity);
		
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
		
		final PageRequest pageRequest = new PageRequest(
				0, 10, Direction.DESC, "id");
		
		List<Project> projects = new ArrayList<Project>();
		Iterable<ProjectEntity> entities = projectRepository.findAll(pageRequest);
		
		for(ProjectEntity entity : entities){
			projects.add(ProjectMapper.fromEntity(entity));
		}
		return projects;
		//return null;
	}

	@Override
	public List<TaskDoc> findTaskDocByTaskId(Integer taskId) {
		// TODO Auto-generated method stub
		List<TaskDoc> result = new ArrayList<TaskDoc>();
		List<TaskDocEntity> entities = taskDocRepository.findByTaskIdOrderByIdDesc(taskId);
		for(TaskDocEntity entity : entities){
			result.add(ProjectMapper.fromEntity(entity));
		}
		return result;
	}
    @Override
    public List<TaskNote> findTaskNotesByTaskId(Integer taskId) {
        // TODO Auto-generated method stub
        List<TaskNoteEntity> tmp=taskNoteRepository.findByTaskIdOrderByIdDesc(taskId);
        List<TaskNote> res=new ArrayList<TaskNote>();
        for(TaskNoteEntity e:tmp)
        {
            res.add(ProjectMapper.fromEntity(e));
        }
        return res;
    }

	@Override
	public void updateProject(Project project) {
		ProjectEntity entity = projectRepository.findOne(project.getProjId());		

		ProjectMapper.copyToEntity(project, entity);
		entity.setLastUpdateDate(new Timestamp(System.currentTimeMillis()));
		if("In Progress".equals(entity.getStatus())){
			entity.setStartDate(new Timestamp(System.currentTimeMillis()));
		}else if("Completed".equals(entity.getStatus())){
			entity.setCompletedDate(new Timestamp(System.currentTimeMillis()));
		}
		projectRepository.save(entity);		
	}

	@Override
	public void deleteTask(Integer taskId) {
		TaskEntity entity = taskRepository.findOne(taskId);
		taskRepository.delete(entity);
	}

	@Override
	public void updateTaskSeq(int taskId, int taskSeq) {
		TaskEntity entity = taskRepository.findOne(taskId);
		entity.setSeq(taskSeq);
		taskRepository.save(entity);		
	}

}