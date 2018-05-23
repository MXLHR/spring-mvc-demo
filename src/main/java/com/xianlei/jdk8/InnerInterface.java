package com.xianlei.jdk8;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.junit.Test;


/**
 * JDK1.8 api 内置函数接口
 * 
 * @author stron
 *
 */
public class InnerInterface {
	
	public void testConsumers() {
		/**
		 * consumer代表了一在一个输入参数上需要进行的操作。
		 */
		Consumer<Person> greeter = (p)->System.out.println("Hello," + p.firstName);
		greeter.accept(new Person("Luke","Skywalker"));
		
	}
	
	public void testSuppliers() {
		/**
		 * Supplier接口产生一个给定类型的结果。 Supplier没有输入参数。
		 */
		Supplier<Person> ps = Person::new;
		ps.get(); //new Person
	}
	
	//Function 接口接受一个参数，并返回单一的结果。
	//默认方法可以将多个函数串在一起（compse,andThen)
	@Test
	public void testFunction() {
		Function<String,Integer> toInreger = Integer::valueOf;
		Function<String,String> backToString = toInreger.andThen(String::valueOf);
		backToString.apply("123");//"123"
	}

	@Test
	public void predicates() {
		//predicates是一个布尔类型的函数，该函数只有一个输入参数。
		//Predicate接口包含了多种默认方法，用于处理发咋的逻辑动词（add,or,negate
		Predicate<String> pre = (s) ->s.length()>0;
		
		pre.test("foo");
		pre.negate().test("foo");
		
		Predicate<Boolean> nonNull = Objects::nonNull;
		Predicate<Boolean> isNull = Objects::isNull;
		Predicate<String> isEmpty = String::isEmpty;
		Predicate<String> isNotEmpty = isEmpty.negate();
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
