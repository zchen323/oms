package com.ccg.oms.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
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
import com.ccg.oms.common.data.project.TaskDoc;
import com.ccg.oms.common.data.user.UserInfo;
import com.ccg.oms.common.indexing.Doc;
import com.ccg.oms.common.indexing.IndexingHelper;
import com.ccg.oms.common.indexing.ResultDoc;
import com.ccg.oms.common.indexing.SearchResult;
import com.ccg.oms.common.pdf.util.PdfUtil;
import com.ccg.oms.service.DocumentService;
import com.ccg.oms.service.UserServices;
import com.ccg.util.JSON;

@Controller
@RequestMapping("/document")
public class DocumentController {
	
	@Autowired
	DocumentService docService;
	
	@Autowired
	UserServices userServices;
	
	@RequestMapping(value="download/{id}")
	public void downLoadDocument(@PathVariable("id") Integer documentId, HttpServletRequest request, HttpServletResponse response) throws IOException{
		Document document = docService.findDocumentById(documentId);
		if(document != null){
			String userid = request.getRemoteUser();
			if(userid != null && !userid.isEmpty()){
				if(this.isUserHasAccessToDocument(userid, document)){
					userServices.addUserDocument(userid, documentId);
				}else{
					document = this.getNoAccessDoc();
				}
			}
		}else{
			document = new Document();
			document.setContent("Document not existed".getBytes());
		}
		
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

	@RequestMapping(value="/download/{documentId}/{selectedPages}",method=RequestMethod.GET)
	public void downloadParticalArticle(
			@PathVariable("documentId") Integer documentId, 
			@PathVariable("selectedPages") String selectedPages,  
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception
	{
		Document document = docService.findDocumentById(documentId);
		if(document != null){
			String userid = request.getRemoteUser();
			if(userid != null && !userid.isEmpty()){
				if(this.isUserHasAccessToDocument(userid, document)){
					userServices.addUserDocument(userid, documentId);
				}else{
					document = this.getNoAccessDoc();
				}
			}
		}else{
			document = new Document();
			document.setContent("Document not existed".getBytes());
		}

				
		File originalFile = File.createTempFile(document.getName(), ".tmp");
		FileOutputStream fos = new FileOutputStream(originalFile);
		fos.write(document.getContent());
		fos.close();

		File resultFile = File.createTempFile(document.getName(), "pdf");
		getParticalPdf(originalFile, selectedPages, resultFile);
		
		writeFileToResponse(response, resultFile);
		
		resultFile.delete();
		originalFile.delete();
	}	

	
	private void writeFileToResponse(HttpServletResponse response, File resultFile) throws IOException{
		
		response.setHeader("content-disposition", "inline; filename=" + resultFile.getName());
		response.setContentType("application/pdf");		
		
		OutputStream out = response.getOutputStream();
		InputStream is = new FileInputStream(resultFile);
		byte[] buffer = new byte[1024];
		int length = -1;
		while((length = is.read(buffer)) != -1){
			out.write(buffer, 0, length);
		}
		out.flush();
		is.close();
	}
	
	
	private void getParticalPdf(File originalFile, String selectedPages, File resultFile) throws Exception{
		List<Integer> pages = new ArrayList<Integer>();
		
		int startPage = 0;
		int endPage = 0;
		
		// page range 1-5 (from page 1 to page 5)
		if(selectedPages.indexOf("-") != -1){
			String[] pageRanges = selectedPages.split("-");
			startPage = Integer.parseInt(pageRanges[0]);
			endPage = Integer.parseInt(pageRanges[1]);
			if(endPage < startPage){
				endPage = startPage;
			}
			for(int i = startPage; i < endPage + 1; i++ ){
				pages.add(i);
			}
		}else if(selectedPages.indexOf(",") != -1){
			// selected pages: 1,4,5,9 (page 1, page 4, page 5 and page 9)
			String[] pageRanges = selectedPages.split(",");
			for(String string : pageRanges){
				pages.add(Integer.parseInt(string));
			}	
		}else{
			// single page: 5 (page 5)
			pages.add(Integer.parseInt(selectedPages));
		}
		PdfUtil.extractSelectPageIntoNewFile(originalFile, resultFile, pages);
	}	
	
	@RequestMapping(value="/download/{documentId}/{selectedPages}/{highlightRegEx}",method=RequestMethod.GET)
	public void downloadParticalArticleAndHighlightText(
			@PathVariable("documentId") Integer documentId, 
			@PathVariable("selectedPages") String selectedPages,
			@PathVariable("highlightRegEx") String highlightRegEx,
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception	{

		if(highlightRegEx == null){
			highlightRegEx = "";
		}
		
		if(highlightRegEx.startsWith("\"") && highlightRegEx.endsWith("\"")){
			highlightRegEx = highlightRegEx.substring(1, highlightRegEx.length() - 1);
		}else{
			highlightRegEx = highlightRegEx.replaceAll("\\s+?", "|");
		}
		
		Document document = docService.findDocumentById(documentId);
		if(document != null){
			String userid = request.getRemoteUser();
			if(userid != null && !userid.isEmpty()){
				if(this.isUserHasAccessToDocument(userid, document)){
					userServices.addUserDocument(userid, documentId);
				}else{
					document = this.getNoAccessDoc();
				}
			}
		}else{
			document = new Document();
			document.setContent("Document not existed".getBytes());
		}

		
		File originalFile = File.createTempFile(document.getName(), ".tmp");
		FileOutputStream fos = new FileOutputStream(originalFile);
		fos.write(document.getContent());
		fos.close();
		
		if(selectedPages.equals("-1")){
			
			//ArticleMetaData metadata = dataservice.getArticleMetaDataByArticleId(articleId);
			String html = "Error!!!";//metadata.toHTML();
			response.setContentType("text/html");
			// (?i) means CASE_INSENSITIVE.
			html = html.replaceAll("(?i)" + highlightRegEx, "<span style='background:yellow;'>$0</span>");						
			response.getWriter().println(html);			
		} else {

			//File selectedPageFile = new File(System.currentTimeMillis() + "_" + originalFile.getName());//File.createTempFile(originalFile.getName(), ".tmp");
			File selectedPageFile = File.createTempFile(originalFile.getName(), ".tmp");
			
			getParticalPdf(originalFile, selectedPages, selectedPageFile);

			File highlightedFile = File.createTempFile(originalFile.getName(), "pdf");
			
			//String originalFilePath = "/Users/zchen323/codebase/test_doc/Volume II Technical BAMC IT IM-053.pdf";
			//selectedPageFile = new File(originalFilePath);
			
			try {
				PdfUtil.textHighlight(selectedPageFile, highlightedFile, highlightRegEx);
			} catch (Exception e) {
				e.printStackTrace();
				// failed to highlight, just show the file without highlight
				highlightedFile = selectedPageFile;
			}
			
			writeFileToResponse(response, highlightedFile);
			
			selectedPageFile.delete();
			highlightedFile.delete();
		}
		originalFile.delete();
	}			
	
	
	@RequestMapping(value="{documentId}", method=RequestMethod.GET)
	public @ResponseBody RestResponse getDocumentInfo(@PathVariable(name="documentId") Integer documentId){
		RestResponse resp = RestResponse.getSuccessResponse();
		try{
			resp.setResult(docService.findDocumentInfoById(documentId));
		}catch(Exception e){
			resp.setMessage(e.getMessage());
			e.printStackTrace();
			resp.setStatus(RestResponseConstants.FAIL);
		}
	
		return resp;
	}
	
	@RequestMapping(value="delete/{documentId}", method=RequestMethod.GET)
	public @ResponseBody RestResponse deleteDocument(@PathVariable(name="documentId") Integer documentId){
		RestResponse resp = RestResponse.getSuccessResponse();
		try{
			docService.deleteDocumentById(documentId);
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
	public @ResponseBody RestResponse search(
			@RequestParam(value="query", required=false) String query,
			HttpServletRequest request) {
		RestResponse resp = RestResponse.getSuccessResponse();
		try{
			List<Doc> docs = IndexingHelper.search(query);
			
			System.out.println("======" + JSON.toJson(docs));
			
			
			//SearchResults sr = new SearchResults();
			List<Integer> order = new ArrayList<Integer>();
			Map<Integer, SearchResult> map = new HashMap<Integer, SearchResult>();
			for(Doc doc : docs){
				Integer documentId = doc.getDocumentId();
				
				//if(null == docService.findDocumentInfoById(documentId)){
				//	continue;
				//}
				if(map.containsKey(documentId)){
					SearchResult sr = map.get(documentId);
					sr.getDocuement().getCategories().add(doc);
				}else{
					SearchResult sr = new SearchResult();
					sr.setQ(query);
					if(documentId == null){
						Document document = docService.findDocumentByCategoryId(Integer.parseInt(doc.getId()));
						ResultDoc resultDoc = new ResultDoc();
						resultDoc.setId(document.getId());
						resultDoc.setDocumentId(document.getId());
						resultDoc.setTitle(document.getName());
						order.add(document.getId());
						sr.setDocuement(resultDoc);
						map.put(document.getId(), sr);
					}else{					

						ResultDoc resultDoc = new ResultDoc();
						resultDoc.setId(doc.getDocumentId());
						resultDoc.setDocumentId(doc.getDocumentId());
						resultDoc.setTitle(doc.getDocumentTitle());
						resultDoc.getCategories().add(doc);
						sr.setDocuement(resultDoc);
						order.add(doc.getDocumentId());
						map.put(doc.getDocumentId(), sr);
					}
				}
				doc.setDocumentTitle(null);
			}
			
			// final searh result
			List<ResultDoc> srs = new ArrayList<ResultDoc>();
			for(Integer docId : order){
				srs.add(map.get(docId).getDocuement());
			}
			resp.setResult(srs);
		}catch(Exception e){
			e.printStackTrace();
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
					doc.setType(type);
					
				}
			}
			// save document to db
			String restricted = params.get("restricted");
			if("on".equals(restricted)){
				doc.setRestricted(true);
			}
			Integer documentId =  docService.saveDocument(doc);
			doc.setId(documentId);
			
			// update taskdoc table;
			TaskDoc taskDoc = new TaskDoc();
			taskDoc.setDocumentId(doc.getId());
			taskDoc.setName(doc.getName());
			taskDoc.setTaskId(Integer.parseInt(params.get("id")));
			taskDoc.setUser(request.getRemoteUser());
			taskDoc.setUploadTimestamp(new Timestamp(System.currentTimeMillis()));
			taskDoc.setDoctype(params.get("doctype"));
			if("".equals(params.get("taskdocid"))){
				;
			}else{
				taskDoc.setId(Integer.parseInt(params.get("taskdocid")));
			}
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
		return responseMessage;
	}
	
	private boolean isUserHasAccessToDocument(String userId, Document doc){
		UserInfo userInfo = userServices.getUserInfoById(userId);
		if(userInfo.isFullaccess()){
			return true;
		}else if(doc.isRestricted()){
			return false;
		}
		return true;
	}
	
	private Document getNoAccessDoc(){
		Document doc = new Document();
		String content = "Access denied";
		doc.setContent(content.getBytes());
		return doc;
	}
	
}
