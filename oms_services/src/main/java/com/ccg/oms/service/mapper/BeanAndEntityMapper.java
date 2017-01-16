package com.ccg.oms.service.mapper;
/**
 * http://mapstruct.org/
 */
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.ccg.oms.common.data.document.DocType;
import com.ccg.oms.common.data.project.Project;
import com.ccg.oms.common.data.project.ProjectTemplate;
import com.ccg.oms.common.data.project.ProjectUserRoleType;
import com.ccg.oms.common.data.project.TaskTemplate;
import com.ccg.oms.common.data.user.User;
import com.ccg.oms.dao.entiry.document.DocTypeEntity;
import com.ccg.oms.dao.entiry.project.ProjectEntity;
import com.ccg.oms.dao.entiry.project.ProjectTemplateEntity;
import com.ccg.oms.dao.entiry.project.ProjectUserRoleTypeEntity;
import com.ccg.oms.dao.entiry.project.TaskTemplateEntity;
import com.ccg.oms.dao.entiry.user.UserEntity;

@Mapper
public interface BeanAndEntityMapper {
	
	BeanAndEntityMapper INSTANCE = Mappers.getMapper(BeanAndEntityMapper.class);	
	
	// project
	ProjectUserRoleType entityToBean(ProjectUserRoleTypeEntity entity );	
	ProjectUserRoleTypeEntity beanToEntity(ProjectUserRoleType bean);
	
	ProjectTemplate entityToBean(ProjectTemplateEntity entity);
	ProjectTemplateEntity beanToEntity(ProjectTemplate bean);
	
	TaskTemplate entityToBean(TaskTemplateEntity entity);
	TaskTemplateEntity beanToEntity(TaskTemplate bean);
	
	Project entityToBean(ProjectEntity entity);
	ProjectEntity beanToEntity(Project bean);
	
	// user
	User entityToBean(UserEntity entity);
	UserEntity beanToEntity(User bean);
	
	// document
	DocType entityToBean(DocTypeEntity entity);
	DocTypeEntity beanToEntity(DocType bean);
	
	
}
