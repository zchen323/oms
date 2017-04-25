package com.ccg.oms.common.indexing;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


@JsonInclude(Include.NON_NULL)
public class ResultDoc {
	Integer id;
	String nodetext="";
	@JsonProperty("text")
	public String getNodetext() {
		String res="["+id+"]";
		if(title!=null)
		{
			res=res+"- ["+title+"]";
		}
		return res;
	}
	public void setNodetext(String nodetext) {
		this.nodetext = nodetext;
	}

	String title;
	boolean leaf=false;
	boolean expanded=true;
	public boolean isExpanded() {
		return expanded;
	}
	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}
	public boolean isLeaf() {
		return leaf;
	}
	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}
	
	List<Doc> categories = new ArrayList<Doc>();
	@JsonProperty("indexID")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@JsonProperty("children")
	public List<Doc> getCategories() {
		return categories;
	}
	public void setCategories(List<Doc> categories) {
		this.categories = categories;
	}
}
