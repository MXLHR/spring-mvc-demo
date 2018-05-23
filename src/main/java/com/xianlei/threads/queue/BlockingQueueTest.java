package com.xianlei.threads.queue;

public class BlockingQueueTest {

	public static void main(String[] args) {
		BlockingQueueWN bq = new BlockingQueueWN(10);
		Thread t1 = new ThreadPush(bq,20);
		Thread t3 = new ThreadPush(bq,10);
		t1.setName("入队线程");
		Thread t2 = new ThreadPop(bq);
		t2.setName("出队线程");
		t3.setName("入队线程2");
		t3.start();
		t1.start();
		t2.start();
		
	}
	
	//入队线程
	private static class ThreadPush extends Thread {
		private final BlockingQueueWN<Integer> queue;
		private final int num;
		public ThreadPush(BlockingQueueWN<Integer> queue,int num) {
			this.queue = queue;
			this.num = num ;
		}
		@Override
		public void run() {
			String threadName = Thread.currentThread().getName();
			int i = num;
			while(i>0) {
//				System.out.println("准备入队...线程_ "+Thread.currentThread().getName());
				System.out.println("准备入队...线程_ "+Thread.currentThread().getName()+"_元素["+i+"]");
				try {
					Thread.sleep(1000);
					queue.enqueue(i);//入队
					System.out.println("入队完成...线程_ "+Thread.currentThread().getName()+"_元素["+i+"]");
					i--;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	//出队线程
	private static class ThreadPop extends Thread {
		BlockingQueueWN<Integer> queue;
		public ThreadPop(BlockingQueueWN<Integer> bq) {
			this.queue = bq;
		}
		@Override
		public void run() {
			while(true) {
				System.out.println("准备出队...线程_ "+Thread.currentThread().getName());
				try {
					Integer i = queue.dequeue();
					System.out.println("出队完成...线程_ "+Thread.currentThread().getName()+"_元素["+i+"]");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
                 
			}
		}
		
	}
}
