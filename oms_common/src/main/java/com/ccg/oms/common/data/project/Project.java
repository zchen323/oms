package com.ccg.oms.common.data.project;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Project {
	
	//{"projName":"New Project","projAgency":"aaaa","projOrg":"aaaa","projloc":"aa","contactoffice":"aa","projcategory":"aaa","isPrimeProject":"on","primeName":"aaa","projduedate":"02/11/2017","projTempId":8}
//	{  
//		   "projName":"New Project",
//		   "projAgency":"aaaa",
//		   "projOrg":"aaaa",
//		   "projloc":"aa",
//		   "contactoffice":"aa",
//		   "projcategory":"aaa",
//		   "isPrimeProject":true,
//		   "primeName":"aaa",
//		   "projduedate":"02/11/2017",
//		   "projTempId":8
//		}

	private Integer projId;
	private String projName;
	private String projAgency;
	private String projOrg;
	private String projloc;
	private String contactoffice;
	private String projcategory;
	private Boolean isPrimeProject;
	private String primeName;
	private String projduedate;
	private Timestamp dueDate;
	private Integer projTempId;
	private String projManager;
	private String projStatus;
	private String description;
	
	//private List<Task> tasks;
	
	public Integer getProjId() {
		return projId;
	}
	public void setProjId(Integer projId) {
		this.projId = projId;
	}
	public String getProjName() {
		return projName;
	}
	public void setProjName(String projName) {
		this.projName = projName;
	}
	public String getProjAgency() {
		return projAgency;
	}
	public void setProjAgency(String projAgency) {
		this.projAgency = projAgency;
	}
	public String getProjOrg() {
		return projOrg;
	}
	public void setProjOrg(String projOrg) {
		this.projOrg = projOrg;
	}
	public String getProjloc() {
		return projloc;
	}
	public void setProjloc(String projloc) {
		this.projloc = projloc;
	}
	public String getContactoffice() {
		return contactoffice;
	}
	public void setContactoffice(String contactoffice) {
		this.contactoffice = contactoffice;
	}
	public String getProjcategory() {
		return projcategory;
	}
	public void setProjcategory(String projcategory) {
		this.projcategory = projcategory;
	}
	public Boolean getIsPrimeProject() {
		return isPrimeProject;
	}
	public void setIsPrimeProject(Boolean isPrimeProject) {
		this.isPrimeProject = isPrimeProject;
	}
	public String getPrimeName() {
		return primeName;
	}
	public void setPrimeName(String primeName) {
		this.primeName = primeName;
	}
	public String getProjduedate() {
		if(projduedate == null && dueDate != null){
			DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
			projduedate = df.format(dueDate);			
		}
		return projduedate;
	}
	public void setProjduedata(String projduedate) {
		this.projduedate = projduedate;
	}
	public Timestamp getDueDate() {
		if(dueDate == null && projduedate != null){
			//DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
			try {
				dueDate = new Timestamp(df.parse(this.projduedate).getTime());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return dueDate;
	}
	public void setDueDate(Timestamp dueDate) {
		this.dueDate = dueDate;
	}
	public Integer getProjTempId() {
		return projTempId;
	}
	public void setProjTempId(Integer projTempId) {
		this.projTempId = projTempId;
	}

	public void setProjduedate(String projduedate) {
		this.projduedate = projduedate;
	}
	public String getProjManager() {
		return projManager;
	}
	public void setProjManager(String projManager) {
		this.projManager = projManager;
	}
	public String getProjStatus() {
		return projStatus;
	}
	public void setProjStatus(String projStatus) {
		this.projStatus = projStatus;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

}
