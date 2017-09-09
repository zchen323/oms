package com.ccg.oms.common.indexing;

public class ResultType {
	private String docType;
	private int count;
	
	public String getDocType() {
		return docType;
	}
	public void setDocType(String docType) {
		this.docType = docType;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public void incrementCountBy1(){
		count++;
	}
}
