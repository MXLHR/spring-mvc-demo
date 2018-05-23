package com.xianlei.jdk8function;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class PredicateTest {
	
	public void test1() {
		Predicate<String> predicate = (s) -> s.lastIndexOf(",") > -1 ;
		
		Predicate<String> isEmpty = String::isEmpty;
		
		Predicate<String> isNotEmpty = isEmpty.negate();
		
	}
	//Function 接口产生一个给定类型的结果，有输入参数
	public void test2() {
		Function<String,Integer> toInteger = Integer::valueOf;
		Function<String,String> backToString = toInteger.andThen(String::valueOf);
		backToString.apply("123");
	}
	//Supplier 接口产生一个给定类型的结果，没有输入参数
	public void test3() {
		Supplier<Object> obj = Object::new;
		obj.get(); // new  obj 
	}
	
	

}
