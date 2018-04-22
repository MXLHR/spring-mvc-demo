package com.xianlei.jdk8action;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.Test;

class User {
	private Integer id;
	private String name;
	private String sex;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	public User(Integer id, String name, String sex) {
		super();
		this.id = id;
		this.name = name;
		this.sex = sex;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", sex=" + sex + "]";
	}
	
	
}


public class StreamMy1 {
	
	//定义一个User的集合，将它转成map
	@Test
	public void test1() {
		
		List<User> users = Arrays.asList(
				new User(1,"zhangsan","man"),
				new User(2,"lisi","woman"),
				new User(3,"wangwu","man"),
				new User(3,"zhaoliu","woman"),
				new User(5,"liqi","man")
				);
		
		
		//转成Map<String,Object>
		Map<Integer, String> nameMap = users.parallelStream().collect(Collectors.toMap(
				User::getId, User::getName,(oldValue,newValue) -> newValue));
		Map<Integer, Object> userMap = users.parallelStream().collect(Collectors.toMap(
				User::getId, (e)->e,(oldValue,newValue) -> newValue));
		
		System.out.println(nameMap);
		System.out.println(userMap.toString());
		
		
		
	}
	

}
