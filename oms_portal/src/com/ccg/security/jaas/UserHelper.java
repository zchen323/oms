package com.ccg.security.jaas;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.ccg.oms.common.data.RestResponseConstants;
import com.ccg.util.JSON;

public class UserHelper {

	public static boolean validateUser(String user, String pass) {
		String urlString = "http://localhost:8080/oms_portal/api/user/validateUser";
		String params = "user=" + user + "&" + "pass=" + pass;
		boolean isValidUser = false;
		try {
			URL url = new URL(urlString);
			HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
			httpConn.setRequestMethod("POST");
			httpConn.setReadTimeout(3 * 1000);
			httpConn.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded"); 
			httpConn.setRequestProperty( "charset", "utf-8");
			httpConn.setRequestProperty( "Content-Length", Integer.toString(params.length()));
			httpConn.setDoOutput(true);
			httpConn.setDoInput(true);
			httpConn.setUseCaches( false );
			DataOutputStream wr = new DataOutputStream( httpConn.getOutputStream());
			wr.write(params.getBytes("UTF-8"));
			InputStream is = httpConn.getInputStream();
			String response = inputStreamToString(is);
			if(response.contains(RestResponseConstants.SUCCESS)){
				isValidUser = true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isValidUser;
	}

	public static List<String> getUserRoles(String user) {
		String urlString = "http://localhost:8080/oms_portal/api/user/userRoles/" + user;
		
		System.out.println(urlString);
		
		List<String> roles = new ArrayList<String>();

		//String urlString = "http://72.177.234.240:8983/solr/select?q=" + query + "&fl=id,last_modified,content_type,author&wt=json";
		try{
	        URL url = new URL(urlString);
	        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
	        httpConn.setRequestMethod("GET");
	        httpConn.setReadTimeout(15*1000);
	        httpConn.connect();
	        InputStream is = httpConn.getInputStream();
	        String response = inputStreamToString(is);
	        
	        System.out.println("======" + response);
	        
	       UserRolesResponse userRolesResponse =  JSON.fromJson(response, UserRolesResponse.class);
	       if("success".equals(userRolesResponse.getStatus())){
	    	   roles = userRolesResponse.getResult();
	       }	        
		}catch(Exception e){
			e.printStackTrace();
		}
		return roles;
	}
	
	
	private static String inputStreamToString(InputStream is) throws IOException {
		String line = null;
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		StringBuffer sb = new StringBuffer();
		while((line = br.readLine()) != null){
			sb.append(line).append("\n");
		}
		System.out.println(sb.toString());
		
		return sb.toString();
	}
	
}
