package com.ccg.oms.common.indexing;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ResultDoc {
	Integer id;
	String title;
	List<Doc> categories = new ArrayList<Doc>();
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
	public List<Doc> getCategories() {
		return categories;
	}
	public void setCategories(List<Doc> categories) {
		this.categories = categories;
	}
}
