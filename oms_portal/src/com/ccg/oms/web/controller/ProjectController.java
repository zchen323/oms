package com.ccg.oms.web.controller;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ccg.oms.common.data.RestResponse;
import com.ccg.oms.common.data.RestResponseConstants;
import com.ccg.oms.common.data.project.Project;
import com.ccg.oms.common.data.project.ProjectInfo;
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
	public @ResponseBody RestResponse createNewProject(@RequestBody ProjectInfo project){
		RestResponse resp = RestResponse.getSuccessResponse();
		System.out.println(JSON.toJson(project));
		try{
			System.out.println(project);
			service.createNewProject(project);
			resp.setMessage("Done");
		}catch(Exception e){
			e.printStackTrace();
			resp.setStatus(RestResponseConstants.FAIL);
			resp.setMessage(e.getMessage());
		}
		return resp;
	}
	
	@RequestMapping(value="{id}", method=RequestMethod.GET)
	public @ResponseBody RestResponse findProjectById(@PathVariable Integer id){
		RestResponse resp = RestResponse.getSuccessResponse();
		try{
			ProjectInfo info = service.findProjectInfo(id);
			resp.setResult(info);
		}catch(Exception e){
			resp.setStatus(RestResponseConstants.FAIL);
			resp.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return resp;
	}
	
	@RequestMapping(value="all", method=RequestMethod.GET)
	public @ResponseBody RestResponse findAllProject(){
		RestResponse resp = RestResponse.getSuccessResponse();
		try{
			resp.setResult(service.findAllProjectInfo());
		}catch(Exception e){
			resp.setStatus(RestResponseConstants.FAIL);
			resp.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return resp;
	}
	
	@RequestMapping(value="search", method=RequestMethod.GET)
	public @ResponseBody RestResponse searchProjectByName(@RequestParam(value="pname") String name){
		RestResponse resp = RestResponse.getSuccessResponse();
		try{
				System.out.println("pname value..."+name);
				List<Project> projects = service.searchByName(name);
				resp.setResult(projects);
		}catch(Exception e){
			resp.setStatus(RestResponseConstants.FAIL);
			resp.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return resp;
	}

}
