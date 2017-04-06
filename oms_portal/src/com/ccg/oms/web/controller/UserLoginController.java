package com.ccg.oms.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ccg.oms.common.data.RestResponse;
import com.ccg.oms.common.data.RestResponseConstants;
import com.ccg.oms.service.UserServices;

@Controller
@RequestMapping("/login")
public class UserLoginController {

	@Autowired
	private UserServices services;

	@RequestMapping(value = "validateUser", method = RequestMethod.POST)
	public @ResponseBody RestResponse changePassword(@RequestParam("user") String user,
			@RequestParam("pass") String pass) {
		RestResponse result = new RestResponse();
		try {
			if (services.validateUser(user, pass)) {
				result.setStatus(RestResponseConstants.SUCCESS);
			} else {
				result.setStatus(RestResponseConstants.FAIL);
			}
		} catch (Exception e) {
			result.setStatus(RestResponseConstants.FAIL);
			result.setMessage(e.getMessage());
		}
		return result;
	}

	@RequestMapping(value = "userRoles/{user}", method = RequestMethod.GET)
	public @ResponseBody RestResponse findUserRoles(@PathVariable("user") String user) {
		RestResponse response = new RestResponse();
		try {
			List<String> userRoles = services.findUserRoles(user);
			response.setResult(userRoles);
			response.setStatus(RestResponseConstants.SUCCESS);
		} catch (Exception e) {
			response.setStatus(RestResponseConstants.FAIL);
			response.setMessage(e.getMessage());
		}
		return response;
	}
}
