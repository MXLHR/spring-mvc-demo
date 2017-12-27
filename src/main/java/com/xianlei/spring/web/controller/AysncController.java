package com.xianlei.spring.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

import com.xianlei.spring.web.service.PushService;

/**
 * 本列演示SSE(Server Send Event 服务端发送事件)的服务器端推送，基于Servlet3.0+的异步方法
 * 其中一种方式需要新式浏览器的支持，第二种方式是跨浏览器的。
 * 
 * 本列演示第二种方式，首先需要servlet.setAsyncSupported(true);//servlet3.0 对异步方法的支持
 * 
 * 
 * @author Xianlei
 *
 */
@Controller
public class AysncController {

	@Autowired
	PushService pushService;//定时任务，定时更新DeferedResult

	@RequestMapping("/defer")
	public @ResponseBody DeferredResult<String> defferedCall() {
		return pushService.getAsyncUpdate();
	}
	@RequestMapping("/defer/close")
	public @ResponseBody String defferedCallClose() {
		pushService.setOpen(false);
		return "pushService have closed";
	}

}
