package com.xianlei.concurency.crawler;

import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.BlockingQueue;

/**
 * 桌面搜索应用程序中的生产者任务和消费者任务
 * 
 * @author xianlei
 * @date 2018年5月7日下午2:17:06
 */
public class FileCrawler implements Runnable {
	
	private final BlockingQueue<File> fileQueue;
	private final FileFilter fileFilter;
	private final File root;
	
	public FileCrawler(BlockingQueue<File> queue, FileFilter fileFilter, File root) {
		this.fileQueue = queue;
		this.fileFilter = fileFilter;
		this.root = root;
	}

	@Override
	public void run() {
		try {
			crawl(root);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	private void crawl(File root) throws InterruptedException {
		File[] entries = root.listFiles(fileFilter);
		if(entries != null) {
			for(File entry : entries) {
				if(entry.isDirectory()) {
					crawl(entry);
				} else if(!alreadyIndexed(entry)) {
					fileQueue.put(entry);
				}
			}
		}
	}

	private boolean alreadyIndexed(File entry) {
		return false;
	}

}
