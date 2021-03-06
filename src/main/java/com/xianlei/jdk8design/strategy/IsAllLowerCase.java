package com.xianlei.jdk8design.strategy;

/**
 * 这个类实现类  验证器策略
 * 
 * @author xianlei
 * @date 2018年5月26日上午11:10:02
 */
public class IsAllLowerCase implements ValidationStrategy {

	@Override
	public boolean execute(String s) {
		return s.matches("[a-z]+");//是否全为小写
	}

}
