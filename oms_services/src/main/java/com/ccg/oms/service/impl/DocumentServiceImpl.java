package com.ccg.oms.service.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ccg.ingestion.extract.ArticleInfo;
import com.ccg.ingestion.extract.ArticleTypePattern;
import com.ccg.ingestion.extract.Category;
import com.ccg.ingestion.extract.ExtractArticleInfo;
import com.ccg.oms.common.data.document.Document;
import com.ccg.oms.common.data.document.DocumentInfo;
import com.ccg.oms.common.data.project.TaskDoc;
import com.ccg.oms.common.indexing.Doc;
import com.ccg.oms.common.indexing.IndexingException;
import com.ccg.oms.common.indexing.IndexingHelper;
import com.ccg.oms.dao.entiry.document.DocumentAdditionalEntity;
import com.ccg.oms.dao.entiry.document.DocumentCategoryEntity;
import com.ccg.oms.dao.entiry.document.DocumentEntity;
import com.ccg.oms.dao.entiry.document.DocumentTextEntity;
import com.ccg.oms.dao.entiry.document.ProjectTaskDocumentEntity;
import com.ccg.oms.dao.entiry.project.TaskDocEntity;
import com.ccg.oms.dao.entiry.project.TaskEntity;
import com.ccg.oms.dao.repository.document.DocumentAdditionalRepository;
import com.ccg.oms.dao.repository.document.DocumentCategoryRepository;
import com.ccg.oms.dao.repository.document.DocumentRepository;
import com.ccg.oms.dao.repository.document.DocumentTextRepository;
import com.ccg.oms.dao.repository.document.ProjectTaskDocumentRepository;
import com.ccg.oms.dao.repository.project.TaskDocRepository;
import com.ccg.oms.dao.repository.project.TaskRepository;
import com.ccg.oms.service.DocumentService;
import com.ccg.oms.service.ProjectServices;
import com.ccg.oms.service.mapper.DocumentMapper;
import com.ccg.oms.service.mapper.ProjectMapper;

@Service
public class DocumentServiceImpl implements DocumentService{

	@Autowired
	DocumentRepository docRepository;
	
	@Autowired
	DocumentAdditionalRepository  docAddRepository;
	
	@Autowired
	ProjectTaskDocumentRepository ptdRepository;
	
	@Autowired
	TaskDocRepository taskDocRepository;
	
	@Autowired
	TaskRepository taskRepository;
	
	@Autowired
	ProjectServices projectService;
	
	@Autowired
	DocumentTextRepository dtRepository;
	
	@Autowired
	DocumentCategoryRepository dcRepository;
	
	static final int max = 1000000;
	
	@Override
	public Integer saveDocument(Document doc) {
		DocumentEntity entity = DocumentMapper.toEntity(doc);
		// com.mysql.jdbc.PacketTooBigException: Packet for query is too large (1168267 > 1048576).
		// 2^20 = 1048576
		// set max content size 1048576 - 1024 = 1047552
		int max = 1000000;
		if(doc.getContent().length < max){
			docRepository.save(entity);
		}else{
			int length = doc.getContent().length;
			int howManyAdditional = length/max;
			int rest = length%max;
			byte[][] contents = new byte[howManyAdditional][max];
			for(int i = 0; i < howManyAdditional; i++){
				System.arraycopy(doc.getContent(), i*max, contents[i], 0, max);
			}
			
			byte[] restContent = new byte[rest];
			System.arraycopy(doc.getContent(), howManyAdditional*max, restContent, 0, rest);
			
			entity.setContent(contents[0]);
			entity.setHasMore(true);
			
			System.out.println("====>>> length:" + entity.getContent().length);
			docRepository.save(entity);
			
			// save additional
			for(int i = 1; i < contents.length; i++){
				DocumentAdditionalEntity daEntity = new DocumentAdditionalEntity();
				daEntity.setDocumentId(entity.getId());
				daEntity.setContent(contents[i]);
				docAddRepository.save(daEntity);
			}
			
			// save the rest
			if(rest > 0){
				DocumentAdditionalEntity daEntity = new DocumentAdditionalEntity();
				daEntity.setDocumentId(entity.getId());
				daEntity.setContent(restContent);
				docAddRepository.save(daEntity);
			}
		}
		doc.setId(entity.getId());
		indexing(doc);
		
		return entity.getId();
	}

	@Override
	public void saveProjectTaskDocument(Integer documentId, Integer projectId, Integer taskId) {
		ProjectTaskDocumentEntity entity = new ProjectTaskDocumentEntity();
		entity.setDocumentId(documentId);
		entity.setProjectId(projectId);
		entity.setTaskId(taskId);
		ptdRepository.save(entity);
		
	}

