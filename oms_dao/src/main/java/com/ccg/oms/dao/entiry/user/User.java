package com.ccg.oms.dao.entiry.user;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="users")
public class User {
	
	private String username;
	private String password;
	
	private String email;
//	private Set<String> roles;
	private Boolean enabled = true;
	
	@Id
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
//	@OneToMany(mappedBy="role", cascade=CascadeType.ALL)
//	public Set<String> getRoles() {
//		return roles;
//	}
//	
//	public void setRoles(Set<String> roles) {
//		this.roles = roles;
//	}
	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	
	
}
