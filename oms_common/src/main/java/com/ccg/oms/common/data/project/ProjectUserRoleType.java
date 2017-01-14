package com.ccg.oms.common.data.project;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ProjectUserRoleType {
	
	private String roletype;
	private String description;
	private Timestamp createdTS;
	private String createdBy;
	private String status;
	private String roletypecol;
	
	public String getRoletype() {
		return roletype;
	}
	public void setRoletype(String roletype) {
		this.roletype = roletype;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Timestamp getCreatedTS() {
		return createdTS;
	}
	public void setCreatedTS(Timestamp createdTS) {
		this.createdTS = createdTS;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRoletypecol() {
		return roletypecol;
	}
	public void setRoletypecol(String roletypecol) {
		this.roletypecol = roletypecol;
	}	
}
