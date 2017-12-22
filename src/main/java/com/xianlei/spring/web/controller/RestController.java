package com.xianlei.spring.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;

import com.xianlei.spring.web.model.User;



@org.springframework.web.bind.annotation.RestController
@RequestMapping("/rest")
public class RestController {

	@RequestMapping(value="/getUser",produces={"application/json;charset=UTF-8"})
	public User getUser(User user){
		user.setName(user.getName()+"yy");
		return user;
	}
	@RequestMapping(value="/getUserXml",produces={"application/xml;charset=UTF-8"})
	public User getUserXml(User user){
		user.setName(user.getName()+"yy");
		return user;
	}
	
}
