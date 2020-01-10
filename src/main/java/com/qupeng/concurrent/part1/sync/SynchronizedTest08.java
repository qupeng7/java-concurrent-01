package com.qupeng.concurrent.part1.sync;


/**
 *  锁定一个对象f，如果这个对象的属性发生改变，
 * 会不会影响到锁的使用呢？
 * 
 * 答：是不影响这个锁对象的正常使用的，但是如果这个对象f的
 * 引用发生改变去指向了另一个对象了，那么这个锁的对象
 * 会变成新指向的那个对象了。
 * 所以应该杜绝将锁定对象的引用去指向另外的对象，才能达到
 * 我们软件想要的结果。

 * @author Peter
 */
public class SynchronizedTest08{
	
	
	static class Test_1{
		
		public Test_2 lock=new Test_2();
		
		public void testMethod() throws InterruptedException{
			synchronized (lock) {
				System.out.println(lock.toString());
				String threadName = Thread.currentThread().getName();
				while(true){
					Thread.sleep(1_000);
					System.out.println(threadName+"：你好，我是复读机，我每1秒中复读一次！");
				}
			}
		}
		
	}
	
	
	static class Test_2{
			
			public   int a=0;
			
	}

	public static void main(String[] args) throws InterruptedException {
		
		final Test_1 test=new Test_1();

		//创建第一个线程并启动
		new  Thread("线程1"){
			
			@Override
			public void run() {
				try {
					test.testMethod();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}.start();
		
		Thread.sleep(3_000);
		
		//创建第二个线程并启动
		Thread t2 = new  Thread("线程2"){
					
			@Override
			public void run() {
				try {
					test.testMethod();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		};
		
		/*
		 * 这里是将lock这个引用去指向了另一个新创建出来的对象
		 * 所以两个线程持有的不是同一个锁对象，所以不能形成互斥现象
		 */
//		test.lock=new Test_2();
		test.lock.a=2;
		t2.start();
		
	}
	
}
