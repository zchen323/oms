package com.ccg.oms.service.mapper;

import com.ccg.oms.common.data.document.Document;
import com.ccg.oms.dao.entiry.document.DocumentEntity;

public class DocumentMapper {
	public static Document fromEntity(DocumentEntity entity){
		Document doc = new Document();
		doc.setContent(entity.getContent());
		doc.setId(entity.getId());
		doc.setName(entity.getName());
		doc.setSize(entity.getSize());
		doc.setType(entity.getType());
		doc.setRestricted(entity.isRestricted());
		return doc;
	}
	
	public static DocumentEntity toEntity(Document doc){
		DocumentEntity entity = new DocumentEntity();
		entity.setContent(doc.getContent());
		entity.setId(doc.getId());
		entity.setName(doc.getName());
		entity.setSize(doc.getSize());
		entity.setType(doc.getType());
		entity.setRestricted(doc.isRestricted());
		return entity;
	}
}
