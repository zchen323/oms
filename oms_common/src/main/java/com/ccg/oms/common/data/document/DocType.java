package com.ccg.oms.common.data.document;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class DocType {
	
	private String doctype;
	private String description;
	private String sampleURL;
	private Timestamp createdTS;
	private String createdBy;
	
	
	public String getDoctype() {
		return doctype;
	}
	public void setDoctype(String doctype) {
		this.doctype = doctype;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getSampleURL() {
		return sampleURL;
	}
	public void setSampleURL(String sampleURL) {
		this.sampleURL = sampleURL;
	}
	public Timestamp getCreatedTS() {
		return createdTS;
	}
	public void setCreatedTS(Timestamp createdTS) {
		this.createdTS = createdTS;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
}
