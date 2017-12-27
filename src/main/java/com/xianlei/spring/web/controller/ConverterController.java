package com.xianlei.spring.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xianlei.spring.web.model.User;

@Controller
public class ConverterController {

	@RequestMapping(value="/convert",produces = {"application/x-wisely"})
	public @ResponseBody User convert(@RequestBody User user){
		return user;
	}
	
	
}
