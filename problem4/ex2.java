//package test;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class Reader extends Thread {
	SafeInt shared_int;
	
    public Reader(String name, SafeInt shared_int) {
    	super(name);
    	this.shared_int = shared_int;
    }

    public void run() {
    	for (int i = 0; i<10; i++) {
    		shared_int.readValue(this.getName());
    	}
    }
}

class Writer extends Thread {
	SafeInt shared_int;
	
    public Writer(String name, SafeInt shared_int) {
    	super(name);
    	this.shared_int = shared_int;
    }
    public void run() {
    	for (int i = 0; i<3; i++) {	
    		shared_int.increaseNum(this.getName());
    	}
    }
}

class SafeInt {
	int num;
    ReadWriteLock readWriteLock;
    
    public SafeInt() {
    	num = 10;
    	readWriteLock = new ReentrantReadWriteLock();
    }
    public void readValue(String name) {
    	readWriteLock.readLock().lock();
    	try {
        	System.out.println(name + ": read the value " + num);
        } finally {
        	readWriteLock.readLock().unlock();
        }    	
    }
    public void increaseNum(String name) {
    	readWriteLock.writeLock().lock();
    	try {
        	num += 10;
    		System.out.println(name + ": increase 10 ");
        } finally {
        	readWriteLock.writeLock().unlock();
        }  
    }
}

public class ex2 {
	public static void main(String[] args) throws Exception {
		SafeInt shared_integer = new SafeInt();
		
		Reader reader0 = new Reader("Reader "+0, shared_integer);
		Reader reader1 = new Reader("Reader "+1, shared_integer);

		Writer writer0 = new Writer("Writer "+0, shared_integer);
		Writer writer1 = new Writer("Writer "+1, shared_integer);

		reader0.start();
		reader1.start();

		writer0.start();
		writer1.start();	
    }
}

