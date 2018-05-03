package com.xianlei.threads.queue;

public class BlockingQueueTest {

	public static void main(String[] args) {
		
	}
	
	//入队线程
	private static class ThreadPush implements Runnable {
		private final BlockingQueueWN<Integer> queue;
		public ThreadPush(BlockingQueueWN<Integer> queue) {
			this.queue = queue;
		}
		@Override
		public void run() {
			String threadName = Thread.currentThread().getName();
			int i =20;
			while(i>0) {
				try {
					Thread.sleep(1000);
					System.out.println("i="+i);
					queue.enqueue(i--);//入队
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	//出队线程
	private static class ThreadPop implements Runnable {
		BlockingQueueWN<Integer> queue;
		public ThreadPop(BlockingQueueWN<Integer> bq) {
			this.queue = bq;
		}
		@Override
		public void run() {
			while(true) {
				 System.out.println(Thread.currentThread().getName()
                         +" will pop.....");
				try {
					Integer i = queue.dequeue();
					System.out.println(" i="+i.intValue()+" alread pop");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
                 
			}
		}
		
	}
}
