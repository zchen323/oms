package com.ccg.oms.common.data.project;

public class ProjectUser {
	
	private String userId;
	private String username;
	private Integer projectId;
	private String projectUserRole;
	private Boolean restricted;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Integer getProjectId() {
		return projectId;
	}
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	public String getProjectUserRole() {
		return projectUserRole;
	}
	public void setProjectUserRole(String projectUserRole) {
		this.projectUserRole = projectUserRole;
	}
	public Boolean getRestricted() {
		return restricted;
	}
	public void setRestricted(Boolean restricted) {
		this.restricted = restricted;
	}

	
}
