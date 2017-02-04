package com.ccg.oms.common.data.project;

import java.util.List;

public class TaskTemplateConfig {
	
	private Integer taskTemplateId;
	private List<String> docTypes;
	
	public Integer getTaskTemplateId() {
		return taskTemplateId;
	}
	public void setTaskTemplateId(Integer taskTemplateId) {
		this.taskTemplateId = taskTemplateId;
	}
	public List<String> getDocTypes() {
		return docTypes;
	}
	public void setDocTypes(List<String> docTypes) {
		this.docTypes = docTypes;
	}
	

}
