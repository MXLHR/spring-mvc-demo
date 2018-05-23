package com.xianlei.concurency;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CellularAutomata {
	
	class Board {
		private int count;
		
		public Board(int count) {
			this.count = count;
		}

		public void commitNewValues() {
			
		}
		public Board getSubBoard(int count, int i) {
			return new Board(count-i);
		}

		public boolean hasConveraged() {
			// TODO Auto-generated method stub
			return false;
		}

		public int getMaxX() {
			// TODO Auto-generated method stub
			return 0;
		}

		public int getMaxY() {
			// TODO Auto-generated method stub
			return 0;
		}

		public void setNewValue(int x, int y, Object computeValue) {
			// TODO Auto-generated method stub
			
		}

		public void waitForConvergence() {
			// TODO Auto-generated method stub
			
		}
	}
	class Worker implements Runnable {
		private final Board board;
		public Worker(Board board) {
			this.board = board;
		}

		@Override
		public void run() {
			while (!board.hasConveraged()) {
				for (int x = 0; x< board.getMaxX(); x ++) 
					for(int y = 0; y< board.getMaxY(); y++)
						board.setNewValue(x, y, computeValue(x, y));
				try {
					barrier.await();
				} catch (InterruptedException ex) {
					return;
				} catch (BrokenBarrierException e) {
					return;
				}
			}
		}
	}
	
	private final Board mainBoard;
	private final CyclicBarrier barrier;
	private final Worker[] workers;
	public CellularAutomata(Board board) {
		this.mainBoard = board;
		int count = Runtime.getRuntime().availableProcessors();
		this.barrier = new CyclicBarrier(count, new Runnable() {
			@Override
			public void run() {
				mainBoard.commitNewValues();
			}
		});
		this.workers = new Worker[count];
		for(int i = 0; i < count; i++)
			workers[i] = new Worker(mainBoard.getSubBoard(count, i));
	}
	public Object computeValue(int x, int y) {
		// TODO Auto-generated method stub
		return null;
	}
	public void start() {
		for(int i = 0; i < workers.length; i++)
			new Thread(workers[i]).start();
		mainBoard.waitForConvergence();
	}
	
}
