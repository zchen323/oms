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
import com.ccg.oms.common.data.RestResponseConstants;
import com.ccg.oms.common.data.document.DocType;
import com.ccg.oms.common.data.project.Project;
import com.ccg.oms.common.data.project.ProjectTemplate;
import com.ccg.oms.common.data.project.ProjectUserRoleType;
import com.ccg.oms.common.data.project.TaskTemplate;
import com.ccg.oms.service.ProjectAdminServices;

@Controller
@RequestMapping("/projectadmin")
public class ProjectAdminController {
	
	@Autowired
	private ProjectAdminServices service;
	

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
	
	@RequestMapping(value="projectUserRoleType/{userRoleType}", method=RequestMethod.DELETE)
	public @ResponseBody RestResponse deleteProjectUserRoleTypes(@PathVariable String userRoleType){
		RestResponse resp = RestResponse.getSuccessResponse();
		try{
			service.deleteProjectUserRoleType(userRoleType);
			resp.setMessage("User role type: " + userRoleType + " has been deleted");
		}catch(Exception e){
			resp.setStatus(RestResponseConstants.FAIL);
			resp.setMessage(e.getMessage());
			e.printStackTrace();		
		}
		return resp;
	}
	
	@RequestMapping(value="projectUserRoleType", method=RequestMethod.POST)
	public @ResponseBody RestResponse saveProjectUserRoleType(@RequestBody ProjectUserRoleType roleType){
		RestResponse resp = RestResponse.getSuccessResponse();
		try{
			service.saveProjectUserRoleType(roleType);
			resp.setMessage("Role type: " + roleType.getRoletype() + ", is saved");
		}catch(Exception e){
			resp.setMessage(e.getMessage());
			resp.setStatus(RestResponseConstants.FAIL);
			e.printStackTrace();
		}
		return resp;
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
		RestResponse resp = RestResponse.getSuccessResponse();
		try{
			service.saveProjectTemplate(input);
			resp.setMessage("Done!");
		}catch(Exception e){
			resp.setMessage(e.getMessage());
			resp.setStatus(RestResponseConstants.FAIL);
			e.printStackTrace();
		}
		return resp;
	}
	
	@RequestMapping(value="projectTemplate/{id}", method=RequestMethod.DELETE)
	public @ResponseBody RestResponse deleteProjectTemaplate(@PathVariable Integer id){
		service.deleteProjectTemplate(id);
		return RestResponse.getSuccessResponse();
	}

	@RequestMapping(value="projectTemplate/config/{id}", method=RequestMethod.POST)
	public @ResponseBody RestResponse saveProjectTemaplateConifg(
			@PathVariable Integer id,
			@RequestBody String configString){
		RestResponse resp = RestResponse.getSuccessResponse();
		try{
			service.saveProjectTtemplateConfig(id, configString);
			resp.setMessage("Project template config is updated");
		}catch(Exception e){
			resp.setStatus(RestResponseConstants.FAIL);
			resp.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return resp;
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
		RestResponse resp = RestResponse.getSuccessResponse();
		try{
			service.saveTaskTemplate(input);
			resp.setMessage("Done!");
		}catch(Exception e){
			e.printStackTrace();
			resp.setMessage(e.getMessage());
			resp.setStatus(RestResponseConstants.FAIL);
		}
		return resp;
	}
	
	@RequestMapping(value="taskTemplate", method=RequestMethod.DELETE)
	public @ResponseBody RestResponse deleteTaskTemaplate(@PathVariable Integer id){
		service.deleteTaskTemplate(id);
		return RestResponse.getSuccessResponse();
	}
	
	@RequestMapping(value="taskTemplate/config/{id}", method=RequestMethod.POST)
	public @ResponseBody RestResponse saveTaskTemaplateConfig(
			@PathVariable Integer id,
			@RequestBody String configString){
		RestResponse resp = RestResponse.getSuccessResponse();
		
		try{
			service.saveTaskTtemplateConfig(id, configString);
			resp.setMessage("Done!");
		}catch(Exception e){
			e.printStackTrace();
			resp.setMessage(e.getMessage());
			resp.setStatus(RestResponseConstants.FAIL);
		}
		return resp;
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
		RestResponse restResp = RestResponse.getSuccessResponse();
		try{
			service.saveDoctype(input);
			restResp.setMessage("Doc type has been saved");
		}catch(Exception e){
			restResp.setMessage(e.getMessage());
			restResp.setStatus(RestResponseConstants.FAIL);
		}
		return restResp;
	}

	@RequestMapping(value="docType/{doctype}", method=RequestMethod.DELETE)
	public @ResponseBody RestResponse deleteDocType(@PathVariable String doctype){
		RestResponse restResp = RestResponse.getSuccessResponse();
		try{
			service.deleteDoctype(doctype);
			restResp.setMessage("Doc type" + doctype + "  has been deleted");
		}catch(Exception e){
			restResp.setMessage(e.getMessage());
			restResp.setStatus(RestResponseConstants.FAIL);
		}
		return restResp;
	}	
}

