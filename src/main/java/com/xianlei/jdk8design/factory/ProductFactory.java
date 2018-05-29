package com.xianlei.jdk8design.factory;

class Product {
	private String name;
	
}
class Loan1 extends Product {
	public Loan1() {
	}
}

public class ProductFactory {

	public static Product createProcuct(String name) {
		switch (name) {
		case "loan":
			return new Loan1();
//		case "loan2":
//			return new Product("loan2");
//		case "loan3":
//			return new Product("loan3");
		default:
			throw new RuntimeException("No such product " + name);
		}
	}
}
