package com.ccg.oms.common.data.project;

import java.util.LinkedList;
import java.util.List;

public class Task {
	private Integer id;
	private String name;
	private String description;
	private String status;
	private List<TaskDoc> docs;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	
}
