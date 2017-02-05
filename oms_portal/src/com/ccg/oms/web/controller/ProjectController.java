package com.ccg.oms.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ccg.oms.common.data.RestResponse;
import com.ccg.oms.common.data.RestResponseConstants;
import com.ccg.oms.common.data.project.Project;
import com.ccg.oms.service.ProjectServices;
import com.ccg.util.JSON;

@Controller
@RequestMapping("/project")
public class ProjectController {
	
	@Autowired
	ProjectServices service;
	
	// project user role
	@RequestMapping(value="projectUserRoleType/all", method=RequestMethod.GET)
	public @ResponseBody RestResponse getProjectUserRoleTypes(){
		//List<ProjectUserRoleType> list = service.getProjectUserRoleTypes();
		RestResponse resp = RestResponse.getSuccessResponse();
		//resp.setResult(list);
		return resp;
	}
	
	@RequestMapping(value="newProject", method=RequestMethod.POST)
	public @ResponseBody RestResponse createNewProject(@RequestBody Project project){
		RestResponse resp = RestResponse.getSuccessResponse();
		//System.out.println(JSON.toJson(project));
		try{
			service.createNewProject(project);
			resp.setMessage("Done");
		}catch(Exception e){
			e.printStackTrace();
			resp.setStatus(RestResponseConstants.FAIL);
			resp.setMessage(e.getMessage());
		}
		return resp;
	}

}
