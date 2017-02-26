package com.ccg.oms.common.data.project;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

public class Task {
	private Integer id;
	private Integer projectId;
	private String name;
	private String description;
	private String status;
	private String owner;
	private String targetDate;
	private Timestamp targetTimestamp;
	
	private List<TaskDoc> docs;
	private List<TaskNote> notes;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getProjectId() {
		return projectId;
	}
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public List<TaskDoc> getDocs() {
		if(docs == null)
			docs = new LinkedList<TaskDoc>();
		return docs;
	}
	public void setDocs(List<TaskDoc> docs) {
		this.docs = docs;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getTargetDate() {
		if(targetTimestamp != null && targetDate == null){
			DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
			targetDate = df.format(targetTimestamp);			
		}
		return targetDate;
	}
	public void setTargetDate(String targetDate) {
		this.targetDate = targetDate;
	}
	public Timestamp getTargetTimestamp() {
		if(targetTimestamp == null && targetDate != null){
			DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
			try {
				targetTimestamp = new Timestamp(df.parse(targetDate).getTime());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return targetTimestamp;
	}
	public void setTargetTimestamp(Timestamp targetTimestamp) {
		this.targetTimestamp = targetTimestamp;
	}
	public List<TaskNote> getNotes() {
		if(notes == null){
			notes = new LinkedList<TaskNote>();
		}
		return notes;
	}
	public void setNotes(List<TaskNote> notes) {
		this.notes = notes;
	}
}
