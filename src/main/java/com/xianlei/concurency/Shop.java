package com.xianlei.concurency;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.stream.Collectors;

public class Shop {
	
	private String shopName;
	public Shop(String shopName) {
		this.shopName = shopName;
	}
	
	List<Shopname> shops ;
	
	{
		shops = Arrays.asList(new Shopname("shop1"),
				new Shopname("shop2"),
				new Shopname("shop3"),
				new Shopname("shop4"),
				new Shopname("shop5"),
				new Shopname("shop6"),
				new Shopname("Shopname7"),
				new Shopname("Shopname91"),
				new Shopname("Shopname92"),
				new Shopname("Shopname93"),
				new Shopname("Shopname94"),
				new Shopname("Shopname8"));
	}
	
	//parallelStream 并行 使4个操作同时在迭代进行。 否则会一个一个的阻塞。在方法getPrice
	public List<String> findPrices(String product) {
		return shops.parallelStream().map(s -> String.format("%s price is %.2f", s.getName(), s.getPrice(product)))
				.collect(Collectors.toList());
	}
	
	private final Executor excutor = Executors.newFixedThreadPool(Math.min(shops.size(), 100), new ThreadFactory() {
		@Override
		public Thread newThread(Runnable r) {
			Thread t = new Thread(r);
			t.setDaemon(true);// 使用守护线程，这种方式不会阻止程序关停
			return t;
		}
	});
	
	
	public List<String> findPrices2 (String product){
		List<CompletableFuture<String>> priceFutures = shops.stream()
				.map(shop -> CompletableFuture.supplyAsync(()->shop.getShopName() + " price is " + shop.getPrice(product)))
				.collect(Collectors.toList());
		//List<CompletableFuture<String>> 以异步方式计算每种商品的价格
		return priceFutures.stream()
				.map(CompletableFuture::join)//等待所有异步操作结束
				.collect(Collectors.toList());
	}
		//需要处理大量异步任务时这时excutor非常有用
	public List<String> findPrices3 (String product){
		List<CompletableFuture<String>> priceFutures = shops.stream()
				.map(shop -> CompletableFuture
						.supplyAsync(()->shop.getShopName() + " price is " + shop.getPrice(product),excutor))
				.collect(Collectors.toList());
		//List<CompletableFuture<String>> 以异步方式计算每种商品的价格
		return priceFutures.stream()
				.map(CompletableFuture::join)//等待所有异步操作结束
				.collect(Collectors.toList());
	}
	
	 
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	Random random = new Random();
	
	/*
	 * 异步计算价格的方法。 
		CompletableFuture对象包含计算的结果，调用fork创建了另一个线程去执行实际的价格计算，
		不等该耗时计算任务结束，直接返回一个Future实例
	 */
	public Future<Double> getPriceAsync(String product){
		CompletableFuture<Double> futurePrice = new CompletableFuture<>();
		new Thread(()->{
			try {
				double result = caculaterPrice(product);
				futurePrice.complete(result);
			} catch (Exception e) {
				futurePrice.completeExceptionally(e);//抛出异常给future
			}
		}).start();
		return futurePrice;
	}
	//进一步简写    supplyAsync()接受一个生产者（supplier)为参数，返回 CompletableFuture<Future>对象
	// 而且，生产者会交有ForkJoinPool池中的某个执行线程executor运行。 当然也可以自己指定线程。
	public Future<Double> getPriceAsync2(String product){
		return CompletableFuture.supplyAsync(() -> caculaterPrice(product));
	}

	//同步获得价格 
	public double getPrice(String product) {
		return caculaterPrice(product);
	}
	private double caculaterPrice(String product) {
		//计算价格
		delay();
		return random.nextDouble() * product.charAt(0) + product.charAt(1);
	}
	public static void delay () {
		try {
			Thread.sleep(1000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
