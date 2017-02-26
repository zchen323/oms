package com.ccg.oms.web.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ccg.oms.common.data.RestResponse;
import com.ccg.oms.common.data.RestResponseConstants;
import com.ccg.oms.common.data.document.Document;
import com.ccg.oms.common.data.project.TaskDoc;
import com.ccg.oms.service.DocumentService;
import com.ccg.util.JSON;

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
	

	@RequestMapping(value="upload", method=RequestMethod.POST)
	public @ResponseBody RestResponse upload(HttpServletRequest request){
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		RestResponse resp = RestResponse.getSuccessResponse();
		if(!isMultipart){
			resp.setStatus(RestResponseConstants.FAIL);
			resp.setMessage("Not a multipart");
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
				}
			}
			
			System.out.println(params);
			
			// update taskdoc table;
			TaskDoc taskDoc = new TaskDoc();
			taskDoc.setDocumentId(doc.getId());
			taskDoc.setName(doc.getName());
			taskDoc.setTaskId(Integer.parseInt(params.get("id")));
			taskDoc.setUser(request.getRemoteUser());
			taskDoc.setUploadTimestamp(new Timestamp(System.currentTimeMillis()));
			System.out.println(JSON.toJson(taskDoc));
			docService.saveTaskDoc(taskDoc);
			
			// update project-task-document
			docService.saveProjectTaskDocument(
					doc.getId(), 
					Integer.parseInt(params.get("projectId")), 
					taskDoc.getTaskId()
				);
			
		} catch (FileUploadException e) {
			resp.setMessage(e.getMessage());
			resp.setStatus(RestResponseConstants.FAIL);
			e.printStackTrace();
		}
		return resp;
	}
	
}
