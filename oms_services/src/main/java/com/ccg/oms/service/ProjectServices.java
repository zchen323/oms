package com.ccg.oms.service;

import java.util.List;

import com.ccg.oms.common.data.project.ProjectTemplate;
import com.ccg.oms.common.data.project.ProjectUserRoleType;
import com.ccg.oms.common.data.project.TaskTemplate;

public interface ProjectServices {
	void findProjectById(int id);
	
	List<ProjectUserRoleType> getProjectUserRoleTypes();
	void saveProjectUserRoleType(ProjectUserRoleType roleType);
	
	List<ProjectTemplate> getProjectTemplate();
	ProjectTemplate getProjectTemplateById(Integer id);
	void saveProjectTemplate(ProjectTemplate projectTemplate);
	void deleteProjectTemplate(Integer id);
	
	List<TaskTemplate> getTaskTemplate();
	TaskTemplate getTaskTemplateById(Integer id);
	void saveTaskTemplate(TaskTemplate taskTemplate);
	void deleteTaskTemplate(Integer id);
	
}
