package com.xianlei.spring.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdviceController {

	@RequestMapping("/advice")
	public String getSomething(@ModelAttribute("msg") String msg){
		
		if(!msg.equals("admin")){
			throw new IllegalArgumentException("参数验证失败。" + " 来自@ModelAttribute:"+msg);
		}
		
		return "index";
	}
}
