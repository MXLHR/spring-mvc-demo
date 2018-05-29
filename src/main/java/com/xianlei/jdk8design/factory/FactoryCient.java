package com.xianlei.jdk8design.factory;

import java.util.function.Supplier;

public class FactoryCient {

	public static void main(String[] args) {
		Product p = ProductFactory.createProcuct("loan1");
		
		//lambda
		Supplier<Product> loanSupplier = Loan1::new;
		Product p2 = loanSupplier.get();
		
		
		
	}
}
