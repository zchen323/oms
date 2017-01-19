package com.ccg.oms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccg.oms.common.data.admin.Admin;
import com.ccg.oms.service.AdminServices;
import com.ccg.oms.service.ProjectServices;
import com.ccg.oms.service.UserServices;

@Service
public class AdminServiceImpl implements AdminServices {

	@Autowired
	ProjectServices projectService;
	
	@Autowired
	UserServices userServices;

	@Override
	public Admin getAdiminData() {
		Admin admin = new Admin();
		admin.setDocTypes(projectService.getDocTypes());
		admin.setPrjectTemplates(projectService.getProjectTemplate());
		admin.setProjectUserRoleTypes(projectService.getProjectUserRoleTypes());
		admin.setTaskTemplates(projectService.getTaskTemplate());
		admin.setUsers(userServices.getUsers());		
		return admin;
	}
}
