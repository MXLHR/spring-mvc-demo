package com.xianlei.jdk8design.model;

import com.xianlei.spring.web.model.User;

public abstract class OnlineBanking {
	
	public void processCustomer(int id) {
//		Person p = Database.selectPerson(id);
		User u = new User();
		makeCustomerHappy(u);
	}
	
	abstract void makeCustomerHappy(Object p);
	//不同的子类实现该模板方法。  来实现不同的银行不同的方式。
}
