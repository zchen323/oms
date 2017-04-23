package com.ccg.oms.common.indexing;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import com.ccg.oms.common.restclinet.SimpleRestClient;
import com.ccg.util.JSON;

public class TestMain {
	public static void main(String[] args) throws Exception{
		AddDocumentRequest req = new AddDocumentRequest();
		AddDocument add = new AddDocument();
		Doc doc = new Doc();
		doc.setId("123");
		doc.setDocumentId(12345);
		doc.setEndPage(12);
		doc.setStartPage(123);;
		doc.setStartPosition(112);
		doc.setEndPosition(1233);
		doc.setCategoryTitle("title");
		doc.setText("this is test security content");

		Doc doc2 = new Doc();
		doc2.setId("1234");
		doc2.setDocumentId(12345);
		doc2.setEndPage(12);
		doc2.setStartPage(123);;
		doc2.setStartPosition(112);
		doc2.setEndPosition(1233);
		doc2.setCategoryTitle("title");
		doc2.setText("this is test security content");		
		
		
		add.getDoc().add(doc);
		
		req.setAdd(add);
		
		List<Doc> tobeIndexed = new ArrayList<Doc>();
		tobeIndexed.add(doc);
		tobeIndexed.add(doc2);
		
		List<String> ids = new ArrayList<String>();
		ids.add("123");
		ids.add("1234");
		//delete(ids);
		
		IndexingHelper.updateDocument(tobeIndexed);
		
		List<Doc> docs = IndexingHelper.search("security content");
		
		System.out.println(JSON.toJson(docs));
	}
	

	
	
}
