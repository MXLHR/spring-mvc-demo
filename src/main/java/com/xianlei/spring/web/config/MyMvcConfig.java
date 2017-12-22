package com.xianlei.spring.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.xianlei.spring.web.interceptor.HandingtimeInterceptor;

/**
 * 1.�����������viewResolver
 * 2.��̬��Դӳ�䣬��д addResourceHandlers
 * 3.��������������, ��д addInterceptors
 * 4.@ControllerAdvice �Կ�������ȫ�����÷���ͬһ��λ�ã�������@Controller����ķ�������ʹ��
 * @ExceptionHandler,@InitBinder,@ModelAttribute �������������������@RequestMapping�Ŀ������ڵķ�����Ч
 * @ExceptionHandler ����ȫ�ִ�����������쳣
 * @InitBinder ��������WebDataBinder,�����Զ���ǰ̨���󵽲���Model�С�
 * @ModelAttribute @ModelAttribute �󶨼�ֵ��Model�У��˴�����ȫ�ֵ�@RequestMapping���ܻ�ô˴����õļ�ֱ��
 * 
 * ʾ��ʹ��@ExceptionHandler����ȫ���쳣�������Ի��Ľ��쳣������û���
 * 
 * @author Xianlei
 *
 */
@Configuration
@EnableWebMvc
@ComponentScan("com.xianlei.spring.web.*")
public class MyMvcConfig extends WebMvcConfigurerAdapter{

	@Bean
	public InternalResourceViewResolver viewResolver(){
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/classes/views/");
		viewResolver.setSuffix(".jsp");
		viewResolver.setViewClass(JstlView.class);
		return viewResolver;
	}
	@Bean
	public MultipartResolver MultipartResolver(){
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setMaxUploadSize(1000000);
		return multipartResolver;
	}
	/*
	 * @Configuration:
	 * 1.��̬��Դӳ�� �̳�WebMvcConfigurerAdapter����дaddResourceHandlers����
	 * 2.���������� ʵ�ֶ�һ��������ǰ���ҵ����������Servlet��Filter
	 * 
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		//1.��̬��Դӳ��  addResourceHandler���ļ���ŵ�Ŀ¼�� addResourceLocations�Ƕ��Ⱪ¶��·��
		registry.addResourceHandler("/asserts/**").addResourceLocations("classpath:/asserts/");
		
	}
	
	/*
	 * ���������� 
	 */
	@Bean
	public HandingtimeInterceptor handingtimeInterceptor(){
		return new HandingtimeInterceptor();
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(handingtimeInterceptor());
	}
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/index").setViewName("/index");
		registry.addViewController("/toUpload").setViewName("/upload");
		/*
		 * 意思是省去了如下页面转向的代码：
		 * 	
			@RequestMapping("/index")
			public String hello(){
				return "index";
			}
		 */
		
	}
	
	
	
}
