package com.ccg.oms.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ccg.oms.common.data.RestResponse;
import com.ccg.oms.common.data.admin.Admin;
import com.ccg.oms.service.AdminServices;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminServices services;
	
	@RequestMapping(method=RequestMethod.GET)
	public @ResponseBody RestResponse getAdmindata(){
		Admin admin = services.getAdiminData();
		RestResponse resp = RestResponse.getSuccessResponse();
		resp.setResult(admin);
		return resp;
	}
	
}
