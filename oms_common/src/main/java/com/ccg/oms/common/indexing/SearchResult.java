package com.ccg.oms.common.indexing;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class SearchResult {
	ResultDoc docuement;
	String q;
	public ResultDoc getDocuement() {
		return docuement;
	}
	public void setDocuement(ResultDoc docuement) {
		this.docuement = docuement;
	}
	public String getQ() {
		return q;
	}
	public void setQ(String q) {
		this.q = q;
	}
	

}

