package com.xianlei.concurency.semaphore;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;

public class BoundedHashSet<T> {
	
	private final Set<T> set;
	private final Semaphore sem;
	public BoundedHashSet(Set<T> set, Semaphore sem) {
		this.set = Collections.synchronizedSet(new HashSet<T>());;
		this.sem = sem;
	}
	
	public boolean add(T o) throws InterruptedException {
		sem.acquire();
		boolean wasAdded = false;
		try {
			wasAdded = set.add(o);
			return wasAdded;
		}finally {
			if(!wasAdded) {
				sem.release();
			}
		}
	}
	public boolean remove(Object o) {
		boolean wasRemoved = set.remove(o);
		if(wasRemoved)
			sem.release();
		return wasRemoved;
	}
	
	
	

}
