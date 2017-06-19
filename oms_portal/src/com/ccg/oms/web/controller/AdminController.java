package com.ccg.oms.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ccg.oms.common.data.RestResponse;
import com.ccg.oms.common.data.admin.Admin;
import com.ccg.oms.common.data.user.UserInfo;
import com.ccg.oms.service.AdminServices;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminServices services;
	
	@RequestMapping(method=RequestMethod.GET)
	public @ResponseBody RestResponse getAdmindata(HttpServletRequest request){
		Admin admin = services.getAdiminData();
		RestResponse resp = RestResponse.getSuccessResponse();
		resp.setResult(admin);
		
		String loginUser = request.getRemoteUser();
		// find the current user and set to admin object
		for(UserInfo u:admin.getUsers())
		{
			System.out.println(u.getUsername());
			if(u.getUsername().equals(loginUser))
			{
				admin.setCurrentUser(u);
			}
		}
		//System.out.println(request.getUserPrincipal().getName());
		System.out.println("=== login user: " + loginUser);
		
		return resp;
	}
	
}
