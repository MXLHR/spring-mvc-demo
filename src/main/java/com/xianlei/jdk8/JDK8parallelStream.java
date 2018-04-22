package com.xianlei.jdk8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.CountDownLatch;

public class JDK8parallelStream {

	/*
	 * Stream 具有平行处理的能力，处理的过程会分而治之。也就是将一个大人物切分程多个小任务
	 */
	public static void parallelStream() {
	    System.out.println("Hello World!");
        // 构造一个10000个元素的集合
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            list.add(i);
        }
        // 统计并行执行list的线程
        Set<Thread> threadSet = new CopyOnWriteArraySet<>();
        // 并行执行
        list.parallelStream().forEach(integer ->{
        	Thread thread = Thread.currentThread();
        	threadSet.add(thread); // 统计并行执行list的线程
        });
        System.out.println("threadSet长度："+threadSet.size());
        System.out.println("系统一共有cpu{}个："+Runtime.getRuntime().availableProcessors());
        try {
			paralleStream2();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void paralleStream2() throws InterruptedException {
		List<Integer> list1= new ArrayList<>();
		List<Integer> list2 = new ArrayList<>();
		for(int i=0;i<100000;i++) {
			list1.add(i);
			list2.add(i);
		}
		Set<Thread> threadSet2 = new CopyOnWriteArraySet<>();
		CountDownLatch countDownLatch = new CountDownLatch(2);
		Thread threadA = new Thread(() ->{
			list1.parallelStream().forEach(integer -> {
				Thread thread = Thread.currentThread();
				threadSet2.add(thread);

			}); 
			countDownLatch.countDown();
		});
		Thread threadB = new Thread(() ->{
			list2.parallelStream().forEach(integer -> {
				Thread thread = Thread.currentThread();
				threadSet2.add(thread);
				
			});
			countDownLatch.countDown();
		});
		
		threadA.start();
		threadA.start();
		
		countDownLatch.await();
		System.out.println("threadSet2一共有线粗数："+threadSet2.size());
		
	     System.out.println(threadSet2);
	}
	
	public static void main(String[] args) {
		parallelStream();
	}
}
