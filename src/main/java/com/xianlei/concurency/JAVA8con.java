package com.xianlei.concurency;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class JAVA8con {
	
	public void test1() {
		Runnable task = () -> {
			String threadName = Thread.currentThread().getName();
			System.out.println("hello,my name is "+ threadName);
			try {
				//TimeUnit是一个操作时间的一个比较方便的枚举类。除此之外，你还可以使用 Thread.sleep(1000)
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		};
		
//		task.run();
		Thread t = new Thread(task);
		t.start();
	}
	
	public void test2() {
		ExecutorService executor = Executors.newSingleThreadExecutor();
		executor.submit(() -> {
		    String threadName = Thread.currentThread().getName();
		    System.out.println("Hello " + threadName);
		});
		//executor 必须要手动停止 。 shutdown()与shutdownNow().
		try {
		    executor.shutdown();
		    executor.awaitTermination(5, TimeUnit.SECONDS);
		    //结束executor之前等待一段时间
		    //过了这个时间如果executor仍然没有结束则立即结束此执行体
		}
		catch (InterruptedException e) {
		}
		finally {
		    if (!executor.isTerminated()) {
		        System.err.println("cancel non-finished tasks");
		    }
		    executor.shutdownNow();
		    System.out.println("shutdown finished");
		}
	}
	
	public void  test3 () throws InterruptedException, ExecutionException {
		Callable<Integer> task = () -> {
		    try {
		        TimeUnit.SECONDS.sleep(1);
		        return 666;
		    }
		    catch (InterruptedException e) {
		        throw new IllegalStateException("task interrupted", e);
		    }
		};
		//提交给executor去执行
		ExecutorService executor = Executors.newFixedThreadPool(1);
		Future<Integer> future = executor.submit(task);
		//任务未完成
		//执行代码...

		Integer result = future.get();//任务完成 
		//future.get()方法会阻塞当前线程直到callback结束
		//执行代码...
		
		System.out.println("future done? " + future.isDone());
		System.out.print("result: " + result);
	}
	
	public void test4() {
		ExecutorService executor = Executors.newFixedThreadPool(1);

		Future<Integer> future = executor.submit(() -> {
		    try {
		        TimeUnit.SECONDS.sleep(2);
		        return 666;
		    }
		    catch (InterruptedException e) {
		        throw new IllegalStateException("task interrupted", e);
		    }
		});

		try {
			future.get(1, TimeUnit.SECONDS);//设置超时
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			e.printStackTrace();
		} 
		
	}
	
	public void test5 () {
		
	}
	
	
	
	

}
