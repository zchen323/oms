package com.ccg.oms.service.mapper;
/**
 * http://mapstruct.org/
 */
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import com.ccg.oms.common.data.project.ProjectUserRoleType;
import com.ccg.oms.dao.entiry.project.ProjectUserRoleTypeEntity;

@Mapper
public interface BeanAndEntityMapper {
	
	BeanAndEntityMapper INSTANCE = Mappers.getMapper(BeanAndEntityMapper.class);	
	
	ProjectUserRoleType entityToBean(ProjectUserRoleTypeEntity entity );	
	ProjectUserRoleTypeEntity beanToEntity(ProjectUserRoleType bean);
	ProjectUserRoleTypeEntity copyEntity(ProjectUserRoleTypeEntity entity);
	
	
	
}
