package com.ccg.oms.common.data.project;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ProjectInfo {
	private Project projectInfo;
	private List<Task> tasks;
	private List<ProjectUser> projectUsers;
	
	public Project getProjectInfo() {
		return projectInfo;
	}
	public void setProjectInfo(Project projectInfo) {
		this.projectInfo = projectInfo;
	}
	public List<Task> getTasks() {
		return tasks;
	}
	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}
	public List<ProjectUser> getProjectUsers() {
		return projectUsers;
	}
	public void setProjectUsers(List<ProjectUser> projectUsers) {
		this.projectUsers = projectUsers;
	}
}
