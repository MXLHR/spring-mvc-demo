package com.xianlei.jdk8design.strategy.client;

import com.xianlei.jdk8design.strategy.IsAllLowerCase;
import com.xianlei.jdk8design.strategy.IsNumeric;
import com.xianlei.jdk8design.strategy.Validator;

public class StrategyClient {

	public static void main(String[] args) {
		// 数字验证器
		Validator numericValidator = new Validator(new IsNumeric());
		// 全部小写验证器
		Validator lowerCaseValidator = new Validator(new IsAllLowerCase());

		boolean b1 = numericValidator.validate("aaaa");
		boolean b2 = lowerCaseValidator.validate("aaaa");
		
		System.out.println("数字验证器：" + b1);
		System.out.println("小写验证器：" + b2);
		
		
		////////////////////////////////////////////
		//Lambda表达式重写策略模式
		//我们知道ValidationStrategy是一个函数接口。 这意味着我们不需要声明新的类
		//来实现不同的策略，通过直接传递Lambda表达式即可
		Validator v1 = new Validator((String s) -> s.matches("[a-z]+"));
		Validator v2 = new Validator((String s) -> s.matches("\\d+"));
		
		boolean b3 = v1.validate("aaaa");
		boolean b4 = v2.validate("aaaa");
		
		//使用Lambda表达式后就不需要再建立两个实现类了。
		//lambda避免了采用策略模式僵化的模板代码
	}
	
	
	
}
