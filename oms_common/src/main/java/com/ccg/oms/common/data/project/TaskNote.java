package com.ccg.oms.common.data.project;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TaskNote {
	
	private Integer id;
	private Integer taskId;
	private String title;
	private String content;
	private String user;
	private String date;
	private Timestamp timestamp;
	
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getDate() {
		if(date == null && timestamp != null){
			DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
			date = df.format(timestamp);
		}
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Timestamp getTimestamp() {
		if(timestamp == null && date != null){
			DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
			try {
				timestamp = new Timestamp(df.parse(date).getTime());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return timestamp;
	}
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
}
