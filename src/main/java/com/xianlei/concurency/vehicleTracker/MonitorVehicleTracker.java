package com.xianlei.concurency.vehicleTracker;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Monitor 监控
 * Vehicle 车辆,交通工具
 * Tracke 追踪者
 * @author xianlei
 * @date 2018年5月5日下午7:11:27
 */
public class MonitorVehicleTracker {
	
	private final Map<String,MutablePoint> locations;//表示车辆的位置

	public MonitorVehicleTracker(Map<String, MutablePoint> locations) {
		this.locations = deepCopy(locations);
	}

	public synchronized Map<String, MutablePoint> getLocations() {
		return deepCopy(locations);
	}
	/*
	 * 在某种程度上,这种不存在性能问题，但在车辆容器非常大的情况下降极大地降低性能，每次调用都要复制数据。
	 * 因此会出现一种错误情况————虽然车辆的实际位置发生了变化，但返回的信息却保持不变。这种情况是好还是坏，要取决于你的需求。
	 * 如果在location集合上存在内部的一致性需求，那么这就是优点。  如果调用者需要每辆车的最新信息，那么这就是缺点。
	 */
	
	public synchronized void setLocation(String id, int x, int y) {
		MutablePoint loc = locations.get(id);
		if(loc == null)
			throw new IllegalArgumentException("No such ID: " + id);
		loc.x = x;
		loc.y = y;
	}
	
	public synchronized MutablePoint getLocation(String id) {
		MutablePoint loc = locations.get(id);
		return loc == null ? null : new MutablePoint(loc);//通过构造函数拷贝，防止对象逸出。
	}
	
	private static Map<String, MutablePoint> deepCopy(Map<String, MutablePoint> m) {
		Map<String,MutablePoint> result = new HashMap<>();
		for(String id : m.keySet()) {
			result.put(id, new MutablePoint(m.get(id)));//通过构造函数拷贝
		}
		return Collections.unmodifiableMap(result);//返回不可修改（只读）的集合
	}
	/*
	 * 虽然累MutablePoint不是线程安全的，但追踪器类是线程安全的。它所包含的Map对象和可变的Point对象都未曾发布。
	 * 当需要返回车辆的位置时，通过MutablePoint拷贝构造函数 或者 deepCopy方法来复制正确的值，
	 * 从而生成一个新的Map对象，并且该对象中的值与原来Map对象中的key值和value值相同。
	 */
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
