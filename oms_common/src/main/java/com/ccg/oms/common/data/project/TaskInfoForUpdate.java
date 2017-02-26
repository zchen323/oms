package com.ccg.oms.common.data.project;

import java.sql.Timestamp;

public class TaskInfoForUpdate {
	private Integer taskId;
	private String status;
	private String owner;
	private String targetDate;
	private Timestamp targetTimestamp;
	
	public Integer getTaskId() {
		return taskId;
	}
	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getTargetDate() {
		return targetDate;
	}
	public void setTargetDate(String targetDate) {
		this.targetDate = targetDate;
	}
	public Timestamp getTargetTimestamp() {
		return targetTimestamp;
	}
	public void setTargetTimestamp(Timestamp targetTimestamp) {
		this.targetTimestamp = targetTimestamp;
	}
}
