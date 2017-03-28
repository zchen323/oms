package com.ccg.oms.common.data.document.solr;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Doc {
	private String id;
	private String name;
	private String last_modified;
	private Date lastModified;
	private String author;
	private List<String> content_type = new ArrayList<String>();
	
	public String getId() {		
		if(id != null && id.startsWith("document")){
			int p = id.indexOf("_");
			id = id.substring(p + 1);
		}		
		return id;
	}
	public void setId(String id) {
		if(id != null && id.startsWith("document")){
			int p = id.indexOf("_");
			id = id.substring(p + 1);
		}
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
