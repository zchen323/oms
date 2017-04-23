package com.ccg.oms.common.indexing;

import java.net.URLEncoder;
import java.util.List;

import com.ccg.oms.common.restclinet.SimpleRestClient;
import com.ccg.util.JSON;

public class IndexingHelper {
	
	public static void updateDocument(List<Doc> documents) throws Exception{
		SimpleRestClient client = new SimpleRestClient();
		// TODO put url in configuration file
		String urlString = "http://localhost:8083/solr/update/json?commit=true";
		String jsonPayload = JSON.toJson(documents);
		client.post(urlString, jsonPayload);		
	}
	
	public static void deleteDocument(List<String> indexingId) throws Exception{
		
		DeleteRequest deleteRequest = new DeleteRequest();
		deleteRequest.setDelete(indexingId);
		
		SimpleRestClient client = new SimpleRestClient();
		// TODO put url in configuration file
		String urlString = "http://localhost:8083/solr/update/json?commit=true";
		String jsonPayload = JSON.toJson(deleteRequest);
		client.post(urlString, jsonPayload);
		
	}
	
	public static List<Doc> search(String query) throws Exception{
		// TODO 
		/*
		String id;
		Integer documentId;
		String documentTitle;
		String categoryTitle;
		Integer startPage;
		Integer endPage;
		Integer startPosition;
		Integer endPosition;
		String text;
		*/
		String searURL = "http://72.177.234.240:8983/solr/select?q=" + URLEncoder.encode(query, "UTF-8") 
			+ "&fl=id,documentId,documentTitle,categoryTitle,startPage,endPage,startPosition,endPosition"
			+ "&wt=json";
		SimpleRestClient client = new SimpleRestClient();
		String responseJsonString = client.get(searURL);
		System.out.println(responseJsonString);
		SolrSearchResponse resp = JSON.fromJson(responseJsonString, SolrSearchResponse.class);
		if(resp.getResponse() != null){
			return resp.getResponse().getDocs();
		}
		return null;
	}
}
