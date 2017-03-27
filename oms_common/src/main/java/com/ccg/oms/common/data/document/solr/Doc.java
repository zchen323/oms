package com.ccg.oms.common.data.document.solr;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Doc {
	private String id;
	private String last_modified;
	private Date lastModified;
	private String author;
	private List<String> content_type = new ArrayList<String>();
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLast_modified() {
		return last_modified;
	}
	public void setLast_modified(String last_modified) {
		this.last_modified = last_modified;
	}
	public Date getLastModified() {
		return lastModified;
	}
	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public List<String> getContent_type() {
		return content_type;
	}
	public void setContent_type(List<String> content_type) {
		this.content_type = content_type;
	}
}
