package com.ccg.oms.dao.entiry.user;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="userdetail")
public class UserDetailEntity {
	
	@Id
	private String username;
	private String name;
	private String company;
	private String phone;
	private String email;
	private String address;
	private Boolean isContractor;
	private Boolean fullAccess;
	private Timestamp createdTS;
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Boolean getIsContractor() {
		return isContractor;
	}
	public void setIsContractor(Boolean isContractor) {
		this.isContractor = isContractor;
	}
	public Boolean getFullAccess() {
		return fullAccess;
	}
	public void setFullAccess(Boolean fullAccess) {
		this.fullAccess = fullAccess;
	}
	public Timestamp getCreatedTS() {
		return createdTS;
	}
	public void setCreatedTS(Timestamp createdTS) {
		this.createdTS = createdTS;
	}
}
