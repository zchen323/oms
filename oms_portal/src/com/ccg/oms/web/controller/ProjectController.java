package com.ccg.oms.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/project")
public class ProjectController {

	@RequestMapping(value="name", method=RequestMethod.GET)
	public @ResponseBody Shop getShop(){
		Shop shop = new Shop();
		shop.setLocation("local");
		shop.setName("heb");
		return shop;
	}
}

class Shop{
	String name;
	String location;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
}
