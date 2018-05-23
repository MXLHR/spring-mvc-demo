package com.xianlei.concurency.safe;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
//CopyOnWriteArrayList来保存各个监听器列表，它是一个线程安全的链表，特别适用于管理监听器列表。
//VisualComponent 可以将它的线程安全性委托给mouseListeners和keyListeners等对象
public class VisualComponent {
	
	interface KeyListener<T> {
		void onEvent(T t);
	}
	interface MouseListener<T> {
		void onEvent(T t);
	}
	
	private final List<KeyListener> keyListeners = new CopyOnWriteArrayList<>();
	private final List<MouseListener> mouseListeners = new CopyOnWriteArrayList<>();
	
	public void addKeyListener(KeyListener listener) {
		keyListeners.add(listener);
	}
	public void addMouseListener(MouseListener listener) {
		mouseListeners.add(listener);
	}
	public void removeKeyListener(KeyListener listener) {
		keyListeners.remove(listener);
	}
	public void removeMouseListener(MouseListener listener) {
		mouseListeners.remove(listener);
	}
}
