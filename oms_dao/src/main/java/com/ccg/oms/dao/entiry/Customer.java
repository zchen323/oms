package com.ccg.oms.dao.entiry;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

//@Entity
public class Customer {
	@Id
	@GeneratedValue
	private int id;
	
	private String agency;
	private String organization;
	private String subOrganization;
	private String phoneNumber;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAgency() {
		return agency;
	}
	public void setAgency(String agency) {
		this.agency = agency;
	}
	public String getOrganization() {
		return organization;
	}
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	public String getSubOrganization() {
		return subOrganization;
	}
	public void setSubOrganization(String subOrganization) {
		this.subOrganization = subOrganization;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	
}
