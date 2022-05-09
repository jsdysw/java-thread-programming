//package test;

import java.util.concurrent.atomic.AtomicInteger;

class Thread1 extends Thread {
	AtomicInteger atomicInt;
	public Thread1(AtomicInteger i) {
		this.atomicInt = i;
	}
	
	public void run() {
		for (int i = 0; i<3; i++) {
			atomicInt.getAndAdd(10);
		}
	}
}

class Thread2 extends Thread {
	AtomicInteger atomicInt;
	public Thread2(AtomicInteger i) {
		this.atomicInt = i;
	}
	
	public void run() {
		for (int i = 0; i<3; i++) {
			atomicInt.getAndAdd(10);
		}
	}
}

public class ex3 {
	public static void main(String[] args) throws Exception {
		AtomicInteger atomicInteger = new AtomicInteger(1);

		
		atomicInteger.set(100);

		System.out.println("Get and add : " + atomicInteger.getAndAdd(10));
		System.out.println("Add and get : " + atomicInteger.addAndGet(10));
		
		Thread1 t1 = new Thread1(atomicInteger);
		Thread2 t2 = new Thread2(atomicInteger);
		t1.run();
		t2.run();
		
		int theValue = atomicInteger.get();
		System.out.println("After summation " + theValue);
  }
}

