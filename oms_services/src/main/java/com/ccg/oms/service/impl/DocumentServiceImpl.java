package com.ccg.oms.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccg.oms.common.data.document.Document;
import com.ccg.oms.common.data.project.TaskDoc;
import com.ccg.oms.dao.entiry.document.DocumentAdditionalEntity;
import com.ccg.oms.dao.entiry.document.DocumentEntity;
import com.ccg.oms.dao.entiry.document.ProjectTaskDocumentEntity;
import com.ccg.oms.dao.entiry.project.TaskDocEntity;
import com.ccg.oms.dao.repository.document.DocumentAdditionalRepository;
import com.ccg.oms.dao.repository.document.DocumentRepository;
import com.ccg.oms.dao.repository.document.ProjectTaskDocumentRepository;
import com.ccg.oms.dao.repository.project.TaskDocRepository;
import com.ccg.oms.service.DocumentService;
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
		
		List<ProjectTaskDocumentEntity> entities = ptdRepository.findByProjectId(taskId);
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
		TaskDocEntity entity = ProjectMapper.toEntity(taskDoc);
		taskDocRepository.save(entity);
		
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
}
