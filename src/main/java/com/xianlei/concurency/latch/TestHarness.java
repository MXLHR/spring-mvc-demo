package com.xianlei.concurency.latch;

import java.util.concurrent.CountDownLatch;

public class TestHarness {

	public long timeTasks(int nThreads, final Runnable task) throws InterruptedException {
		final CountDownLatch startGate = new CountDownLatch(1);
		final CountDownLatch endGate = new CountDownLatch(nThreads);

		for (int i = 0; i < nThreads; i++) {
			Thread t = new Thread() {
				@Override
				public void run() {
					try {
						startGate.await();//启动门等待
						try {
							task.run();
						} finally {
							endGate.countDown();//结束门完成
						}
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
					}
				}
			};
			t.start();
		}

		long start = System.nanoTime();
		startGate.countDown();//启动门一起执行 -1
		endGate.await(); //结束门等待 -0
		long end = System.nanoTime();
		return end - start;
	}

}
