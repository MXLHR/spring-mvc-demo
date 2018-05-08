package com.xianlei.concurency.latch;

import static org.hamcrest.CoreMatchers.instanceOf;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Preloader创建了一个FutureTask，其中包含从数据库加载产品信息的人物，以及一个指向运算的线程。
 * 通过start方法来启动线程。
 * 当程序随后需要productInfo时，可以调用get方法，如果数据已经加载将返回这些数据，否则将等待加载完成后再返回。
 * 
 * @author xianlei
 * @date 2018年5月7日下午3:53:56
 */
public class Preloader {
	
	private final FutureTask<ProductInfo> future =  new FutureTask<>(new Callable<ProductInfo>() {
		@Override
		public ProductInfo call() throws Exception {
			return loadProductInfo();
		}
	});
	
	private final Thread thread = new Thread(future);
	
	public void start() {
		thread.start();
	}
	
	public ProductInfo get() throws DataLoadException,InterruptedException {
		try {
			return future.get();
		} catch (InterruptedException | ExecutionException e) {
			Throwable cause = e.getCause();
			if(cause instanceof DataLoadException)
				throw (DataLoadException) cause;
			else
				throw launderThrowable(cause);
		}
	}
	
	
	private RuntimeException launderThrowable(Throwable t) {
		if(t instanceof RuntimeException) 
			return (RuntimeException) t;
		else if(t instanceof Error)
			throw (Error) t;
		else
			throw new IllegalStateException("Not unchecked",t);
	}

	protected ProductInfo loadProductInfo() {
		//从数据库中加载产品信息
		//...
		return new ProductInfo();
	}

}
