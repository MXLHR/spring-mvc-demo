package com.xianlei.jdk8action.domain;

/**
 * Dish:碟
 * 
 * @author stron
 *
 */
public class Dish {
	public enum Type {
		MEAT,FISH,OTHER  //肉，鱼，其他
	}
	
	private final String name;//名称
	private final boolean vegetarian;//素
	private final int calories;//卡路里
	private final Type type;
	
	public Dish(String name, boolean vegetarian, int calories, Type type) {
		super();
		this.name = name;
		this.vegetarian = vegetarian;
		this.calories = calories;
		this.type = type;
	}
	

	@Override
//	public String toString() {
//		return "Dish [name=" + name + ", vegetarian=" + vegetarian + ", calories=" + calories + ", type=" + type + "]";
//	}
	public String toString() {
		return this.name;
	}

	public String getName() {
		return name;
	}

	public boolean isVegetarian() {
		return vegetarian;
	}

	public int getCalories() {
		return calories;
	}

	public Type getType() {
		return type;
	}
	
}
