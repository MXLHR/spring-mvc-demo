package com.xianlei.spring.web.taskservice;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduledTaskservice {

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	
	@Scheduled(fixedRate = 5000)
	public void reportCurrentTime(){
		//每隔5s执行一次
		System.out.println("【定时任务1】 " +dateFormat.format(new Date()));
	}
	@Scheduled(fixedDelay = 5000)
	public void reportCurrentTimeDelay(){
//		try {
//			Thread.sleep(2000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		//每隔5s执行一次
		System.out.println("【定时任务 fixedDelay】 " +dateFormat.format(new Date()) + ".long:"+System.currentTimeMillis());
	}
	@Scheduled(cron = "10 * * * * *")//使用cron属性克按照指定时间执行，cron是Liunx系统下的定时任务
	public void fixTimeExecution(){
		//在指定的时间执行
		System.out.println("【定时任务2】 " + dateFormat.format(new Date()));
	}
}
