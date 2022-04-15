# java-thread-programming

## problem 1. Compute the number of ‘prime numbers’ between 1 and 200000.
* experiment environment 
  * CPU type : Apple M1
  * number of cores : 8 core CPU
  * memory size : 16 GB
  * OS : macOS Monterey

* execution time (ms) per the number of entire threads = {1,2,4,6,8,10,12,14,16,32}.
<img width="600" alt="2" src="https://user-images.githubusercontent.com/76895949/163588071-794b2141-28be-4c69-b6cf-5924b00f138a.png">
<img width="600" alt="1" src="https://user-images.githubusercontent.com/76895949/163588088-cf076a57-4da1-45c0-b171-1b18c346a8ad.png">
  
  * Testing programs were done at 8-cores cpu environment. So even thought you increase the number of threads more than 8, there’s no more performance improvement.
  * But until 8-threads, the more threads you add, the higher performance it shows
  * Dynamic load balancing shows better performance than the others. The reason why it shows better load balancing is explained below.

* Analysis

      (1) pc_static_block.java
  * N number of threads find the number of prime numbers at different range and gather them into one variable at last.
  * Main thread divides the whole range, 0…200000, into n sub range blocks and allocate them to n threads separately.

  <img width="277" alt="a1" src="https://user-images.githubusercontent.com/76895949/163588410-f24951c6-0095-4974-99e0-a39a6ed8693d.png">![스크린샷 2022-04-15 오후 10 42 54](https://user-images.githubusercontent.com/76895949/163588424-11862627-50ea-43d4-a7e1-6decd0b7dec4.png)

  * If an integer gets bigger, it takes more time to check whether it’s prime number or not. So Thread#3(blue) takes more time to count the number of prime numbers at its range than other Threads(red, yellow, green).
  * Therefore, the loads allocated to each thread are not equal.
      









     (2) pc_static_cyclic.java

     

  Let’s look thread#0, To define whether 8k (1 <= k < 25000) is prime number or not, dividing 8k with 2 is enough. So even though thread#1 checks the same length of range with other threads but it works only 3ms and rest.
  In contrast, thread#7 takes way more times to count because it should try to divide more times per integer than thread#0.
  Therefore, the loads allocated to each thread are not equal.
  




















      (3) pc_dynamic.java








  In this case, there’s only one queue which all the threads can get jobs from. They share the queue together. So loads are distributed to the threads randomly and as equal amount. Therefore all thread work equally. 

How to compile and execute the source code

At terminal,
javac pc_static_bock.java
javac pc_static_cyclic.java
javac pc_static_dynamic.java

After compilation execute the program. <java “filename” “numberOfThreads” “numRange”>
java pc_static_block 4 200000
java pc_static_cyclic 6 12000
java pc_static_dynamic 8 200000
