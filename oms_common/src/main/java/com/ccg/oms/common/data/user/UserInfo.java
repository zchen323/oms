package com.ccg.oms.common.data.user;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class UserInfo {
	private String username;
	private Boolean fullaccess;
	private String name;
	private String createdTS;
	private Timestamp createdDate;
	private String email;
	private String role;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getFullaccess() {
		return "" + fullaccess;
	}
	public void setFullaccess(Boolean fullaccess) {
		this.fullaccess = fullaccess;
	}
	public boolean isFullaccess(){
		if(fullaccess == null)
			return false;
		return this.fullaccess;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCreatedTS() {
		return createdTS;
	}
	public void setCreatedTS(String createdTS) {
		this.createdTS = createdTS;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		if(createdDate != null){
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			this.createdTS = df.format(createdDate);
		}
		this.createdDate = createdDate;
	}
}
