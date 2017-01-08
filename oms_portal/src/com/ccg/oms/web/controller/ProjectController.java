package com.ccg.oms.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ccg.oms.common.data.Project;
import com.ccg.oms.service.OmsService;

@Controller
@RequestMapping("/project")
public class ProjectController {
	
	@Autowired
	private OmsService service;
	

	@RequestMapping(value="name", method=RequestMethod.GET)
	public @ResponseBody Project getShop(){
		return service.getProjectById(1);
	}
}

