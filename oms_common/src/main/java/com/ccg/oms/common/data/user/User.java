package com.ccg.oms.common.data.user;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class User {

	protected String username;
	protected String email;
	protected Set<String> roles;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Set<String> getRoles() {
		if(roles == null){
			roles = new HashSet<String>();
		}
		return roles;
	}
	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}
}
