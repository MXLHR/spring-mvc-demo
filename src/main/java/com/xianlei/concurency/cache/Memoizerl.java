package com.xianlei.concurency.cache;

import java.util.HashMap;
import java.util.Map;

public class Memoizerl<A, V> implements Computable<A, V> {

	private final Map<A, V> cache = new HashMap<A, V>();
	private final Computable<A, V> c;
	
	public Memoizerl(Computable<A, V> c) {
		this.c = c;
	}

	@Override
	public synchronized V compute(A arg) throws InterruptedException {
		V result = cache.get(arg);
		if(result == null) {
			result = c.compute(arg);
			cache.put(arg, result);
		}
		return result;
	}
	//每次只有一个线程能够知晓compute，如果另一个线程正在计算结果，那么compute的线程可能被阻塞很长时间。

}


















