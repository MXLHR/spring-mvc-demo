package com.xianlei.spring.web.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


/**
 * 1.继承HandlerInterceptorAdapter实现自定义拦截器 2.重写preHandle方法，在请求发生前执行
 * 2.重写postHandle方法，在请求完成后执行
 * 
 * @author Xianlei
 *
 */
public class HandingtimeInterceptor extends HandlerInterceptorAdapter {
	
	private static final Logger logger = LoggerFactory.getLogger(HandingtimeInterceptor.class);

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

		logger.debug("-----------------------");
		request.setCharacterEncoding("UTF-8");
		setResponseHeader(response);
		logger.info("本次请求处理时间：" + new Long(handingTime) + "ms. :" + request.getRequestURI());
		logger.debug("-----------------------");
		request.setAttribute("handingTime", handingTime);
		
	}

	private void setResponseHeader(HttpServletResponse response) {
		// 设置编码
		response.setCharacterEncoding("UTF-8");
		for(String headName : response.getHeaderNames()){
			logger.debug(headName+":"+response.getHeader(headName));
		}
	}

}
