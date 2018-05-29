package com.xianlei.jdk8design.handle;

import java.util.function.Function;
import java.util.function.UnaryOperator;

public class HandleClient {

	public static void main(String[] args) {
		// 使用lambda, UnaryOperator<String> 链接函数, 使用andThen进行构造
		UnaryOperator<String> headProcessing = s -> "来自四川，成都的开发者 " + s;
		UnaryOperator<String> spellCheck = s -> s.replaceAll("labda", "lambda");
		
		Function<String,String> function = headProcessing.andThen(spellCheck);
		
		String result = function.apply("鲜磊正在学习labda");
		
		System.out.println(result);

		
	}

}
