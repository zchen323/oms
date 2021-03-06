package com.ccg.oms.common.data.admin;

import java.util.List;

import com.ccg.oms.common.data.document.DocType;
import com.ccg.oms.common.data.project.ProjectTemplate;
import com.ccg.oms.common.data.project.ProjectUserRoleType;
import com.ccg.oms.common.data.project.TaskTemplate;
import com.ccg.oms.common.data.user.User;
import com.ccg.oms.common.data.user.UserInfo;

public class Admin {
	
	private List<UserInfo> users;
	private List<DocType> docTypes;
	private List<ProjectUserRoleType> projectUserRoleTypes;
	private List<ProjectTemplate> prjectTemplates;
	private List<TaskTemplate> taskTemplates;
	private UserInfo currentUser;
	
	public UserInfo getCurrentUser() {
		return currentUser;
	}
	public void setCurrentUser(UserInfo currentUser) {
		this.currentUser = currentUser;
	}
	public List<UserInfo> getUsers() {
		return users;
	}
	public void setUsers(List<UserInfo> users) {
		this.users = users;
	}
	public List<DocType> getDocTypes() {
		return docTypes;
	}
	public void setDocTypes(List<DocType> docTypes) {
		this.docTypes = docTypes;
	}
	public List<ProjectUserRoleType> getProjectUserRoleTypes() {
		return projectUserRoleTypes;
	}
	public void setProjectUserRoleTypes(List<ProjectUserRoleType> projectUserRoleTypes) {
		this.projectUserRoleTypes = projectUserRoleTypes;
	}
	public List<ProjectTemplate> getPrjectTemplates() {
		return prjectTemplates;
	}
	public void setPrjectTemplates(List<ProjectTemplate> prjectTemplates) {
		this.prjectTemplates = prjectTemplates;
	}
	public List<TaskTemplate> getTaskTemplates() {
		return taskTemplates;
	}
	public void setTaskTemplates(List<TaskTemplate> taskTemplates) {
		this.taskTemplates = taskTemplates;
	}
}
