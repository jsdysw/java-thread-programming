//package test;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

class Worker extends Thread {
    protected BlockingQueue<Integer> queue = null;
    int sum;
    ArrayList<Integer> arr;
    
    public Worker(BlockingQueue<Integer> queue) {
        this.queue = queue;
        this.sum = 0;
        this.arr = new ArrayList<Integer>(50);
    }

    public void run() {
    	for (int i = 0; i<50; i++) {
    		 try {
    			 int num = queue.take();
    			 sum += num;
    			 arr.add(num);
    	     } catch (InterruptedException e) {
    	    	 e.printStackTrace();
    	     }
    	}
    }
    public int getSum() {
    	return sum;
    }
    public void printNumArr() {
		System.out.print("[ ");
    	for (int i = 0; i<50; i++) {
    		System.out.print(" " +  arr.get(i) + " ");
    	}
		System.out.println(" ]");
    }
}


public class ex1 {
	public static void main(String[] args) throws Exception {

        BlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(100);
        
        for (int i = 0; i<100; i++) {
        	queue.put(i+1);
        }
        
        Worker worker1  = new Worker(queue);
        Worker worker2 = new Worker(queue);

        worker1.start();
        worker2.start();
        
        Thread.sleep(2000);
        
        int total_sum = worker1.getSum() + worker2.getSum();
        System.out.println("Sum from 1 to 100 : " + total_sum);
        
        System.out.println("Worker 1's numbers : ");
        worker1.printNumArr();
        
        System.out.println("Worker 2's numbers : ");
        worker2.printNumArr();
    }
}

