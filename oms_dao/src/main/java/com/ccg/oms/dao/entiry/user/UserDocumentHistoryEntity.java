package com.ccg.oms.dao.entiry.user;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="userdocumenthistory")
public class UserDocumentHistoryEntity {
	private Integer id;
	private String userId;
	private Integer documentId;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Integer getDocumentId() {
		return documentId;
	}
	public void setDocumentId(Integer documentId) {
		this.documentId = documentId;
	}
}
