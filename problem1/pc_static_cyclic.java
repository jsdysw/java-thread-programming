//package test;

class PrimeThread extends Thread {
	int r;
	int n;
	int end; 
	int count = 0;
	long timeDiff = 0;
	  
	PrimeThread(int _n, int _end, int _r) {
		n = _n;
		r = _r;
	    end = _end;
	}
	  
	public void run() { // overriding, must have this type
		long s = System.currentTimeMillis(); 
		int x = 0;
		while (n * x + r <= end) {
			if (isPrime(n * x + r)) 
				count++; 
		    x++;
		}
		long e = System.currentTimeMillis(); 
		timeDiff = e - s; 
	}
	long getTimeDiff() {
	    return timeDiff;
	}
	int getR() {
		return r;
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

public class pc_static_cyclic {
	private static int NUM_END = 200000; // default input 
	private static int NUM_THREADS = 1; // default number of threads 
	
	public static void main (String[] args) {
		if (args.length == 2) {
			NUM_THREADS = Integer.parseInt(args[0]); 
			NUM_END = Integer.parseInt(args[1]); 
		}
		
	    PrimeThread[] pt = new PrimeThread[NUM_THREADS];
		
	    int counter=0; 
	    
		// find prime numbers from 2 to 200000
		long startTime = System.currentTimeMillis();		
		for (int i = 0; i < NUM_THREADS; i++) {
			pt[i] = new PrimeThread(NUM_THREADS, NUM_END, i);
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
			System.out.println("#" + i + " thread : range " + NUM_THREADS + "k + " + pt[i].getR());
			System.out.println("   execution time : " + pt[i].getTimeDiff() +"ms");
		}
	}

}