	@Override
	public List<Document> findDocumentByProjectId(Integer projectId) {
		List<Document> result = new ArrayList<Document>();
		
		List<ProjectTaskDocumentEntity> entities = ptdRepository.findByProjectId(projectId);
		List<Integer> documentIdList = new ArrayList<Integer>();
		for(ProjectTaskDocumentEntity entity : entities){
			documentIdList.add(entity.getDocumentId());
		}
		Iterable<DocumentEntity> iterable = docRepository.findAll(documentIdList);
		Iterator<DocumentEntity> it = iterable.iterator();
		while(it.hasNext()){
			DocumentEntity de = it.next();
			de.setContent(null);
			result.add(DocumentMapper.fromEntity(de));
		}		
		return result;		
	}

	@Override
	public List<Document> findDocumentByTaskId(Integer taskId) {
		List<Document> result = new ArrayList<Document>();
		
		List<ProjectTaskDocumentEntity> entities = ptdRepository.findByTaskId(taskId);
		List<Integer> documentIdList = new ArrayList<Integer>();
		for(ProjectTaskDocumentEntity entity : entities){
			documentIdList.add(entity.getDocumentId());
		}
		Iterable<DocumentEntity> iterable = docRepository.findAll(documentIdList);
		Iterator<DocumentEntity> it = iterable.iterator();
		while(it.hasNext()){
			DocumentEntity de = it.next();
			de.setContent(null);
			result.add(DocumentMapper.fromEntity(de));
		}		
		return result;		
	}

	@Override
	public void saveTaskDoc(TaskDoc taskDoc) {
		
		TaskEntity taskEntity = taskRepository.findOne(taskDoc.getTaskId());
		saveProjectTaskDocument(taskDoc.getDocumentId(), taskEntity.getProjectId(), taskDoc.getTaskId());
		
		if(taskDoc.getId() == null){
			TaskDocEntity entity = ProjectMapper.toEntity(taskDoc);
			taskDocRepository.save(entity);
		}else{
			TaskDocEntity entity = taskDocRepository.findOne(taskDoc.getId());
			entity.setDocumentId(taskDoc.getDocumentId());
			entity.setTaskId(taskDoc.getTaskId());
			entity.setUploadDate(taskDoc.getUploadTimestamp());
			entity.setUser(taskDoc.getUser());
			entity.setName(taskDoc.getName());
			taskDocRepository.save(entity);	
			taskDoc.setId(entity.getId());
		}
	}

	@Override
	public Document findDocumentById(Integer documentId) {
		
		DocumentEntity entity = docRepository.findOne(documentId);

		if(entity.isHasMore()){
			
			byte[] content = new byte[entity.getSize()];
			System.arraycopy(entity.getContent(), 0, content, 0, entity.getContent().length);
			int position = entity.getContent().length;
			List<DocumentAdditionalEntity> entities = docAddRepository.findByDocumentIdOrderById(entity.getId());
			for(int i = 0; i < entities.size(); i++){
				DocumentAdditionalEntity en = entities.get(i);
				System.arraycopy(en.getContent(), 0, content, position, en.getContent().length);
				position = position + en.getContent().length;				
			}
			entity.setContent(content);
		}
		
		Document doc = DocumentMapper.fromEntity(entity);
		return doc;
	}

	@Override
	public DocumentInfo findDocumentInfoById(Integer documentId) {
		DocumentInfo docInfo = new DocumentInfo();
		
		DocumentEntity docEntity = docRepository.findOne(documentId);
		List<ProjectTaskDocumentEntity> ptdEntities = ptdRepository.findByDocumentId(documentId);
		Set<Integer> projectIds = new HashSet<Integer>();
		for(ProjectTaskDocumentEntity entity : ptdEntities){
			projectIds.add(entity.getProjectId());
		}
		for(Integer projectId : projectIds){
			docInfo.getProject().add(projectService.findProjectInfo(projectId));
		}
		
		docInfo.setName(docEntity.getName());
		docInfo.setDocumentId(documentId);
		docInfo.setUrl("api/document/download/" + documentId);
		
		return docInfo;
	}

