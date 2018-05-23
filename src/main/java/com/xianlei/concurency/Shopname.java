package com.xianlei.concurency;

import java.util.Random;

public class Shopname {
	private String name;
	
	public Shopname(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}
	public String getShopName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	//同步获得价格 
		public double getPrice(String product) {
			return caculaterPrice(product);
		}
		private double caculaterPrice(String product) {
			//计算价格
			delay();
			return new Random().nextDouble() * product.charAt(0) + product.charAt(1);
		}
		public static void delay () {
			try {
				Thread.sleep(1000L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

}
