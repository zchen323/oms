package com.ccg.oms.common.data.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * this class is used to hold create new user data from
 * ajax cal
 */		
@JsonInclude(Include.NON_NULL)
public class NewUser {
	//{"user":"q","pass":"q","name":"q","company":"q","email":"","iscontractor":false,"role":"q","fullaccess":false}
	private String user;
	private String pass;
	private String verify;
	private String name;
	private String company;
	private String email;
	private Boolean iscontractor;
	private Boolean fullaccess;
	private String role;
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getVerify() {
		return verify;
	}
	public void setVerify(String verify) {
		this.verify = verify;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Boolean getIscontractor() {
		return iscontractor;
	}
	public void setIscontractor(Boolean iscontractor) {
		this.iscontractor = iscontractor;
	}
	public Boolean getFullaccess() {
		return fullaccess;
	}
	public void setFullaccess(Boolean fullaccess) {
		this.fullaccess = fullaccess;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	
}
