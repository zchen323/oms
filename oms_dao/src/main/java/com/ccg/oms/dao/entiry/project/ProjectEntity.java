package com.ccg.oms.dao.entiry.project;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="project")
public class ProjectEntity {

	private int id;
	private String name;
	private String category;
	private String description;
	private String status;
	
	private Boolean prime;
	private String primeName;
	private String primeContactInfo;
	
	private String location;
	private String organization;
	private String agency;
	
	
	private String createdBy;
	
	private Timestamp startDate;
	private Timestamp createdDate;
	private Timestamp dueDate;
	private Timestamp completedDate;
	private Timestamp lastUpdateDate;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Boolean getPrime() {
		return prime;
	}
	public void setPrime(Boolean prime) {
		this.prime = prime;
	}
	public String getPrimeName() {
		return primeName;
	}
	public void setPrimeName(String primeName) {
		this.primeName = primeName;
	}
	public String getPrimeContactInfo() {
		return primeContactInfo;
	}
	public void setPrimeContactInfo(String primeContactInfo) {
		this.primeContactInfo = primeContactInfo;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getOrganization() {
		return organization;
	}
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	public String getAgency() {
		return agency;
	}
	public void setAgency(String agency) {
		this.agency = agency;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Timestamp getStartDate() {
		return startDate;
	}
	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	public Timestamp getDueDate() {
		return dueDate;
	}
	public void setDueDate(Timestamp dueDate) {
		this.dueDate = dueDate;
	}
	public Timestamp getCompletedDate() {
		return completedDate;
	}
	public void setCompletedDate(Timestamp completedDate) {
		this.completedDate = completedDate;
	}
	public Timestamp getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(Timestamp lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	

	
	
}

/*
 * http://tomee.apache.org/examples-trunk/jpa-enumerated/README.html
 */
