package com.ccg.oms.service;

import java.util.List;

import com.ccg.oms.common.data.project.Project;
import com.ccg.oms.common.data.project.ProjectInfo;
import com.ccg.oms.common.data.project.ProjectUser;
import com.ccg.oms.common.data.project.Task;
import com.ccg.oms.common.data.project.TaskDoc;
import com.ccg.oms.common.data.project.TaskNote;

public interface ProjectServices {
	void createNewProject(ProjectInfo project);
	ProjectInfo findProjectInfo(Integer id);
	List<ProjectInfo> findAllProjectInfo();
	List<Project> searchByName(String nameContains);
	void addTaskComment(TaskNote taskNote);
	void updateTask(Task task);
	void updateTaskSeq(int taskId, int taskSeq);
	void addTask(Task task);
	void deleteTask(Integer taskId);
	List<ProjectUser> findProjectUserByProjectId(Integer projectId);
	void addProjectUser(ProjectUser user);
	void removeUserFromProject(Integer projectId, String userId, String role);
	List<Project> findFirst10();
	List<TaskDoc> findTaskDocByTaskId(Integer taskId);
    List<TaskNote> findTaskNotesByTaskId(Integer taskId);
    List<TaskNote> findTaskNotesByProjectId(Integer projetId);
    void updateProject(Project project);
}
