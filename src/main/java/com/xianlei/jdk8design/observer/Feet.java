package com.xianlei.jdk8design.observer;

import java.util.ArrayList;
import java.util.List;

public class Feet implements Subject {
	
	private final List<Observer> observers = new ArrayList<>();

	@Override
	public void registerObserver(Observer observer) {
		observers.add(observer);
	}

	@Override
	public void notifyObservers(String tweet) {
		observers.forEach(o -> o.notify(tweet));
	}

}
