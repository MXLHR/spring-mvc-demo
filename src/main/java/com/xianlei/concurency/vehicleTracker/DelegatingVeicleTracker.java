package com.xianlei.concurency.vehicleTracker;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class DelegatingVeicleTracker {

	private final ConcurrentMap<String, Point> locations;
	private final Map<String, Point> unmodifyableMap;
	//	没有任何显示的同步，所有对状态的访问都有ConcurrentHashMap来管理，而且Map所有的键和值都是不可变的。
	public DelegatingVeicleTracker(ConcurrentMap<String, Point> locations, Map<String, Point> unmodifyableMap) {
		this.locations = new ConcurrentHashMap<String, Point>(locations);
		this.unmodifyableMap = Collections.unmodifiableMap(unmodifyableMap);
	}

	public Map<String, Point> getLocations() {
		return unmodifyableMap;
	}
	//如果需要一个不发生变化的车辆试图，那么getLocations可以返回对locations的浅拷贝。
	//由于Map的内容是不可变的，因此只需复制Map的结构，而不用复制它的内容
	public Map<String, Point> getLocations2(){
		return Collections.unmodifiableMap(new HashMap<String,Point>(locations));
	}
	public Point getLocation(String id) {
		return locations.get(id);
	}

	public void setLocation(String id, int x, int y) {
		//改变位置Point
		if (locations.replace(id, new Point(x, y)) == null) {
			/*
			 * 等价于 如果存在就覆盖值，不存在就返回null; if (map.containsKey(key)) { return map.put(key,
			 * value); } else return null; }
			 */
			throw new IllegalArgumentException("invalid vehicle name: " + id);// 无效的 车辆 名称 ：id
		}
	}
	
	/*
	 * 使用的委托的车辆追踪器返回的是一个不可修改但却实时的车辆位置试图。
	 * 如果线程A调用getLocations，而线程B在随后修改了某些点的位置，那么返回给线程A的Map中将反映出这些变化。
	 * 
	 */
}
