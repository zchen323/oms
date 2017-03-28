package com.ccg.oms.web.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ccg.oms.common.data.RestResponse;
import com.ccg.oms.common.data.RestResponseConstants;
import com.ccg.oms.common.data.document.Document;
import com.ccg.oms.common.data.document.solr.SolrSearchResponse;
import com.ccg.oms.common.data.project.TaskDoc;
import com.ccg.oms.service.DocumentService;
import com.ccg.util.JSON;
import com.ccg.util.MultipartUtility;

@Controller
@RequestMapping("/document")
public class DocumentController {
	
	@Autowired
	DocumentService docService;
	
	@RequestMapping(value="download/{id}")
	public void downLoadDocument(@PathVariable("id") Integer documentId, HttpServletResponse response) throws IOException{
		Document document = docService.findDocumentById(documentId);
		response.setHeader("content-disposition", "inline; filename=" + document.getName());
		if("pdf".equalsIgnoreCase(document.getType())){
			response.setContentType("application/pdf");
		}else if("doc".equalsIgnoreCase(document.getType())){
			response.setContentType("application/msword");
		}else if("docx".equalsIgnoreCase(document.getType())){
			response.setContentType("application/msword");
		}else if("xml".equalsIgnoreCase(document.getType())){
			response.setContentType("application/xml");
		}else{
			response.setContentType("text/plain");
		}
		OutputStream os = response.getOutputStream();
		os.write(document.getContent());
		os.flush();
	}
	
	@RequestMapping(value="{documentId}", method=RequestMethod.GET)
	public @ResponseBody RestResponse getDocumentInfo(@PathVariable(name="documentId") Integer documentId){
		RestResponse resp = RestResponse.getSuccessResponse();
		try{
			resp.setResult(docService.findDocumentInfoById(documentId));
		}catch(Exception e){
			resp.setMessage(e.getMessage());
			resp.setStatus(RestResponseConstants.FAIL);
		}
	
		return resp;
	}
	
	@RequestMapping(value="project/{projectId}", method=RequestMethod.GET)
	public @ResponseBody RestResponse getDocumentInfoByProjectId(@PathVariable(name="projectId") Integer projectId){
		RestResponse resp = RestResponse.getSuccessResponse();
		try{
			//docService.findDocumentByProjectId(projectId)
			resp.setResult(docService.findDocumentByProjectId(projectId));
		}catch(Exception e){
			resp.setMessage(e.getMessage());
			resp.setStatus(RestResponseConstants.FAIL);
		}
	
		return resp;
	}
	
	@RequestMapping(value="task/{taskId}", method=RequestMethod.GET)
	public @ResponseBody RestResponse getDocumentInfoByTaskId(@PathVariable(name="taskId") Integer taskId){
		RestResponse resp = RestResponse.getSuccessResponse();
		try{
			resp.setResult(docService.findDocumentByTaskId(taskId));
		}catch(Exception e){
			resp.setMessage(e.getMessage());
			resp.setStatus(RestResponseConstants.FAIL);
		}
	
		return resp;
	}

	@RequestMapping(value="search", method=RequestMethod.GET)
	public @ResponseBody RestResponse search(@RequestParam(value="query", required=false) String query) {
		RestResponse resp = RestResponse.getSuccessResponse();
		try{
			
			String urlString = "http://72.177.234.240:8983/solr/select?q=" + query + "&fl=id,last_modified,content_type,author&wt=json";
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
	        
	        SolrSearchResponse searchResponse = JSON.fromJson(sb.toString(), SolrSearchResponse.class);
	        
	        System.out.println(JSON.toJson(searchResponse));
			resp.setResult(searchResponse);
		}catch(Exception e){
			resp.setMessage(e.getMessage());
			resp.setStatus(RestResponseConstants.FAIL);
		}
	
		return resp;
	}
	
	
	

	@RequestMapping(value="upload", method=RequestMethod.POST)
	public @ResponseBody String upload(HttpServletRequest request){
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		//RestResponse resp = RestResponse.getSuccessResponse();
		String responseMessage = "{}";//"{ 'success': false, 'file': 'filename' }";

		if(!isMultipart){
			//resp.setStatus(RestResponseConstants.FAIL);
			//resp.setMessage("Not a multipart");
			responseMessage = "{ 'success': false, 'file': 'Not a mutipart' }";
		}
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		Map<String, String> params = new HashMap<String, String>();
		try {
			List<FileItem> fields = upload.parseRequest(request);
			System.out.println(fields.size());
			Iterator<FileItem> it = fields.iterator();
		
			
			Document doc = null;
			while(it.hasNext()){
				FileItem ft = it.next();
				if(ft.isFormField()){
					params.put(ft.getFieldName(), ft.getString());
				}else{
					String filename = ft.getName();
					int position = filename.lastIndexOf(".");
					String type = filename.substring(position + 1);
					doc = new Document();
					doc.setContent(ft.get());
					doc.setName(ft.getName());
					doc.setSize((int)ft.getSize());
					System.out.println("======>>>" + ft.getSize());
					doc.setType(type);
					// save document to db
					Integer documentId =  docService.saveDocument(doc);
					doc.setId(documentId);
					
					/////////////////////////////
					// indexing uploaded file					
					/////////////////////////////
					// create temp file
					File tempFile = File.createTempFile("tempfile", doc.getType());
					FileOutputStream fos = new FileOutputStream(tempFile);
					fos.write(doc.getContent());
					fos.close();
					
					// cal solr rest servicee for indexing
					// TODO put rest URL in config file
					String URL = "http://72.177.234.240:8983/solr/update/extract?literal.id=document_" + doc.getId() + "&commit=true";
					String charSet = "UTF-8";
					MultipartUtility utility = new MultipartUtility(URL, charSet);
					
					File file = new File("/Users/zchen323/Downloads/test.pdf");						
					utility.addFilePart(doc.getName(), tempFile);						
					List<String> resp = utility.finish();
					for(String string : resp){
						System.out.println("====>>>" + string);
					}
					// delete temp file
					tempFile.deleteOnExit();
				}
			}
			System.out.println("======params======");
			System.out.println(params);
			
			// update taskdoc table;
			TaskDoc taskDoc = new TaskDoc();
			taskDoc.setDocumentId(doc.getId());
			taskDoc.setName(doc.getName());
			taskDoc.setTaskId(Integer.parseInt(params.get("id")));
			taskDoc.setUser(request.getRemoteUser());
			taskDoc.setUploadTimestamp(new Timestamp(System.currentTimeMillis()));
			if("".equals(params.get("taskdocid"))){
				;
			}else{
				taskDoc.setId(Integer.parseInt(params.get("taskdocid")));
			}
			System.out.println(JSON.toJson(taskDoc));
			docService.saveTaskDoc(taskDoc);
			
			Map<String, Object> respMap = new HashMap<String, Object>();
			respMap.put("success", true);
			respMap.put("file", doc.getName());
			respMap.put("docId", doc.getId() );
			
			responseMessage = JSON.toJson(respMap);

			
		} catch (Exception e) {
			Map<String, Object> respMap = new HashMap<String, Object>();
			respMap.put("success", false);
			respMap.put("message", e.getMessage());
			
			responseMessage = JSON.toJson(respMap);
			e.printStackTrace();
		}
		//{ "success": true, "file": "filename" }

		return responseMessage;
	}
	
}
