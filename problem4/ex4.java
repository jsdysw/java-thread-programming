//package test;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;


class Cumulator extends Thread {
	int from;
	int to;
	int result;
	int name;
	CyclicBarrier barrier;
	
	public Cumulator(int i, int from, int to, CyclicBarrier barrier) {
		this.from = from;
    	this.to = to;
    	this.result = 0;
    	this.name = i;
    	this.barrier = barrier;
	}
	
	public void run() {
		for (int i = from; i < to/2; i++) {
            result += i;
        }
		System.out.println("tread "+ name+ " : Additon half done "+ result);
		
		try {
			this.barrier.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (BrokenBarrierException e) {
			e.printStackTrace();
		}
		
		for (int i = to/2; i <= to; i++) {
            result += i;
        }
		System.out.println("tread "+ name+ " : Additon done "+ result);
	}
	public int getResult() {
    	return result;
    }
}


public class ex4 {
	public static void main(String[] args) throws Exception {
		CyclicBarrier barrier = new CyclicBarrier(3);
	
		Cumulator t1 = new Cumulator(1, 0, 100, barrier);
		Cumulator t2 = new Cumulator(2, 0, 100, barrier);
		Cumulator t3 = new Cumulator(3, 0, 100, barrier);
		
		t1.start();
		t2.start();
		t3.start();
	}
}

