package com.xianlei.spring.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloController {

	@RequestMapping("/hello")
	public String index(){
		return "index";
	}
	
	public String sayHello(HttpServletRequest request){
		String contextPath = (String) request.getAttribute("contextPath");
		System.out.println("获取全局参数 --> contextPath:"+contextPath );
		return "index";
	}
}
