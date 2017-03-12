package com.ccg.oms.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import com.ccg.oms.common.data.project.ProjectUser;
import com.ccg.oms.common.data.project.Task;
import com.ccg.oms.common.data.project.TaskNote;
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
	@RequestMapping(value="first10", method=RequestMethod.GET)
	public @ResponseBody RestResponse findFirst10Project(){
		RestResponse resp = RestResponse.getSuccessResponse();
		try{
			resp.setResult(service.findFirst10());
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
	
	@RequestMapping(value="task/comment", method=RequestMethod.POST)
	public @ResponseBody RestResponse addTaskComment(@RequestBody TaskNote taskNote, HttpServletRequest request){
		RestResponse resp = RestResponse.getSuccessResponse();
		try{
				String loginUser = request.getRemoteUser();
				taskNote.setUser(loginUser);
				service.addTaskComment(taskNote);
		}catch(Exception e){
			resp.setStatus(RestResponseConstants.FAIL);
			resp.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return resp;
	}

	@RequestMapping(value="task", method=RequestMethod.PUT)
	public @ResponseBody RestResponse updateTask(@RequestBody Task task){
		RestResponse resp = RestResponse.getSuccessResponse();
		try{
				service.updateTask(task);
		}catch(Exception e){
			resp.setStatus(RestResponseConstants.FAIL);
			resp.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return resp;
	}

	@RequestMapping(value="task", method=RequestMethod.POST)
	public @ResponseBody RestResponse addTask(@RequestBody Task task){
		RestResponse resp = RestResponse.getSuccessResponse();
		try{
				service.addTask(task);
		}catch(Exception e){
			resp.setStatus(RestResponseConstants.FAIL);
			resp.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return resp;
	}	
	
	@RequestMapping(value="user", method=RequestMethod.POST)
	public @ResponseBody RestResponse addProjecUser(@RequestBody ProjectUser projectUser){
		RestResponse resp = RestResponse.getSuccessResponse();
		try{
				service.addProjectUser(projectUser);
<<<<<<< Upstream, based on origin/master
				// now attach users to response
=======
>>>>>>> e7ecd8f re- user add/remove refresh
				List<ProjectUser>users=service.findProjectUserByProjectId(projectUser.getProjectId());
				resp.setResult(users);
				
		}catch(Exception e){
			resp.setStatus(RestResponseConstants.FAIL);
			resp.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return resp;
	}	
	@RequestMapping(value="user", method=RequestMethod.DELETE)
	public @ResponseBody RestResponse addProjecUser(
			@RequestParam(value="projectId") Integer projectId, 
			@RequestParam(value="userId") String userId,
			@RequestParam(value="projectUserRole") String role){
		
		RestResponse resp = RestResponse.getSuccessResponse();
		try{
				service.removeUserFromProject(projectId, userId, role);
				List<ProjectUser>users=service.findProjectUserByProjectId(projectId);
				resp.setResult(users);
				
		}catch(Exception e){
			resp.setStatus(RestResponseConstants.FAIL);
			resp.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return resp;
	}
	
	
}
