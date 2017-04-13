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
import com.ccg.oms.common.data.document.Document;
import com.ccg.oms.common.data.project.Project;
import com.ccg.oms.common.data.user.NewUser;
import com.ccg.oms.common.data.user.User;
import com.ccg.oms.common.data.user.UserChangePassword;
import com.ccg.oms.common.data.user.UserInfo;
import com.ccg.oms.common.data.user.UserWithPassword;
import com.ccg.oms.service.UserServices;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserServices services;
	
	@RequestMapping(method=RequestMethod.POST)
	public @ResponseBody RestResponse createNewUse(@RequestBody NewUser input){
		//System.out.println(input);
		RestResponse result = new RestResponse();
		NewUser newuser = input;
		try{
			services.createNewUser(newuser);
			result.setStatus(RestResponseConstants.SUCCESS);
			result.setMessage("User is added");
		}catch(Exception e){
			result.setStatus(RestResponseConstants.FAIL);
			result.setMessage(e.getMessage());
			e.printStackTrace();			
		}
		return result;
	}
	
	@RequestMapping(method=RequestMethod.PUT)
	public @ResponseBody RestResponse updateUse(@RequestBody NewUser input){
		System.out.println(input);
		RestResponse result = new RestResponse();
		NewUser newuser = input;
		try{
			services.updateUser(newuser);
			result.setStatus(RestResponseConstants.SUCCESS);
			result.setMessage("User is updated");
		}catch(Exception e){
			result.setStatus(RestResponseConstants.FAIL);
			result.setMessage(e.getMessage());
			e.printStackTrace();			
		}
		return result;
	}
	
	@RequestMapping(value="{username}", method=RequestMethod.DELETE)
	public @ResponseBody RestResponse deleteUser(@PathVariable String username){
		System.out.println(username);
		RestResponse result = new RestResponse();
		try{
			services.removeUser(username);
			result.setMessage("User: " + username + " has been deleted");
			result.setStatus(RestResponseConstants.SUCCESS);
		}catch(Exception e){
			result.setMessage(e.getMessage());
			result.setStatus(RestResponseConstants.FAIL);
		}
		return result;
	}
	
	@RequestMapping(value="find/{username}", method=RequestMethod.GET)
	public @ResponseBody RestResponse getUser(@PathVariable String username ){
		User user = services.findUserById(username);
		RestResponse response = new RestResponse();
		if(user == null){
			response.setStatus(RestResponseConstants.FAIL);
			response.setMessage("Username: " + username + " not fould");
		}else{
			response.setResult(user);
			response.setStatus(RestResponseConstants.SUCCESS);
		}
		return response;
	}
	
//	@RequestMapping(value="delete/{username}", method=RequestMethod.GET)
//	public @ResponseBody RestResponse deleteUser(@PathVariable String username){
//		services.removeUser(username);
//		return RestResponse.getSuccessResponse();
//	}

	@RequestMapping(value="addToRole/{username}/{role}", method=RequestMethod.GET)
	public @ResponseBody RestResponse addUserToRole(@PathVariable String username, @PathVariable String role){
		services.assignToRole(username, role);
		return RestResponse.getSuccessResponse();
	}

	@RequestMapping(value="removeFromRole/{username}/{role}", method=RequestMethod.GET)
	public @ResponseBody RestResponse removeUserFromRole(@PathVariable String username, @PathVariable String role){
		services.removeFromRole(username, role);
		return RestResponse.getSuccessResponse();
	}
	
	/*
	 * {"username":"chen3","email":"chen@email.com"}
	 */
	@RequestMapping(value="updateEmail", method=RequestMethod.POST)
	public @ResponseBody RestResponse updateEmail(@RequestBody User user){
		services.updateEmail(user.getUsername(), user.getEmail());
		return RestResponse.getSuccessResponse();
	}		
	/*
	 * sample input
	 * {"username":"chen3","email":"chen@gmail.com","password":"123456","roles":["ROLE_USER","XXXXX"]}
	 */
	@RequestMapping(value="create", method=RequestMethod.POST)
	public @ResponseBody RestResponse createUser(@RequestBody UserWithPassword user){
		System.out.println(user.getEmail());
		services.createUser(user);
		return RestResponse.getSuccessResponse();
	}
	
	/*
	 * request payload
	 * {"username":"chen3","oldPassword":"123456","newPassword":"aaaaa"}
	 */
	@RequestMapping(value="changePassword", method=RequestMethod.POST)
	public @ResponseBody RestResponse changePassword(@RequestBody UserChangePassword user){
		RestResponse result = new RestResponse();
		try{
			services.changePassword(user.getUsername(), user.getOldPassword(), user.getNewPassword());
			result.setStatus(RestResponseConstants.SUCCESS);
		}catch(Exception e){
			result.setStatus(RestResponseConstants.FAIL);
			result.setMessage(e.getMessage());
		}
		return result;
	}
	
	@RequestMapping(value="search", method=RequestMethod.GET)
	public @ResponseBody RestResponse searchUserByName(@RequestParam(value="uname") String username ){
		RestResponse response = new RestResponse();
		try{
			List<UserInfo> userInfos = services.searchUserByName(username);
			response.setResult(userInfos);
			
		}catch(Exception e){
			response.setStatus(RestResponseConstants.FAIL);
			response.setMessage(e.getMessage());
		}
		return response;
	}
	
	@RequestMapping(value="myProfile", method=RequestMethod.GET)
	public @ResponseBody RestResponse getMyProfile(HttpServletRequest request){
		RestResponse response = new RestResponse();
		try{
			//Subject.
			String loginUser = request.getRemoteUser();
			System.out.println(request.getUserPrincipal().getName());
			System.out.println("=== login user: " + loginUser);
			//response.setResult();
			
		}catch(Exception e){
			response.setStatus(RestResponseConstants.FAIL);
			response.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return response;
	}
	
	@RequestMapping(value="viewedProjects", method=RequestMethod.GET)
	public @ResponseBody RestResponse getViewedProjects(HttpServletRequest request){
		RestResponse response = new RestResponse();
		try{
			String userid = request.getRemoteUser();
			List<Project> projects = services.getUserProject(userid);
			response.setResult(projects);
			response.setStatus(RestResponseConstants.SUCCESS);
		}catch(Exception e){
			response.setStatus(RestResponseConstants.FAIL);
			response.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return response;
	}
	
	@RequestMapping(value="viewedDocuments", method=RequestMethod.GET)
	public @ResponseBody RestResponse getViewedDocuments(HttpServletRequest request){
		RestResponse response = new RestResponse();
		try{
			String userid = request.getRemoteUser();
			List<Document> projects = services.getUserDocument(userid);
			response.setResult(projects);
			response.setStatus(RestResponseConstants.SUCCESS);
		}catch(Exception e){
			response.setStatus(RestResponseConstants.FAIL);
			response.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return response;
	}		

	@RequestMapping(value="searchedKeyWords", method=RequestMethod.GET)
	public @ResponseBody RestResponse getSearchedKeyWords(HttpServletRequest request){
		RestResponse response = new RestResponse();
		try{
			String userid = request.getRemoteUser();
			List<String> keywords = services.getUserSearchKeyWorld(userid);
			response.setResult(keywords);
			response.setStatus(RestResponseConstants.SUCCESS);
		}catch(Exception e){
			response.setStatus(RestResponseConstants.FAIL);
			response.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return response;
	}		
	
	
}
