package com.ccg.oms.dao.entiry.user;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="user_roles")
public class UserRoleEntity2 {
	
	private int user_role_id;
	private UserEntity2 user;
	private String role;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getUser_role_id() {
		return user_role_id;
	}
	public void setUser_role_id(int user_role_id) {
		this.user_role_id = user_role_id;
	}
	
	@ManyToOne
	@JoinColumn(name="username")
	public UserEntity2 getUser() {
		return user;
	}
	public void setUser(UserEntity2 user) {
		this.user = user;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
}
