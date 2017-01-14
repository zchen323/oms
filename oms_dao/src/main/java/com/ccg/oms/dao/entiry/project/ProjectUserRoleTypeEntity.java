package com.ccg.oms.dao.entiry.project;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="roletype")
public class ProjectUserRoleTypeEntity {
	
	private String roletype;
	private String description;
	private Timestamp createdTS;
	private String createdBy;
	private String status;
	private String roletypecol;
	
	@Id
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
	
	@Column(columnDefinition = "char(1)")
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
