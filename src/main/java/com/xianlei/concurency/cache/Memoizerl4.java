package com.xianlei.concurency.cache;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class Memoizerl4<A, V> implements Computable<A, V> {

	private final Map<A, Future<V>> cache = new ConcurrentHashMap<A, Future<V>>();
	private final Computable<A, V> c;
	
	public Memoizerl4(Computable<A, V> c) {
		this.c = c;
	}

	@Override
	public V compute(A arg) throws InterruptedException {
		while(true) {
			Future<V> f = cache.get(arg);
			if(f == null) {
				Callable<V> eval = new Callable<V>() {
					@Override
					public V call() throws Exception {
						return c.compute(arg);
					}
				};
				FutureTask<V> ft = new FutureTask<V>(eval);
//				f = ft;
				f = cache.putIfAbsent(arg, ft);
				if(f == null ) {
					f = ft; 
					ft.run();
				}
			
		}
		try {
			return f.get();
		} catch (CancellationException e) {
			cache.remove(arg,f);
		} catch (ExecutionException e) {
			throw launderThrowable(e.getCause());
		}
		}
	}
	
	private RuntimeException launderThrowable(Throwable t) {
		if(t instanceof RuntimeException) 
			return (RuntimeException) t;
		else if(t instanceof Error)
			throw (Error) t;
		else
			throw new IllegalStateException("Not unchecked",t);
	}

}


