	@Override
	public void deleteDocumentById(Integer documentId) {
		
		List<ProjectTaskDocumentEntity> projectTaskDocuments = ptdRepository.findByDocumentId(documentId);
		if(projectTaskDocuments != null && !projectTaskDocuments.isEmpty()){
			ptdRepository.delete(projectTaskDocuments);
		}	
		
		List<TaskDocEntity> taskDocEntities = taskDocRepository.findByDocumentId(documentId);
		if(taskDocEntities != null && !taskDocEntities.isEmpty()){
			taskDocRepository.delete(taskDocEntities);
		}
		
		DocumentEntity docEntity = docRepository.findOne(documentId);
		if(docEntity != null){
			docRepository.delete(docEntity);
		}
		
		List<DocumentAdditionalEntity> documentAddEntities = docAddRepository.findByDocumentIdOrderById(documentId);
		if(documentAddEntities != null && !documentAddEntities.isEmpty()){
			docAddRepository.delete(documentAddEntities);
		}
		
		// remove from indexing
		List<DocumentCategoryEntity> dcEntities = dcRepository.findByDocumentId(documentId);
		List<String> categoryIds = new ArrayList<String>();
		for(DocumentCategoryEntity dce : dcEntities){
			categoryIds.add("" + dce.getId());
		}
		try {
			IndexingHelper.deleteDocument(categoryIds);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	
	@Transactional
	private void indexing(Document document){
		InputStream is = new ByteArrayInputStream(document.getContent());
		ExtractArticleInfo extract = new ExtractArticleInfo();
		//ArticleInfo info = null;
		try {
			ArticleInfo info = extract.fromPDF(is, ArticleTypePattern.PROPOSALS_1);
			DocumentTextEntity dtEntity = new DocumentTextEntity();
			dtEntity.setDocumentId(document.getId());
			dtEntity.setText(info.getContent());
			dtRepository.save(dtEntity);
			
			List<Doc> docsToBeIndexed = new ArrayList<Doc>();
			
			for(Category c : info.getCategoryList()){
				System.out.println(c.getTitle() + ", " 
						+ c.getStartPosition() + ", " + c.getEndPosition() + ", p"
						+ c.getStartPage() + ", p" + c.getEndPage());
				DocumentCategoryEntity dcEntity = new DocumentCategoryEntity();
				dcEntity.setCategoryTitle(c.getTitle());
				dcEntity.setDocumentId(document.getId());
				dcEntity.setStartPage(c.getStartPage());
				dcEntity.setEndPage(c.getEndPage());
				dcEntity.setStartPosition(c.getStartPosition());
				dcEntity.setEndPosition(c.getEndPosition());
				dcRepository.save(dcEntity);
				int startPosition = c.getStartPosition();
				int endPosition = c.getEndPosition();
				String categoryContent = info.getContent().substring(startPosition, endPosition);
				convertToDoc(docsToBeIndexed, dcEntity, info.getTitle(), categoryContent );
				
				try{
					// update indexing
					if(docsToBeIndexed.isEmpty()){
						Doc doc = new Doc();
						doc.setDocumentId(document.getId());
						doc.setDocumentTitle(document.getName());
						doc.setText(info.getContent());
						docsToBeIndexed.add(doc);
						IndexingHelper.updateDocument(docsToBeIndexed);
						
					}else{
						IndexingHelper.updateDocument(docsToBeIndexed);	
					}
				}catch(IndexingException e){		
					e.printStackTrace();
				}
			}
		}catch(Exception e){
			// indexing document as whole file
			e.printStackTrace();
			DocumentCategoryEntity dcEntity = new DocumentCategoryEntity();
			dcEntity.setDocumentId(document.getId());
			dcRepository.save(dcEntity);
			try{
				IndexingHelper.updateDocument(document, "" + dcEntity.getId());
			}catch(IndexingException ie){
				ie.printStackTrace();
			}			
		}
	}
	
	private void convertToDoc(
			List<Doc> docsToBeIndexed, 
			DocumentCategoryEntity dcEntity, 
			String documentTitle,
			String content){
		Doc doc = new Doc();
		doc.setId("" + dcEntity.getId());
		doc.setDocumentId(dcEntity.getDocumentId());
		doc.setDocumentTitle(documentTitle);
		doc.setCategoryTitle(dcEntity.getCategoryTitle());
		doc.setStartPage(dcEntity.getStartPage());
		doc.setEndPage(dcEntity.getEndPage());
		doc.setStartPosition(dcEntity.getStartPosition());
		doc.setEndPosition(dcEntity.getEndPosition());
		doc.setText(content);
		docsToBeIndexed.add(doc);
	}

	@Override
	@Transactional
	public Document findDocumentByCategoryId(Integer categoryId) {
		DocumentCategoryEntity dcEntity = dcRepository.findOne(categoryId);
		Integer documentId = dcEntity.getDocumentId();
		DocumentEntity docEntity = docRepository.findOne(documentId);
		Document doc = DocumentMapper.fromEntity(docEntity);
		return doc;
	}
	
}



