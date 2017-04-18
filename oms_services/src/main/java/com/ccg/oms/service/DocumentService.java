package com.ccg.oms.service;

import java.util.List;

import com.ccg.oms.common.data.document.Document;
import com.ccg.oms.common.data.document.DocumentInfo;
import com.ccg.oms.common.data.project.TaskDoc;

public interface DocumentService {
	Integer saveDocument(Document doc);
	void saveProjectTaskDocument(Integer documentId, Integer projectId, Integer taskId);
	List<Document> findDocumentByProjectId(Integer projectId);
	List<Document> findDocumentByTaskId(Integer taskId);
	Document findDocumentById(Integer documentId);
	DocumentInfo findDocumentInfoById(Integer documentId);
	void deleteDocumentById(Integer documentId);
	
	void saveTaskDoc(TaskDoc taskDoc);
}
