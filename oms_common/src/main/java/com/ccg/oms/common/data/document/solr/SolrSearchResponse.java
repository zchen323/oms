package com.ccg.oms.common.data.document.solr;

public class SolrSearchResponse {
	ResponseHeader responseHeader;
	Response response;
	
	public ResponseHeader getResponseHeader() {
		return responseHeader;
	}
	public void setResponseHeader(ResponseHeader responseHeader) {
		this.responseHeader = responseHeader;
	}
	public Response getResponse() {
		return response;
	}
	public void setResponse(Response response) {
		this.response = response;
	}
	

}
