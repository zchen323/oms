package com.ccg.oms.common.data.project;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TaskDoc {
	private Integer id;
	private Integer taskId;
	private Integer documentId;
	private String doctype;
	private String name;
	private String uploadDate;
	private Timestamp uploadTimestamp;
	private String user;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	public String getDoctype() {
		return doctype;
	}

	public void setDoctype(String doctype) {
		this.doctype = doctype;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUploadDate() {
		if(uploadDate == null && uploadTimestamp != null ){
			DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
			uploadDate = df.format(uploadTimestamp);
		}		
		return uploadDate;
	}

	public void setUploadDate(String uploadDate) {
		this.uploadDate = uploadDate;
	}

	public Timestamp getUploadTimestamp() {
		if(uploadDate != null && uploadTimestamp == null){
			DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
			try {
				uploadTimestamp = new Timestamp(df.parse(uploadDate).getTime());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return uploadTimestamp;
	}

	public void setUploadTimestamp(Timestamp uploadTimestamp) {
		this.uploadTimestamp = uploadTimestamp;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public Integer getDocumentId() {
		return documentId;
	}

	public void setDocumentId(Integer documentId) {
		this.documentId = documentId;
	}
	
}
