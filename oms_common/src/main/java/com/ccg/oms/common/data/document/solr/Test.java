package com.ccg.oms.common.data.document.solr;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.ccg.util.JSON;

public class Test {
	
	public static void main(String[] args) throws Exception{
		
		String urlString = "http://72.177.234.240:8983/solr/select?q=feditc&fl=id,last_modified,content_type,author&wt=json";
		
        URL url = new URL(urlString);
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setRequestMethod("GET");
        httpConn.setReadTimeout(15*1000);
        httpConn.connect();
        
       // BufferedReader br = new BufferedReader(new InputStreamReader())
        BufferedReader reader = new BufferedReader(new InputStreamReader(httpConn.getInputStream()));
        StringBuffer sb = new StringBuffer();
        String line = null;
        while((line = reader.readLine())!= null){
        	sb.append(line).append("\n");
        }
        
        System.out.println(sb.toString());
        
        SolrSearchResponse resp = JSON.fromJson(sb.toString(), SolrSearchResponse.class);
        
        System.out.println(JSON.toJson(resp));
        
        
	}

}
