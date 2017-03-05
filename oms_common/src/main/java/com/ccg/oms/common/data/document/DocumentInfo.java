package com.ccg.oms.common.data.document;

import java.util.ArrayList;
import java.util.List;

import com.ccg.oms.common.data.project.Project;
import com.ccg.oms.common.data.project.ProjectInfo;

public class DocumentInfo {
	private Integer documentId;
	private String name;
	private String url;
	private List<ProjectInfo> project;
	
	public Integer getDocumentId() {
		return documentId;
	}
	public void setDocumentId(Integer documentId) {
		this.documentId = documentId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public List<ProjectInfo> getProject() {
		if(project == null)
			project = new ArrayList<ProjectInfo>();
		return project;
	}
	public void setProject(List<ProjectInfo> project) {
		this.project = project;
	}
	
	
}
