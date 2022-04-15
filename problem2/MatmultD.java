//package test;

import java.util.*;

class ComputeThread extends Thread {
	  int[][] a;
	  int[][] b;
	  BlockedMat c;
	  public int from; 
	  public int to;
	  long timeDiff = 0;
	  
	  ComputeThread(int[][] a, int[][] b, BlockedMat c, int f, int t) {
		this.a = a;
		this.b = b;
		this.c = c;
		from = f;
		to = t;
	  }
	  long getTimeDiff() {
		  return timeDiff;
	  }
	  public void run() { // overriding, must have this type
		long s = System.currentTimeMillis(); 
		int d = c.col;
		for (int i = from; i<=to; i++) {
			int sum = 0;
			for (int j = 0; j<b.length; j++) {
				 sum += a[i/d][j] * b[j][i%d];
			}
			c.setPartialSum(i/d, i%d, sum);
		}
	    long e = System.currentTimeMillis(); 
		timeDiff = e - s; 
	  }
	  
}

class BlockedMat {
	public int row;
	public int col;
	public int[][] mat;
	
	BlockedMat(int r, int c) {
		row = r;
		col = c;
		mat = new int[row][col];
	}
	synchronized void setPartialSum(int r, int c, int sum) {
		mat[r][c] = sum;
	}
	public void printMatrix() {
		System.out.println("Matrix["+mat.length+"]["+mat[0].length+"]");
		int rows = mat.length;
		int columns = mat[0].length;
	    int sum = 0;
		for (int i = 0; i < rows; i++) {
		   for (int j = 0; j < columns; j++) {
		      System.out.printf("%4d " , mat[i][j]);
		      sum+=mat[i][j];
		   }
		   System.out.println();
		 }
		 System.out.println();
		 System.out.println("Matrix Elements Sum = " + sum + "\n");
	}
}

// command-line execution example) java MatmultD 6 < mat500.txt
// 6 means the number of threads to use
// < mat500.txt means the file that contains two matrices is given as standard input
//
// In eclipse, set the argument value and file input by using the menu [Run]->[Run Configurations]->{[Arguments], [Common->Input File]}.

// Original JAVA source code: http://stackoverflow.com/questions/21547462/how-to-multiply-2-dimensional-arrays-matrix-multiplication
public class MatmultD {
  private static Scanner sc = new Scanner(System.in);
  private static BlockedMat multRes;
  
  public static void main(String [] args) {
	// set thread nums
    int thread_no=0;
    if (args.length==1) thread_no = Integer.valueOf(args[0]);
    else thread_no = 1;
        
    // read matrix a, b from .txt file
    int a[][]=readMatrix();
    int b[][]=readMatrix();
    
    multRes = new BlockedMat(a.length, b[0].length);
    ComputeThread[] ct = new ComputeThread[thread_no];
    int totalNeededComputation = a.length * b[0].length;
    
    // if there's too much thread set appropriate thread num;
    if (totalNeededComputation < thread_no) {
    	System.out.println("Too much thread.. downgrade");
    	thread_no = totalNeededComputation;
    }
    int d = totalNeededComputation / thread_no;
    
    long startTime = System.currentTimeMillis();
    for (int i = 0; i < thread_no; i++) {
    	if (i == thread_no - 1) {
    		ct[i] = new ComputeThread(a, b, multRes, i*d, totalNeededComputation-1);
    	} else {
    		ct[i] = new ComputeThread(a, b, multRes, i*d, (i+1)*d - 1);
    	}
		ct[i].start();
	}
	
	// wait the other threads
	try {
		for (int i = 0 ;i < thread_no; i++) {
			ct[i].join();
	    }
	} catch (InterruptedException e) { System.out.println("Error occurred!");}
    long endTime = System.currentTimeMillis();
   
    multRes.printMatrix();

    System.out.printf("Total number of threads: %d\n" , thread_no);
    System.out.printf("Calculation Time: %d ms\n" , endTime-startTime);
    
    for (int i = 0 ;i < thread_no; i++) {
        System.out.printf("[thread_no]:%2d , [Time]:%4d ms, [Range]:%4d...%4d\n", i, ct[i].getTimeDiff(), ct[i].from, ct[i].to);
	}
  }
  
  public static int[][] readMatrix() {
      int rows = sc.nextInt();
      int cols = sc.nextInt();
      int[][] result = new int[rows][cols];
      for (int i = 0; i < rows; i++) {
          for (int j = 0; j < cols; j++) {
             result[i][j] = sc.nextInt();
          }
      }
      return result;
  } 
}