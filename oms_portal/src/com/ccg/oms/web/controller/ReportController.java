package com.ccg.oms.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ccg.oms.common.data.RestResponse;
import com.ccg.oms.common.data.RestResponseConstants;
import com.ccg.oms.service.ReportService;
import com.ccg.oms.service.UserServices;

@Controller
@RequestMapping("/report")
public class ReportController {

	@Autowired
	ReportService service;

	@Autowired
	UserServices userServices;
	
	@RequestMapping(value="projectSummary", method=RequestMethod.GET)
	public @ResponseBody RestResponse findFirst10Project(){
		RestResponse resp = RestResponse.getSuccessResponse();
		try{
			resp.setResult(service.getProjectSummary());
		}catch(Exception e){
			resp.setStatus(RestResponseConstants.FAIL);
			resp.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return resp;
	}

}
