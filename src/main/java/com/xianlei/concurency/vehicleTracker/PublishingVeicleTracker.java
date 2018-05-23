package com.xianlei.concurency.vehicleTracker;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
/**
 * 安全发布底层状态的车辆跟踪器
 * 
 * @author xianlei
 * @date 2018年5月7日上午9:30:05
 */
public class PublishingVeicleTracker {

	private final ConcurrentMap<String, SafePoint> locations;
	private final Map<String, SafePoint> unmodifyableMap;
	
	public PublishingVeicleTracker(ConcurrentMap<String, SafePoint> locations) {
		this.locations = new ConcurrentHashMap<String, SafePoint>(locations);
		this.unmodifyableMap = Collections.unmodifiableMap(this.locations);
	}

	public Map<String, SafePoint> getLocations() {
		return unmodifyableMap;
	}
	public SafePoint getLocation(String id) {
		return locations.get(id);
	}

	public void setLocation(String id, int x, int y) {
		//改变位置SafePoint
//		if (locations.replace(id, new Point(x, y)) == null) {
//			throw new IllegalArgumentException("invalid vehicle name: " + id);// 无效的 车辆 名称 ：id
//		}
		if(!locations.containsKey(id))
			throw new IllegalArgumentException("invalid vehicle name: " + id);
		locations.get(id).set(x, y);//改变值
	}
}
