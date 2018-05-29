package com.xianlei.jdk8design.observer.client;

import com.xianlei.jdk8design.observer.Feet;
import com.xianlei.jdk8design.observer.Observer;

public class ObserverClient {
	
	public static void main(String[] args) {
		
		Feet f = new Feet();
		f.registerObserver(new Observer() {
			@Override
			public void notify(String tweet) {
				if(tweet.contains("xianlei")) {
					System.out.println("成功接受到xianlei66");
				}
			}
		});
		
		f.registerObserver(s -> {
			if(s.contains("666")) {
				System.out.println("成功接受到666");
			}
		});
		
		f.notifyObservers("xianlei 666");
		f.notifyObservers("666");
		
	}

}
