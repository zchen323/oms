package com.ccg.oms.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ccg.oms.common.data.RestResponse;
import com.ccg.oms.common.data.document.DocType;
import com.ccg.oms.common.data.project.Project;
import com.ccg.oms.common.data.project.ProjectTemplate;
import com.ccg.oms.common.data.project.ProjectUserRoleType;
import com.ccg.oms.common.data.project.TaskTemplate;
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
	
	// project user role
	@RequestMapping(value="projectUserRoleType/all", method=RequestMethod.GET)
	public @ResponseBody RestResponse getProjectUserRoleTypes(){
		List<ProjectUserRoleType> list = service.getProjectUserRoleTypes();
		RestResponse resp = RestResponse.getSuccessResponse();
		resp.setResult(list);
		return resp;
	}

	
	@RequestMapping(value="projectUsreRoleType", method=RequestMethod.POST)
	public @ResponseBody RestResponse saveProjectUserRoleType(@RequestBody ProjectUserRoleType roleType){
		service.saveProjectUserRoleType(roleType);
		return RestResponse.getSuccessResponse();
	}
	
	
	// project template
	@RequestMapping(value="projectTemplate/all", method=RequestMethod.GET)
	public @ResponseBody RestResponse getProjectTemplates(){
		List<ProjectTemplate> list = service.getProjectTemplate();
		RestResponse resp = RestResponse.getSuccessResponse();
		resp.setResult(list);
		return resp;
	}
	
	@RequestMapping(value="projectTemplate/{id}", method=RequestMethod.GET)
	public @ResponseBody RestResponse getProjectTemplateById(@PathVariable Integer id){
		ProjectTemplate template = service.getProjectTemplateById(id);
		RestResponse resp = RestResponse.getSuccessResponse();
		resp.setResult(template);
		return resp;
	}
	

	@RequestMapping(value="projectTemplate", method=RequestMethod.POST)
	public @ResponseBody RestResponse saveProjectTemaplate(@RequestBody ProjectTemplate input){
		service.saveProjectTemplate(input);;
		return RestResponse.getSuccessResponse();
	}
	
	@RequestMapping(value="projectTemplate", method=RequestMethod.DELETE)
	public @ResponseBody RestResponse deleteProjectTemaplate(@PathVariable Integer id){
		service.deleteProjectTemplate(id);
		return RestResponse.getSuccessResponse();
	}
	
	// task template
	@RequestMapping(value="taskTemplate/all", method=RequestMethod.GET)
	public @ResponseBody RestResponse getTaskTemplates(){
		List<TaskTemplate> list = service.getTaskTemplate();
		RestResponse resp = RestResponse.getSuccessResponse();
		resp.setResult(list);
		return resp;
	}
	
	@RequestMapping(value="taskTemplate/{id}", method=RequestMethod.GET)
	public @ResponseBody RestResponse getTaskTemplateById(@PathVariable Integer id){
		TaskTemplate template = service.getTaskTemplateById(id);
		RestResponse resp = RestResponse.getSuccessResponse();
		resp.setResult(template);
		return resp;
	}
	

	@RequestMapping(value="taskTemplate", method=RequestMethod.POST)
	public @ResponseBody RestResponse saveTaskTemaplate(@RequestBody TaskTemplate input){
		service.saveTaskTemplate(input);;
		return RestResponse.getSuccessResponse();
	}
	
	@RequestMapping(value="taskTemplate", method=RequestMethod.DELETE)
	public @ResponseBody RestResponse deleteTaskTemaplate(@PathVariable Integer id){
		service.deleteTaskTemplate(id);
		return RestResponse.getSuccessResponse();
	}
		
	// docType
	@RequestMapping(value="docType/all", method=RequestMethod.GET)
	public @ResponseBody RestResponse getDocTypess(){
		List<DocType> list = service.getDocTypes();
		RestResponse resp = RestResponse.getSuccessResponse();
		resp.setResult(list);
		return resp;
	}
	
	@RequestMapping(value="docType", method=RequestMethod.POST)
	public @ResponseBody RestResponse saveDocType(@RequestBody DocType input){
		service.saveDoctype(input);
		return RestResponse.getSuccessResponse();
	}
	
}

