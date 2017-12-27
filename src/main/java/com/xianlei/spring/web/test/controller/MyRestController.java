package com.xianlei.spring.web.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xianlei.spring.web.test.service.TestUserService;

@RestController
public class MyRestController {
	
	@Autowired
	TestUserService userService;
	
	@RequestMapping(value="/testRest",produces={"text/plain;charset=UTF-8"})
	public String testRest(){
		return userService.saySomething();
	}

}
