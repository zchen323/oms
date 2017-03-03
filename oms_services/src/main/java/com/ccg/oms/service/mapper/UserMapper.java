package com.ccg.oms.service.mapper;

import com.ccg.oms.common.data.project.ProjectUser;
import com.ccg.oms.common.data.user.UserInfo;
import com.ccg.oms.dao.entiry.project.ProjectUserEntity;
import com.ccg.oms.dao.entiry.user.UserDetailEntity;

public class UserMapper {
	public static UserInfo fromEntity(UserDetailEntity entity){
		UserInfo info = new UserInfo();
		info.setCreatedDate(entity.getCreatedTS());
		info.setEmail(entity.getEmail());
		info.setFullaccess(entity.getFullAccess());
		info.setName(entity.getName());
		info.setUsername(entity.getUsername());
		
		return info;
	}
	
	public static ProjectUser fromEntity(ProjectUserEntity entity){
		ProjectUser user = new ProjectUser();
		user.setProjectId(entity.getProjectId());
		user.setProjectUserRole(entity.getRole());
		user.setUserId(entity.getUserId());
		user.setUsername(entity.getUsername());		
		return user;
	}
	
	public static ProjectUserEntity toEntity(ProjectUser user){
		ProjectUserEntity entity = new ProjectUserEntity();
		entity.setProjectId(user.getProjectId());
		entity.setRole(user.getProjectUserRole());
		entity.setUserId(user.getUserId());
		entity.setUsername(user.getUsername());		
		return entity;
	}
	
}
