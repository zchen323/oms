package com.ccg.oms.dao.entiry;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="project")
public class Project {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String name;
	//private String category;
	private String description;
	
//	@Enumerated(EnumType.STRING)
//	private ProjectStatus status;
//	
//	private Customer customer;
//	
//	private boolean byPrime;
//	private String primeName;
//	
//	private Date proposalDueDate;
//	private Date startDate;
//	private Date completeDate;
//	private Date lastDateDate;
//	
//	private User owner;
//	private User createdBy;
//	private List<User> teamMembers;
	
	
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
//	public String getCategory() {
//		return category;
//	}
//	public void setCategory(String category) {
//		this.category = category;
//	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
//	public ProjectStatus getStatus() {
//		return status;
//	}
//	public void setStatus(ProjectStatus status) {
//		this.status = status;
//	}
//	public Customer getCustomer() {
//		return customer;
//	}
//	public void setCustomer(Customer customer) {
//		this.customer = customer;
//	}
//	public boolean isByPrime() {
//		return byPrime;
//	}
//	public void setByPrime(boolean byPrime) {
//		this.byPrime = byPrime;
//	}
//	public String getPrimeName() {
//		return primeName;
//	}
//	public void setPrimeName(String primeName) {
//		this.primeName = primeName;
//	}
//	public Date getProposalDueDate() {
//		return proposalDueDate;
//	}
//	public void setProposalDueDate(Date proposalDueDate) {
//		this.proposalDueDate = proposalDueDate;
//	}
//	public Date getStartDate() {
//		return startDate;
//	}
//	public void setStartDate(Date startDate) {
//		this.startDate = startDate;
//	}
//	public Date getCompleteDate() {
//		return completeDate;
//	}
//	public void setCompleteDate(Date completeDate) {
//		this.completeDate = completeDate;
//	}
//	public Date getLastDateDate() {
//		return lastDateDate;
//	}
//	public void setLastDateDate(Date lastDateDate) {
//		this.lastDateDate = lastDateDate;
//	}
//	public User getOwner() {
//		return owner;
//	}
//	public void setOwner(User owner) {
//		this.owner = owner;
//	}
//	public User getCreatedBy() {
//		return createdBy;
//	}
//	public void setCreatedBy(User createdBy) {
//		this.createdBy = createdBy;
//	}
//	public List<User> getTeamMembers() {
//		return teamMembers;
//	}
//	public void setTeamMembers(List<User> teamMembers) {
//		this.teamMembers = teamMembers;
//	}
}

/*
 * http://tomee.apache.org/examples-trunk/jpa-enumerated/README.html
 */
