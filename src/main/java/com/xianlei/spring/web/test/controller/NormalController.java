package com.xianlei.spring.web.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xianlei.spring.web.test.service.TestUserService;

@Controller
public class NormalController {
	
	@Autowired
	TestUserService userService;
	
	@RequestMapping("normal")
	public String testPage(Model model){
		model.addAttribute("msg",userService.saySomething());
		return "page";
	}
	
	

}
