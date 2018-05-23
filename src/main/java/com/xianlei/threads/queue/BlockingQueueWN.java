package com.xianlei.threads.queue;

import java.util.LinkedList;
import java.util.List;

/**
 * 有界阻塞队列
 * 
 * @author xianlei
 * @date 2018年5月3日上午9:11:05
 */
public class BlockingQueueWN<T> {
	
	private List<T> queue = new LinkedList<>();
	/**
	 * 队列的长度
	 */
	private final int limit;
	
	public BlockingQueueWN(int limit) {
		this.limit = limit;
	}
	
	//入队
	public synchronized void enqueue(T item) throws InterruptedException {
		while(this.queue.size() == this.limit ) {
			System.out.println("准备入队...线程_ "+Thread.currentThread().getName()+"_元素["+item+"]...正在入队...已达到上限，等待出队...");
			this.wait();//队列长度已达到上限，等待在这里 。出队后继续入队
		}
		//将数据入队，可以肯定出队的线程正在等待
		if(this.queue.size() == 0 ) {
			System.out.println("准备入队...线程_ "+Thread.currentThread().getName()+"_元素["+item+"]...正在入队...第一个元素，通知可以出队了...");
			this.notifyAll();//入队了，通知出队
		}
		
		this.queue.add(item);//入队
	}
	
	//出队
	public synchronized T dequeue () throws InterruptedException {
		while(this.queue.size() == 0) {
			System.out.println("准备出队...线程_ "+Thread.currentThread().getName()+"...没有元素了，等待入队...");
			wait(); //如果没有元素了，等待入队
		}
		if(this.queue.size() == this.limit) {
			System.out.println("准备出队...线程_ "+Thread.currentThread().getName()+"...最后一个元素，通知可以入队了...");
			notifyAll(); //通知入队
		}
		return (T) this.queue.remove(0);
	}

	
	
	

}
