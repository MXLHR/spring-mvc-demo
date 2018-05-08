package com.xianlei.concurency.cache;

public interface Computable<A, V> {
	V compute(A arg) throws InterruptedException;
}
