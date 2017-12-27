package com.xianlei.spring.web.service;

import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;
/**
 * 1.在PushService里产生DeferedResult给控制器使用，
 * 通过@Scheduled注解的方法定时更新DeferedResult
 * @author Xianlei
 *
 */
@Service
public class PushService {
	
	private DeferredResult<String> deferredResult;//1
	
	public DeferredResult<String> getAsyncUpdate(){
		deferredResult = new DeferredResult<String>();
		return deferredResult;
	}
	/* fixedRate 与 fixedDelay 的区别？
	 * 只是说是 fixedRate 任务两次执行时间间隔是任务的开始点，
	 * fixedDelay 的间隔是前次任务的结束与下次任务的开始。
	 */
	@Scheduled(fixedDelay = 5000)
	public void refresh(){
		if(deferredResult != null){
			//使用apache.commons.lang 日期工具类
			String result = DateFormatUtils.format(System.currentTimeMillis(), "yyyy-MM-dd hh:mm:ss");
			System.out.println("【定时任务 PushService.refresh】 .setResult:"+result);
			deferredResult.setResult(result);
		}
	}
	
	public void setOpen(boolean open){
		if(open){
			getAsyncUpdate();
		} else {
			deferredResult = null;
		}
	}
	
}
