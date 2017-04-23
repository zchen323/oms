package com.ccg.oms.common.restclinet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class SimpleRestClient {
	
	
	
	public String post(String urlString, String payLoad) throws Exception{
		
		HttpURLConnection conn = this.getConnection(urlString);
		conn.setRequestMethod("POST");
		conn.setDoOutput(true);
		conn.setDoInput(true);
		conn.connect();
		
		OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
		writer.write(payLoad);
		writer.flush();
		int responseCode = conn.getResponseCode();
		if(responseCode/100 == 2){	
			String line;
			StringBuffer sb = new StringBuffer();
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			while((line = reader.readLine()) != null){
				sb.append(line).append("\n");
			}
			writer.close();
			reader.close();
			return sb.toString();
		}else{
			InputStream errStream = conn.getErrorStream();
			String errString = this.getStringFromInputStream(errStream);
			System.out.println(errString);
			throw new Exception(errString);
		}
		
		
	}
	
	public String get(String urlString) throws Exception{
		HttpURLConnection conn = this.getConnection(urlString);
		conn.setRequestMethod("GET");
		conn.setDoInput(true);
		conn.connect();
		int responseCode = conn.getResponseCode();
		if(responseCode/100 == 2){
			String line;
			StringBuffer sb = new StringBuffer();
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			while((line = reader.readLine()) != null){
				sb.append(line).append("\n");
			}
			reader.close();
			return sb.toString();	
		}else{
			InputStream errStream = conn.getErrorStream();
			String errString = this.getStringFromInputStream(errStream);
			System.out.println(errString);
			throw new Exception(errString);
		}
	}
	
	public String put(String url, String payLoad){
		
		return null;
	}
	
	public String delete(String url){
		
		return null;
	}
	
	private HttpURLConnection getConnection(String urlString) throws Exception{
		URL url = new URL(urlString);
		URLConnection conn = url.openConnection();
		conn.setRequestProperty("Content-type", "application/json");
		conn.setReadTimeout(30*1000);
		return (HttpURLConnection)conn;
	}
	
	private String getStringFromInputStream(InputStream is) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String line = null;
		StringBuffer sb = new StringBuffer();
		while((line = br.readLine()) != null){
			sb.append(line).append("\n");
		}
		return sb.toString();
	}
	
	
}
