## Spring Boot Asynchronous Execution

### Thread Scheduler in Java:
Thread scheduler in java is the part of the JVM that decides which thread should run.
There is no guarantee that which runnable thread will be chosen to run by the thread scheduler.
Only one thread at a time can run in a single process.

### Executers returns different type of ThreadPools to specific need:
1. **public static ExecutorService newSingleThreadExecutor():**    
This approach creates an Executor that uses a single worker thread operating off an unbounded queue. Tasks are guaranteed to execute sequentially, and no more than one task will be active at any given time.
2. **public static ExecutorService newFixedThreadPool(int nThreads):**  
This approach creates a thread pool that reuses a fixed number of threads. Created nThreads will be active at the runtime. If additional tasks are submitted when all threads are active, they will wait in the queue until a thread is available.
3. **public static ExecutorService newCachedThreadPool():**     
This approach creates a thread pool that creates new threads as needed, but will reuse previously constructed threads when they are available. These pools will typically improve the performance of programs that execute many short-lived asynchronous tasks. If no existing thread is available, a new thread will be created and added to the pool. Threads that have not been used for 60 seconds are terminated and removed from the cache.
4. **public static ScheduledExecutorService newScheduledThreadPool(int corePoolSize):**  
Creates a thread pool that can schedule commands to run after a given delay, or to execute periodically.
Cons: 1-Unbounded queue is harmful
5. **public static ExecutorService newWorkStealingPool():**  
Creates a work-stealing thread pool using all available processors as its target parallelism level
Use cases:1-For divide and conquer type of problems.  2-Effective use of idle threads. Idle threads steals tasks from busy threads.
Cons: 1-Unbounded queue size is harmful


In Java 8, a new type of thread pool is introduced as newWorkStealingPool() to complement the existing ones. Java gave a very succinct definition of this pool as:
“Creates a work-stealing thread pool using all available processors as its target parallelism level.”
Let’s explore this pool in more detail and see what it brings to our development toolbox.
As its name says, it’s based on a work-stealing algorithm, where a task can spawn other, smaller tasks, which are added to queues of parallel processing threads. If one thread has finished its work and has nothing more to do, it can “steal” the work from the other thread’s queue.
But this work-stealing mechanism is already used by ForkJoinPool in Java and is highly useful when your task(s) spawn smaller tasks, which can be proactively picked up by any available thread, reducing the thread idle time.


### Future vs CompletableFuture:
- CompletableFuture is an extension to Java’s Future API which was introduced in Java 5.
- A Future is used as a reference to the result of an asynchronous computation. It provides an isDone() method to check whether the computation is done or not, and a get() method to retrieve the result of the computation when it is done.
- Future API was a good step towards asynchronous programming in Java but it lacked some important and useful features

### Limitations of Future:
1. **It cannot be manually completed:**    
Future does not notify you of its completion. It provides a get() method which blocks until the result is available.
2. **You cannot perform further action on a Future’s result without blocking:** 
Sometimes you need to execute a long-running computation and when the computation is done, you need to send its result to another long-running computation, and so on.
3. **Multiple Futures cannot be chained together:**    
 you have 10 different Futures that you want to run in parallel and then run some function after all of them completes. You can’t do this as well with Future.
4. **No Exception Handling:**   
Future API does not have any exception handling construct.

### What’s a CompletableFuture?
- CompletableFuture is used for asynchronous programming in Java. Asynchronous programming is a means of writing non-blocking code by running a task on a separate thread than the main application thread and notifying the main thread about its progress, completion or failure.
- This way, your main thread does not block/wait for the completion of the task and it can execute other tasks in parallel.
- Having this kind of parallelism greatly improves the performance of your programs.
- CompletableFuture implements Future and CompletionStage interfaces and provides a huge set of convenience methods for creating, chaining and combining multiple Futures. It also has a very comprehensive exception handling support.

### Running asynchronous computation using runAsync():
If you want to run some background task asynchronously and don’t want to return anything from the task, then you can use CompletableFuture.runAsync() method. It takes a Runnable object and returns CompletableFuture<Void>.

### Run a task asynchronously and return the result using SupplyAsync():
- A Supplier<T> is a simple functional interface which represents a supplier of results. It has a single get() method where you can write your background task and return the result.
- Once again, you can use Java 8’s lambda expression to make the above code more concise.

### A note about Executor and Thread Pool:
- You might be wondering that - Well, I know that the runAsync() and supplyAsync() methods execute their tasks in a separate thread. But, we never created a thread right?
- Yes! CompletableFuture executes these tasks in a thread obtained from the global ForkJoinPool.commonPool().

### Executer:
All the methods in the CompletableFuture API has two variants - One which accepts an Executor as an argument and one which doesn’t

### Combining two CompletableFutures together:
1. Combine two dependent futures using thenCompose()
2. Combine two independent futures using thenCombine()

### Combining multiple CompletableFutures together:
1. CompletableFuture.allOf()
2. CompletableFuture.anyOf()

The join() method is similar to get(). The only difference is that it throws an unchecked exception if the underlying CompletableFuture completes exceptionally.

### CompletableFuture Exception Handling:
1. Handle exceptions using exceptionally() callback
2. Handle exceptions using the generic handle() method

### Async in Spring Boot:
Spring comes with @EnableAsync annotation and can be applied on application classes for asynchronous behavior. This annotation will look for methods marked with @Async annotation and run in background thread pools. The @Async annotated methods can return CompletableFuture to hold the result of an asynchronous computation.
- In order to use @Async, we need to know about some crucial things:
- @Async must be applied to public method only.
- self-invocation - calling the async method from within the same class. It won’t work.
- Because in this case, although it creates a proxy, the call bypasses the proxy and directly call the method so that Thread will not be spawned.

By default, Spring will be searching for an associated thread pool definition: either a unique TaskExecutor bean in the context, or an Executor bean named "taskExecutor" otherwise. If neither of the two is resolvable, a SimpleAsyncTaskExecutor will be used to process async method invocations.
Besides, annotated methods having a void return type cannot transmit any exception back to the caller. By default, such uncaught exceptions are only logged. 
To customize all this, implement AsyncConfigurer and provide:
1. your own Executor through the getAsyncExecutor() method, and
2. your own AsyncUncaughtExceptionHandler through the getAsyncUncaughtExceptionHandler() method.



further references:     
- https://www.baeldung.com/spring-async   
- https://howtodoinjava.com/spring-boot2/rest/enableasync-async-controller/   
- https://dzone.com/articles/spring-boot-async-methods    
- https://stackoverflow.com/questions/17659510/core-pool-size-vs-maximum-pool-size-in-threadpoolexecutor  
- https://www.callicoder.com/java-8-completablefuture-tutorial/   
- https://riptutorial.com/java/example/20199/use-cases-for-different-types-of-executorservice 
- https://dzone.com/articles/java-executor-service-types  
- https://dzone.com/articles/diving-into-java-8s-newworkstealingpools 
- https://www.geeksforgeeks.org/countdownlatch-in-java/   
- https://docs.spring.io/spring/docs/4.2.x/spring-framework-reference/html/scheduling.html    
- https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/scheduling/annotation/EnableAsync.html

<hr/>
<a href="mailto:eng.motahari@gmail.com?"><img src="https://img.shields.io/badge/gmail-%23DD0031.svg?&style=for-the-badge&logo=gmail&logoColor=white"/></a>


