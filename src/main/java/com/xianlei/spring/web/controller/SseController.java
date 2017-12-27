package com.xianlei.spring.web.controller;

import java.util.Random;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 本列演示SSE(Server Send Event 服务端发送事件)的服务器端推送，基于Servlet3.0+的异步方法
 * 其中一种方式需要新式浏览器的支持，第二种方式是跨浏览器的。
 * 
 * 方案：当客户端向服务端发送请求，服务端会抓住这个请求不放，等有数据更新的时候才返回给客户端，当客户端收到
 * 消息后，再向服务端发送请求，周而复始。这种方式的好处是减少了服务器的请求数量，大大减少了服务器的压力。
 * 
 * 服务器端推送技术在我们日常开发中较为常用，因为早期人们都是使用Ajax向服务器轮询消息，使浏览器第一时间获得服务端的消息，
 * 但这种方式的轮询频率不好控制，所以大大增加了服务端的压力。
 * 
 * 除了服务器端推送技术以外，还有一个另外的双向通信技术--WebSocket，将在以后演示
 * @author Xianlei
 *
 */
@Controller
public class SseController {
	
	/*
	 * 注意，这里使用输出的媒体类型为text/event-stream，这是服务端端SSE的支持。
	 * 本列演示每5秒钟想浏览器推送随机消息
	 */
	@RequestMapping(value="/push",produces="text/event-stream;charset=UTF-8")
	public @ResponseBody String push(){
		Random r = new Random();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//服务器异常演示
//		if(1==1) throw new RuntimeException("SSE服务器内部异常");
		String resultJson = "data:SSE服务器端推送消息 ---> Random: "+ r.nextInt() + "\n\n";
		return resultJson;
		
	}
	
	public String testPop(){
		System.out.println("into sse pop !");
		return "test-ok:";
	}
}
