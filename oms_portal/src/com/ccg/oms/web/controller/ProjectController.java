package com.ccg.oms.web.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ccg.oms.common.data.RestResponse;
import com.ccg.oms.common.data.project.ProjectUserRoleType;

@Controller
@RequestMapping("/project")
public class ProjectController {
	
	// project user role
	@RequestMapping(value="projectUserRoleType/all", method=RequestMethod.GET)
	public @ResponseBody RestResponse getProjectUserRoleTypes(){
		//List<ProjectUserRoleType> list = service.getProjectUserRoleTypes();
		RestResponse resp = RestResponse.getSuccessResponse();
		//resp.setResult(list);
		return resp;
	}

}
