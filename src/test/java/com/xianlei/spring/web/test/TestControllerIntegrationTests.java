package com.xianlei.spring.web.test;
import static
org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static
org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static
org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static
org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static
org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static
org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.xianlei.spring.web.config.MyMvcConfig;
import com.xianlei.spring.web.test.service.TestUserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={MyMvcConfig.class})
@WebAppConfiguration("src/mian/resources")//1
public class TestControllerIntegrationTests {
	
	private MockMvc mockMvc;//2
	
	@Autowired
	private TestUserService userService;

	@Autowired
	WebApplicationContext wac;
	
	@Autowired
	MockHttpSession session;
	
	@Autowired
	MockHttpServletRequest request;
	
	@Before
	public void setup(){
		//MockMvc模拟MVC对象，通过这里进行初始化Mvc对象
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}
	
	@Test
	public void testNoralController() throws Exception {
		mockMvc.perform(get("/normal"))//模拟向/normal 进行get请求
			.andExpect(status().isOk())//预期控制器返回状态为200
			.andExpect(view().name("page"))//预期view的名称为page
		.andExpect(forwardedUrl("/WEB-INF/classes/views/page.jsp"))//真实的page.jsp的地址
			.andExpect(model().attribute("msg", userService.saySomething()));//预期model里的值是msg,hello
		
	}
	@Test
	public void testRestController() throws Exception {
		mockMvc.perform(get("/testRest"))
			.andExpect(status().isOk())
			.andExpect(content().contentType("text/plain;charset=UTF-8"))//预期返回值的媒体类型
			.andExpect(content().string(userService.saySomething()));//逾期返回值的内容为service的返回内容
	}
	
}
