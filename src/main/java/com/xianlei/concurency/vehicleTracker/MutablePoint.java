package com.xianlei.concurency.vehicleTracker;

public class MutablePoint {
	public int x,y;
	public MutablePoint(MutablePoint p) {
		this.x = p.x;
		this.y = p.y;
	}
}
