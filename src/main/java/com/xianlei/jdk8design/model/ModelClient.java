package com.xianlei.jdk8design.model;

import com.xianlei.spring.web.model.User;

public class ModelClient {
	
	public static void main(String[] args) {
		new OnlineBankingLambda().processCustomer(1337, (User u)->{
			System.out.println("name: "+ u.getName());
		});
	}

}
