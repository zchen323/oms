package com.ccg.oms.service;

import java.util.List;

import com.ccg.oms.common.data.document.DocType;
import com.ccg.oms.common.data.project.ProjectTemplate;
import com.ccg.oms.common.data.project.ProjectUserRoleType;
import com.ccg.oms.common.data.project.TaskTemplate;
import com.ccg.oms.common.data.project.TaskTemplateConfig;

public interface ProjectAdminServices {
	void findProjectById(int id);
	
	List<ProjectUserRoleType> getProjectUserRoleTypes();
	void saveProjectUserRoleType(ProjectUserRoleType roleType);
	void deleteProjectUserRoleType(String roleTpeId);
	
	
	List<ProjectTemplate> getProjectTemplate();
	ProjectTemplate getProjectTemplateById(Integer id);
	void saveProjectTemplate(ProjectTemplate projectTemplate);
	void deleteProjectTemplate(Integer id);
	void saveProjectTtemplateConfig(Integer id, String configString);
	
	
	List<TaskTemplate> getTaskTemplate();
	TaskTemplate getTaskTemplateById(Integer id);
	void saveTaskTemplate(TaskTemplate taskTemplate);
	void deleteTaskTemplate(Integer id);
	void saveTaskTtemplateConfig(Integer id, String configString);
	
	List<DocType> getDocTypes();
	void saveDoctype(DocType docType);
	void deleteDoctype(String docTpeId);

	
}
