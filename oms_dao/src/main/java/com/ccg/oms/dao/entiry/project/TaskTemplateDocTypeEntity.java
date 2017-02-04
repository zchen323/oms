package com.ccg.oms.dao.entiry.project;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tasktemplatedoctype")
public class TaskTemplateDocTypeEntity {
	
	private Integer id;
	private Integer taskTemplateId;
	private String docType;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getTaskTemplateId() {
		return taskTemplateId;
	}
	public void setTaskTemplateId(Integer taskTemplateId) {
		this.taskTemplateId = taskTemplateId;
	}
	
	@Column(name="doctype")
	public String getDocType() {
		return docType;
	}
	public void setDocType(String docType) {
		this.docType = docType;
	}
	
	

}
