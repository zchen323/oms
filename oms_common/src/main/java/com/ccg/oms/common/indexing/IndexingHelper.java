package com.ccg.oms.common.indexing;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

import com.ccg.oms.common.data.document.Document;
import com.ccg.oms.common.restclinet.SimpleRestClient;
import com.ccg.util.JSON;
import com.ccg.util.MultipartUtility;

public class IndexingHelper {
	
	public static void updateDocument(List<Doc> documents) throws IndexingException{
		SimpleRestClient client = new SimpleRestClient();
		// TODO put url in configuration file
		String urlString = "http://localhost:8083/solr/update/json?commit=true";
		String jsonPayload = JSON.toJson(documents);
		try{
			client.post(urlString, jsonPayload);	
		}catch(Exception e){
			throw new IndexingException(e.getMessage(), e);
		}
	}
	
	
	public static void updateDocument(Document doc, String indexingId) throws IndexingException{
		try{
			File tempFile = File.createTempFile("tempfile", doc.getType());
			FileOutputStream fos = new FileOutputStream(tempFile);
			fos.write(doc.getContent());
			fos.close();
			
			// cal solr rest servicee for indexing
			// TODO put rest URL in config file
			String URL = "http://72.177.234.240:8983/solr/update/extract?" 
					+ "literal.id=" + indexingId 
					+ "&literal.documentId=" + doc.getId()
					+ "&literal.documentTitless=title"
					+ "&commit=true";
			System.out.println(URL);
			
			String charSet = "UTF-8";
			MultipartUtility utility = new MultipartUtility(URL, charSet);
						
			utility.addFilePart(doc.getName(), tempFile);						
			List<String> resp = utility.finish();
			for(String string : resp){
				System.out.println("====>>>" + string);
			}
			
			// delete temp file
			tempFile.deleteOnExit();
		}catch(Exception e){
			throw new IndexingException(e.getMessage(), e);
		}
	}
	
	public static void deleteDocument(List<String> indexingId) throws IndexingException{
		
		DeleteRequest deleteRequest = new DeleteRequest();
		deleteRequest.setDelete(indexingId);
		
		SimpleRestClient client = new SimpleRestClient();
		// TODO put url in configuration file
		String urlString = "http://localhost:8083/solr/update/json?commit=true";
		String jsonPayload = JSON.toJson(deleteRequest);
		try{
			client.post(urlString, jsonPayload);
		}catch(Exception e){
			throw new IndexingException(e.getMessage(), e);
		}
		
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
