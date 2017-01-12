package com.ccg.oms.dao.entiry.user;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="user_roles")
public class UserRole {
	
	private int user_role_id;
	private String username;
	private String role;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getUser_role_id() {
		return user_role_id;
	}
	
	public void setUser_role_id(int user_role_id) {
		this.user_role_id = user_role_id;
	}
		
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
}
