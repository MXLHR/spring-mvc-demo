package com.xianlei.jdk8design.observer;

public interface Subject {

	void registerObserver(Observer observer);
	
	void notifyObservers(String tweet);
}
