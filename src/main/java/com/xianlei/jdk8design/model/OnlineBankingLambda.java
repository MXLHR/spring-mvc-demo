package com.xianlei.jdk8design.model;

import java.util.function.Consumer;

import com.xianlei.spring.web.model.User;

public class OnlineBankingLambda {
	
	public void processCustomer(int id, Consumer<User> makeuserHappy) {
		User u = new User();
		makeuserHappy.accept(u);//使用lambda后，就不需要创建子类去实现了。
	}
	
//	abstract void makeCustomerHappy(Object p);
	//不同的子类实现该模板方法。  来实现不同的银行不同的方式。
}
