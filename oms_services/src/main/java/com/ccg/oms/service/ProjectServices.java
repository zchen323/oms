package com.ccg.oms.service;

import java.util.List;

import com.ccg.oms.common.data.project.Project;
import com.ccg.oms.common.data.project.ProjectInfo;
import com.ccg.oms.common.data.project.TaskNote;

public interface ProjectServices {
	void createNewProject(ProjectInfo project);
	ProjectInfo findProjectInfo(Integer id);
	List<ProjectInfo> findAllProjectInfo();
	List<Project> searchByName(String nameContains);
	void addTaskComment(TaskNote taskNote);
}
