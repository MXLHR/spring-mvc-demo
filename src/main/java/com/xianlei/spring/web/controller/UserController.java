package com.xianlei.spring.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xianlei.spring.web.model.User;


/*
 * 引入jackson包，获取对象和json/xml转换的支持
<!-- https://mvnrepository.com/artifact/com.fasterxml.jackson.dataformat/jackson-dataformat-xml -->
<dependency>
    <groupId>com.fasterxml.jackson.dataformat</groupId>
    <artifactId>jackson-dataformat-xml</artifactId>
    <version>2.9.3</version>
</dependency>

 */
@Controller
@RequestMapping("/user")//映射此类的访问路径
public class UserController {

	@RequestMapping(produces="text/plain;charset=UTF-8")//produces可定制返回的response媒体类型和字符集
	public @ResponseBody String index(HttpServletRequest request){
		return "url:"+request.getRequestURI() + " can access";
	}
	//访问路径为 /user/delete/XX   结合@PathVariable接受路径参数
	@RequestMapping(value="/delete/{id}",produces="text/plain;charset=UTF-8")
	public @ResponseBody String delete(@PathVariable String id,HttpServletRequest request){
		
		return "url:"+request.getRequestURI() + " can access,id:"+id;
	}
	//解释参数到对象，访问路径为/user/addUser?name=xxx&sex=xx
	@RequestMapping(value="/addUser",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String addUser(User user,HttpServletRequest request){
		
		return "url:"+request.getRequestURI() + " can access,user name:"+user.getName();
	}
	//访问路径为 /user/name1 或者 /user/name2
	@RequestMapping(value={"/name1","/name2"},produces="text/plain;charst=UTF-8")
	public @ResponseBody String remove(HttpServletRequest request){
		return "url:"+request.getRequestURI() + " can access";
	}
	
	
	
	
}
