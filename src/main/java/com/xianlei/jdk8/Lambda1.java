package com.xianlei.jdk8;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

interface LambdaTestI{
	abstract void print();
}

interface LambdaTest2{
	abstract void print(String a);
}

interface DefaultTest{
	static int a =5 ;
	default void defaultMethod() {
		System.out.println("default method ...");
	}
	int sub(int a,int b);
	static void staticMethod() {
		System.out.println("static method ...");
	}
	
}
public class Lambda1 {
	
	public static void main(String[] args) {
		//java8以前的实现方式--匿名内部类
		DefaultTest dt1 = new DefaultTest() {
			@Override
			public int sub(int a, int b) {
				return a-b;
			}
		};
		
		//lambda表达式
		DefaultTest dt2 = (a,b)->{
			return a-b;
		};
		
		DefaultTest dt3 = (a,b)->a-b;
		
		//TEST
		int c = 5;
		DefaultTest dt4 = (a,b)->a-c;
		System.out.println(dt4.sub(5, 1));
		System.out.println(dt3.sub(5, 2));
		System.out.println(dt2.sub(5, 3));
		System.out.println(dt1.sub(5, 4));
		
		
		//无参构造
		LambdaTestI l1 =()->System.out.println("无参构造方法，打印一句话");
		l1.print();
		
		LambdaTest2 l2 = (e)->System.out.println("有参构造方法，打印出了参数["+e+"]");
		l2.print("柠檬");
		
		//1.引用实例方法 
		/*
		 * 函数式接口><变量名> = <实例>::<实例方法名>
    		调用
    	   <变量名>.接口方法([实际参数...])
		 */
		//如我们引用PrintStram磊中的println方法。
		//我们知道System类中有一个PrintStram的实例方法out,引用System.out::println:
		LambdaTest2 l3 = System.out::println;
		l3.print("引用引用System.out实例方式调用");
		//将l3的实际参数船体给了PrintStream中的println方法，并调用该方法
		
		//2.引用类方法
		/*
		 * 函数式接口>  <变量名> = <类>::<类方法名称>
	       //调用
	       <变量名>.接口方法([实际参数...])
		 */
	      List<Integer>  list = new ArrayList<Integer>();
	        list.add(50);
	        list.add(18);
	        list.add(6);
	        list.add(99);
	        list.add(32);
//	        System.out.println(list.toString()+"排序之前");
//	        LambdaTest3 lt3 = Collections::sort;
//	        lt3.sort(list, (a,b) -> {
//	            return a-b;
//	        });
//	        System.out.println(list.toString()+"排序之后");
	}

	
}

interface LambdaTest3{
	abstract void sort(int[]args);
}
