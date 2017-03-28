package com.ccg.oms.common.data.document.solr;

import java.util.ArrayList;
import java.util.List;

public class Response {
	private int numFound;
	private int start;
	private List<Doc> docs = new ArrayList<Doc>();
	
	public int getNumFound() {
		return numFound;
	}
	public void setNumFound(int numFound) {
		this.numFound = numFound;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public List<Doc> getDocs() {
		return docs;
	}
	public void setDocs(List<Doc> docs) {
		this.docs = docs;
	}
	
	
}
