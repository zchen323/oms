package com.ccg.oms.dao.entiry.user;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="users")
public class UserEntity2 {
	
	private String username;
	private String password;
	
	private String email;
	private Set<UserRoleEntity2> roles;
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
	
	@OneToMany(mappedBy="user", cascade=CascadeType.REMOVE)
	public Set<UserRoleEntity2> getRoles() {
		return roles;
	}
	public void setRoles(Set<UserRoleEntity2> roles) {
		this.roles = roles;
	}
	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
}
