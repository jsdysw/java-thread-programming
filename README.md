# java-thread-programming

<details>
<summary> 
problem 1. Compute the number of ‘prime numbers’ between 1 and 200000.
</summary>
<div markdown="1">

* **experiment environment**
    * CPU type : Apple M1
    * number of cores : 8 core CPU
    * memory size : 16 GB
    * OS : macOS Monterey

* **execution time (ms) per the number of entire threads = {1,2,4,6,8,10,12,14,16,32}.**

     * Testing programs were done at 8-cores cpu environment. So even thought you increase the number of threads more than 8, there’s no more performance improvement.
     * But until 8-threads, the more threads you add, the higher performance it shows
     * Dynamic load balancing shows better performance than the others. The reason why it shows better load balancing is explained below.
   <img width="600" alt="2" src="https://user-images.githubusercontent.com/76895949/163588071-794b2141-28be-4c69-b6cf-5924b00f138a.png">
   <img width="600" alt="1" src="https://user-images.githubusercontent.com/76895949/163588088-cf076a57-4da1-45c0-b171-1b18c346a8ad.png">
  
* **Analysis**

        (1) pc_static_block.java
  * N number of threads find the number of prime numbers at different range and gather them into one variable at last.
  * Main thread divides the whole range, 0…200000, into n sub range blocks and allocate them to n threads separately.

   <img width="277" alt="a1" src="https://user-images.githubusercontent.com/76895949/163588410-f24951c6-0095-4974-99e0-a39a6ed8693d.png">![스크린샷 2022-04-15 오후 10 42 54](https://user-images.githubusercontent.com/76895949/163588424-11862627-50ea-43d4-a7e1-6decd0b7dec4.png)

  * If an integer gets bigger, it takes more time to check whether it’s prime number or not. So Thread#3(blue) takes more time to count the number of prime numbers at its range than other Threads(red, yellow, green).
  * Therefore, the loads allocated to each thread are not equal.

         (2) pc_static_cyclic.java

    <img width="333" alt="스크린샷 2022-04-15 오후 11 39 06" src="https://user-images.githubusercontent.com/76895949/163589902-6e5a2014-bb2d-4028-964e-25b35b8ce1b2.png">![c](https://user-images.githubusercontent.com/76895949/163589523-d41d5dad-9bfd-464f-bf47-f6c53b026c64.png)

   * Let’s look thread#0, To define whether 8k (1 <= k < 25000) is prime number or not, dividing 8k with 2 is enough. So even though thread#1 checks the same length of range with other threads but it works only 3ms and rest.
   * In contrast, thread#7 takes way more times to count because it should try to divide more times per integer than thread#0.
   * Therefore, the loads allocated to each thread are not equal.

         (3) pc_dynamic.java

      ![c2](https://user-images.githubusercontent.com/76895949/163589535-f82fcf5f-ed8d-4bbf-8dd1-da8f442d1192.png)

   * In this case, there’s only one queue which all the threads can get jobs from. They share the queue together. So loads are distributed to the threads randomly and as equal amount. Therefore all thread work equally. 




* **How to compile and execute the source code**

   ![execute](https://user-images.githubusercontent.com/76895949/163590219-82b45907-df24-4cf1-817a-64583bafbfe0.png)

   * At terminal,
      - javac pc_static_bock.java
      - javac pc_static_cyclic.java
      - javac pc_dynamic.java

   * After compilation execute the program. <java “filename” “numberOfThreads” “numRange”>
      - java pc_static_block 4 200000
      - java pc_static_cyclic 6 12000
      - java pc_dynamic 8 200000


</div>
</details>

<details>
<summary> 
problem 2. Parallel matrix multiplication with static load balancing approach
</summary>
<div markdown="1">

* **experiment environment**

	 * CPU type : Apple M1
	 * number of cores : 8 core CPU
	 * memory size : 16 GB
	 * OS : macOS Monterey

* **execution time (ms) per the number of entire threads = {1,2,4,6,8,10,12,14,16,32}.**

  <img width="600" alt="1" src="https://user-images.githubusercontent.com/76895949/163600047-591b34ba-29e9-4e58-8a35-118540c620b0.png">
  <img width="600" alt="1" src="https://user-images.githubusercontent.com/76895949/163600139-53477c9a-4aa7-497d-952c-758b19fd134f.png">

    * Because this experiment was done at 8-core cpu environment, performance improves until 6-8 threads. Interesting point is that more than 8 threads, the execution time rather increases. In my opinion, excessive number of threads doesn’t always guarantee better performance.
    
    * At 8-core cpu environment, It seems that for example 32 threads (more than 8) make worse queueing delay time.

* **Analysis**

  ![r](https://user-images.githubusercontent.com/76895949/163600237-924b6d98-74bc-4695-9401-f3923bb38904.png)

    * It seems that the loads allocated to each thread are very similar.
  
  
    * Let’s say we multiply two matrices A(m * n) and B(n * k). Then there result matrix C will be m*k. It means that we have to calculate m*k elements to get result C.
    * If you use “t” number of threads, then divide C’s elements into t bunch of tasks for t threads.

   ![스크린샷 2022-04-16 오전 1 57 07](https://user-images.githubusercontent.com/76895949/163600268-93956ab1-6581-469a-b9ff-b8dea94a6776.png)


    * For example, let’s say the result matrix A’s shape is 20 by 20. If you use 3 threads. Each of their task domain will be like the image above. Each thread calculate their separated task domain.


* **How to compile and execute the source code**

     * At terminal, javac MatmultD.java

    ![e](https://user-images.githubusercontent.com/76895949/163600290-62ea05b1-cd31-4bad-9026-720c5ebe2853.png)

     * After compilation execute the program. java MatmultD 6 < mat500.txt
     * 6 means the number of threads to use, < mat500.txt means the file that contains two matrices is given as standard input.
</div>
</details>
	
	
<details>
<summary> 
problem 3. Solve parking garage problem
</summary>
<div markdown="1">
	
* using BlockingQueue
	
   <img width="781" alt="스크린샷 2022-05-09 오후 8 05 15" src="https://user-images.githubusercontent.com/76895949/167397515-315bef79-f71f-4264-8ea6-f436f89b9ecc.png">
	
* using semaphore
	
   <img width="784" alt="스크린샷 2022-05-09 오후 8 06 58" src="https://user-images.githubusercontent.com/76895949/167397740-49adf335-8170-49ed-bb6d-e4b3f2f6ca24.png">

</div>
</details>

<details>
<summary> 
problem 4. Practice Java concurrency utilities
</summary>
<div markdown="1">
	
* **ex1 : BlockingQueue / ArrayBlockingQueue**

	* BlockingQueue is an interface. A class which implements BlockingQueue becomes thread safe, which means that multiple threads can put or take elements from the queue concurrently. Only one thread at the same time can access to the queue. 
If a thread tries to take an element while the queue is empty, the thread becomes blocked until there is an element to take. Similarly, if a thread tries to put an element and the there is no space at the queue, it becomes blocked until other thread takes an element out of the queue.

	* ArrayBlockingQueue is one of the BlockingQueue’s implementations. It stores elements in a fixed array so it cannot save unlimited amounts of elements.

	* Examine Methods 
		* **size()** : return the number of elements stored in blocking queue.
		* **remainingCapacity()** : return the remaining capacity of the blocking queue.
		* **peek()** : return the first element of the blocking queue without removing it. If there’s no element at the queue, it returns null.
		* **element()** : return the first element of the blocking queue without removing it. If there’s no element at the queue, it throws NoSuchElementException.
		* **contains(Object o)** : return true if there’s an element which equals to o, otherwise return false.

	* Insert Methods : insert element into the queue.
		* **add()** : If there’s no space for the new element, it throws an IllegalStateException
		* **put()** : iIf there’s no space for the new element, it blocks thread until the blocking queue has a space.
		* **offer()** : If there’s no space for the new element, it returns false.
		* **offer(long millis, TimeUnit timeUnit)** : If there’s no space for the new element, it waits timeUnit for the space. After the time out with no space were made, it returns false. 

	* Remove Methods : remove the first element at the queue.
		* **remove(Object o)** : find one element which equals to o and remove it. 
		* **take()** : If the blocking queue is empty, If the blocking queue is empty, it block the thread until an element is inserted.
		* **poll()** : If the blocking queue is empty, it returns null.
		* **poll(long millis, TimeUnit timeUnit)** : If the blocking queue is empty, it waits timeUnit for an element to become available. If not, it returns null.

	* Drain Methods
		* **drainTo(Collection dest)** : drains all the elements of the blocking queue into the dest.
		* **drainTo(Collection dest, int maxElements)** : drains up to maxElements from the blocking queue into the dest.


 
* **ex2 : 2. ReadWriteLock**

	* “lock”, “unlock” is used for protecting critical section, for example, multiple threads’ read, write action to same resource. ReadWriteLock is also a thread lock mechanism but the difference is that ReadWriteLock allows multiple threads to read a certain resource, but only one to write it, at a time. If a thread has write lock, the others cannot access to the shared resources. If a thread has read lock, the other readers can also have read lock.
 	* ReadWriteLock is an interface. ReentrantReadWriteLock is implementation of ReadWriteLock.


 
* **ex3 : 3. AtomicInteger**

	* It is an int variable which can be read and written atomically. All methods described below are safe with multiple threads’ calls.

	* Methods
		* **get()**: get integer value.
		* **set(234)**: set integer value.
		* **compareAndSet(expectedValue, newValue)** : Compare expectedValue with current value, and if they are same, set the current value with newValue.
		* **getAndAdd(num)**: get the value of the AtomicInteger and add num to it
		* **addAndGet(num)**: add num to the AtomicInteger and get its result returned
		* **getAndIncrement()**: same as getAndAdd() but increase 1.
		* **incrementAndGet()**: same as addAndget() but increase 1.
		* **decrementAndGet()**: subtract 1 and get.
		* **getAndDecrement()**: get the value and subtract 1.
 
* **ex4 : 4. CyclicBarrier**

	* Barrier is used for synchronization. If a barrier is set, all running threads must wait at the barrier until all threads reach it. After all thread reach the point, they are released and continue running again.
 	* After initializing how many threads cyclic barrier will wait, call barrier.await() then N threads will wait each other at that point
If you pass timeout to await function, barrier.await(10, TimeUnit.SECONDS), after 10 seconds, the barrier release all threads even though all threads didn’t reach the barrier.

</div>
</details>
