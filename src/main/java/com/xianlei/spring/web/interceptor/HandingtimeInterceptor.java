package com.xianlei.spring.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
/**
 * 1.继承HandlerInterceptorAdapter实现自定义拦截器
 * 2.重写preHandle方法，在请求发生前执行
 * 2.重写postHandle方法，在请求完成后执行
 * @author Xianlei
 *
 */
public class HandingtimeInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		long startTime = System.currentTimeMillis();
		request.setAttribute("startTime", startTime);
		
		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		long startTime = (long) request.getAttribute("startTime");
		request.removeAttribute("startTime");
		long endTime = System.currentTimeMillis();
		long handingTime = endTime - startTime;
		
		System.out.println("本次请求处理时间：" + new Long(handingTime) + "ms. :"+request.getRequestURI());
		
		request.setAttribute("handingTime", handingTime);
		
	}
	
}
