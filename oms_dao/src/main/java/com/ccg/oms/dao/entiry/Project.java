package com.ccg.oms.dao.entiry;

import java.sql.Date;
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
public class Project {

	private int id;
	private String name;
	private String category;
	private String description;
	private String status;
	
	private Boolean prime;
	private String primeName;
	private String primeContactInfo;
	
	private String createdBy;
	
	private Timestamp createdDate;
	private Timestamp dueDate;
	private Timestamp completedDate;
	private Timestamp lastUpdateDate;
	
	private List<ProjectUser> users;

	
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
	public Boolean isPrime() {
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
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
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
	
	@OneToMany(mappedBy="project", cascade=CascadeType.ALL)
	public List<ProjectUser> getUsers() {
		return users;
	}
	public void setUsers(List<ProjectUser> users) {
		this.users = users;
	}
	
}

/*
 * http://tomee.apache.org/examples-trunk/jpa-enumerated/README.html
 */
