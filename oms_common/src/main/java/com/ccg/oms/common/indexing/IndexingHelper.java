package com.ccg.oms.common.indexing;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import com.ccg.oms.common.data.document.Document;
import com.ccg.oms.common.restclinet.SimpleRestClient;
import com.ccg.util.JSON;
import com.ccg.util.MultipartUtility;

public class IndexingHelper {
	
	private static String indexServerURL = "http://72.177.234.240:8983";
	
	public static void updateDocument(List<Doc> documents) throws IndexingException{
		SimpleRestClient client = new SimpleRestClient();
		// TODO put url in configuration file
		String urlString = indexServerURL + "/solr/update/json?commit=true";
		String jsonPayload = JSON.toJson(mapping(documents));
		System.out.println("+++++" + jsonPayload);
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
			String URL = indexServerURL + "/solr/update/extract?" 
					+ "literal.id=" + indexingId //+ "&captureAttr=true&defaultField=text&fmap.documentTitle=foo_txt&capture=documentTitle&commit=true";
				   //+ "&documentId=" + doc.getId()
				   //+ "&documentTitle=title"
					+ "&commit=true";
			System.out.println("==== index whole file: ===" + URL);
			
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
		String urlString = indexServerURL + "/solr/update/json?commit=true";
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
		String searURL = indexServerURL + "/solr/select?q=" + URLEncoder.encode(query, "UTF-8") 
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
	
	private static List<DocForIndexing> mapping(List<Doc> docs){
		
		List<DocForIndexing> dfis = new ArrayList<DocForIndexing>();
		for(Doc doc : docs){
			DocForIndexing dfi = new DocForIndexing();
			dfi.setCategoryTitle(doc.getCategoryTitle());
			dfi.setDocumentId(doc.getDocumentId());
			dfi.setDocumentTitle(doc.getDocumentTitle());
			dfi.setEndPage(doc.getEndPage());
			dfi.setEndPosition(doc.getEndPosition());
			dfi.setId(doc.getId());
			dfi.setStartPage(doc.getStartPage());
			dfi.setStartPosition(doc.getStartPosition());
			dfi.setText(doc.getText());
			dfis.add(dfi);
		}
		return dfis;		
	}
}
