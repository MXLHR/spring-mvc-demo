package com.xianlei.concurency.crawler;

import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class StartIndexMain {
	
	private static final int BOUND = Integer.MAX_VALUE;
	private static final int N_CONSNUMERS = 2;
	
	public static void startIndexing(File[] roots) {
		BlockingQueue<File> queue = new LinkedBlockingDeque<>(BOUND);
		FileFilter filter = new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				return true;
			}
		};
		for(File root : roots) {
			new Thread(new FileCrawler(queue,filter,root)).start();//启动生产者
		}
		for(int i = 0; i< N_CONSNUMERS; i++) {
			new Thread(new Indexer(queue)).start();//启动消费者
		}
	}
	
	public static void main(String[] args) {
		
	}

}
