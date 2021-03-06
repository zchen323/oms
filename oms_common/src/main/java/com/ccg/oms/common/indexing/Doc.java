package com.ccg.oms.common.indexing;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonInclude(Include.NON_NULL)
public class Doc {
	String id;
	Integer documentId;
	String documentTitle;

	String categoryTitle;
	Integer startPage;
	Integer endPage;
	Integer startPosition;
	Integer endPosition;
	String text;
	
	public String toString()
	{
		String res=id+",";
		res=res+documentId+",";
		res=res+documentTitle+",";
		res=res+categoryTitle+",";
		res=res+startPage+",";
		res=res+endPage+",";
		res=res+startPosition+",";
		res=res+endPosition+",";
		return res;
	}
	public boolean isLeaf() {
		return leaf;
	}
	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}
	boolean leaf=true;
	
	public String getDocumentTitle() {
		return documentTitle;
	}
	public void setDocumentTitle(String documentTitle) {
		this.documentTitle = documentTitle;
	}
	@JsonProperty("indexID")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getDocumentId() {
		return documentId;
	}
	public void setDocumentId(Integer documentId) {
		this.documentId = documentId;
	}
	@JsonProperty("text")
	public String getCategoryTitle() {
		return categoryTitle;
	}
	public void setCategoryTitle(String categoryTitle) {
		this.categoryTitle = categoryTitle;
	}
	public Integer getStartPage() {
		return startPage;
	}
	public void setStartPage(Integer startPage) {
		this.startPage = startPage;
	}
	public Integer getEndPage() {
		return endPage;
	}
	public void setEndPage(Integer endPage) {
		this.endPage = endPage;
	}
	public Integer getStartPosition() {
		return startPosition;
	}
	public void setStartPosition(Integer startPosition) {
		this.startPosition = startPosition;
	}
	public Integer getEndPosition() {
		return endPosition;
	}
	public void setEndPosition(Integer endPosition) {
		this.endPosition = endPosition;
	}
	@JsonProperty("text1")
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
}
