package com.ccg.oms.service.mapper;

import com.ccg.oms.common.data.user.UserInfo;
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
	
}
