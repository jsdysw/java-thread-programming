# java-thread-programming

## problem 1. Compute the number of ‘prime numbers’ between 1 and 200000.
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


# problem 2. Parallel matrix multiplication with static load balancing approach
* **experiment environment**
	   * CPU type : Apple M1
	   * number of cores : 8 core CPU
	   * memory size : 16 GB
	   * OS : macOS Monterey

* **execution time (ms) per the number of entire threads = {1,2,4,6,8,10,12,14,16,32}.**
  
      *Because this experiment was done at 8-core cpu environment, performance improves until 6-8 threads. Interesting point is that more than 8 threads, the execution time rather increases. In my opinion, excessive number of threads doesn’t always guarantee better performance.
      *At 8-core cpu environment, It seems that for example 32 threads (more than 8) make worse queueing delay time.

* **Analysis**

  
      *It seems that the loads allocated to each thread are very similar.
  
  
      *Let’s say we multiply two matrices A(m * n) and B(n * k). Then there result matrix C will be m*k. It means that we have to calculate m*k elements to get result C.
      *If you use “t” number of threads, then divide C’s elements into t bunch of tasks for t threads.


      *For example, let’s say the result matrix A’s shape is 20 by 20. If you use 3 threads. Each of their task domain will be like the image above. Each thread calculate their separated task domain.


* **How to compile and execute the source code**

      * At terminal,
(*) javac MatmultD.java

      * After compilation execute the program. 
(*) java MatmultD 6 < mat500.txt
6 means the number of threads to use, < mat500.txt means the file that contains two matrices is given as standard input.
