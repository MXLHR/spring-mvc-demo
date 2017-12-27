package com.xianlei.spring.web.advice;

import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice//声明一个控制器建言，组合了@Component主键
public class ExceptionHandlerAdvice {

	@ExceptionHandler(Exception.class)//此处定义全局异常处理，通过value的值过滤拦截的条件，这里拦截所有的Exception
	public ModelAndView exception(Exception ex,WebRequest request){
		ModelAndView mv = new ModelAndView();//error.jsp
		mv.addObject("message", ex.getMessage());
		mv.setViewName("error");
		return mv;
	}
	@ModelAttribute//将键值对添加到全局，所有主键的@RequestMapping的方法都可获得此简直对
	public void addAttributes(Model model){
		model.addAttribute("contextPath","/spring-mvc-demo");
	}
	
	@InitBinder//定值WebDataBinder,配置参考API文档
	public void initBinder(WebDataBinder webDataBinder){
		webDataBinder.setDisallowedFields("id");//此处忽略request参数的id
	}
}
