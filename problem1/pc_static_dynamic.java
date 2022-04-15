//package test;

import java.util.concurrent.ArrayBlockingQueue;

class PrimeThread extends Thread {
	  ArrayBlockingQueue<Integer> q; 
	  int count = 0;
	  long timeDiff = 0;
	  
	  PrimeThread(ArrayBlockingQueue<Integer> queue) {
		  q = queue;
	  }
	  
	  public void run() { // overriding, must have this type
		  long s = System.currentTimeMillis();
		  while (!q.isEmpty()) {
			  try {
				  if (isPrime(q.take())) 
					  count++;
			  } catch (InterruptedException e) {
				  System.out.println("error occurred while taking element out of queue ");
			  } 
		  }
		  long e = System.currentTimeMillis(); 
		  timeDiff = e - s; 
	  }
	  long getTimeDiff() {
		  return timeDiff;
	  }
	  int getCount() {
		  return count;
	  }
	  
	  private boolean isPrime(int x) {
			int i; 
			if (x<=1) return false; 
			
			for (i=2;i<x;i++) 
				if (x%i == 0)
					return false; 
			return true; 
		}
	}

public class pc_static_dynamic {
	private static int NUM_END = 200000; // default input 
	private static int NUM_THREADS = 1; // default number of threads 
	
	public static void main (String[] args) {
		if (args.length == 2) {
			NUM_THREADS = Integer.parseInt(args[0]); 
			NUM_END = Integer.parseInt(args[1]); 
		}
		
		int[] numbers = new int[NUM_END+1];
		numbers[NUM_END] = NUM_END;
		ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(NUM_END);
		for (int i = 0; i<NUM_END; i++) {
			numbers[i] = i;
			queue.add(i+1);
		}
	    PrimeThread[] pt = new PrimeThread[NUM_THREADS];
		
	    int counter=0; 
	    
		// find prime numbers from 2 to 200000
		long startTime = System.currentTimeMillis();		
		for (int i = 0; i < NUM_THREADS; i++) {
			pt[i] = new PrimeThread(queue);
			pt[i].start();
		}
		
		// wait the other threads
		try {
			for (int i = 0 ;i < NUM_THREADS; i++) {
				pt[i].join();
		        counter += pt[i].getCount();
		    }
		} catch (InterruptedException e) { System.out.println("Error occurred!");}
		long endTime = System.currentTimeMillis(); 
		
		
		// calculate and print the result and the time taken
		long timeDiff = endTime - startTime; 
		System.out.println("Program Execution Time: " + timeDiff +"ms"); 
		System.out.println("1..." + (NUM_END-1) + " prime# counter=" + counter + "\n"); 
		
		for (int i = 0 ;i < NUM_THREADS; i++) {
			System.out.println("#" + i + " thread execution time : " + pt[i].getTimeDiff() +"ms");

		}
	}

}


