package com.ccg.oms.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ccg.oms.common.data.RestResponse;
import com.ccg.oms.common.data.project.Project;
import com.ccg.oms.common.data.project.ProjectUserRoleType;
import com.ccg.oms.service.ProjectServices;

@Controller
@RequestMapping("/project")
public class ProjectController {
	
	@Autowired
	private ProjectServices service;
	

	@RequestMapping(value="name", method=RequestMethod.GET)
	public @ResponseBody Project getShop(){
		service.findProjectById(1);
		return null;
	}
	
	@RequestMapping(value="saveProjectUsreRoleType", method=RequestMethod.POST)
	public @ResponseBody RestResponse saveProjectUserRoleType(@RequestBody ProjectUserRoleType roleType){
		service.saveProjectUserRoleType(roleType);
		return RestResponse.getSuccessResponse();
	}
	
	@RequestMapping(value="getProjectUserRoleType", method=RequestMethod.GET)
	public @ResponseBody RestResponse getProjectUserRoleTypes(){
		List<ProjectUserRoleType> list = service.getProjectUserRoleTypes();
		RestResponse resp = RestResponse.getSuccessResponse();
		resp.setResult(list);
		return resp;
	}
}

