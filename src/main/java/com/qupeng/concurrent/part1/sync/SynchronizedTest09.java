package com.qupeng.concurrent.part1.sync;


/**
 * 不要以字符串常量或者其它常量作为锁定对象
 * 
 * 这样做会产生几个问题：
 * 1、导致引入我们项目作为jar包的上游方，如果在
 * 同步的时候锁定的是同一个我们已锁定的常量，那么
 * 就会产生死锁现象。
 * 
 * 2、如果是我们引入下游方的jar包，而下游方在逻辑中
 * 有对某个常量进行加锁，但是我们由于读不到其源码，
 * 然后我们在加锁的时候也去用到了这个常量，那么也会
 * 产生死锁现象。
 * 
 * 结论：这样的做法会导致排错极为困难，会造成软件的
 * 可维护性和可靠性极差。

 * @author Peter
 */
public class SynchronizedTest09{
	
	
	static class Test{
		
		private String str_1="动力节点";
		
		private String str_2="动力节点";
		
		private Integer itg_1=50;
		
		private Integer itg_2=50;
		
		private Integer itg_3=150;
		
		private Integer itg_4=150;
		
		public void testMethod_1() throws InterruptedException{
			synchronized (str_1) {
				String threadName = Thread.currentThread().getName();
				while(true){
					Thread.sleep(1_000);
					System.out.println(threadName+"：你好，我是复读机，我每1秒中复读一次！");
				}
			}
		}
		
		
		public void testMethod_2() throws InterruptedException{
			synchronized (str_2) {
				String threadName = Thread.currentThread().getName();
				while(true){
					Thread.sleep(1_000);
					System.out.println(threadName+"：你好，我是复读机，我每1秒中复读一次！");
				}
			}
		}
		
	}
	

	public static void main(String[] args) throws InterruptedException {
		
		final Test test=new Test();

		//创建第一个线程并启动
		new  Thread("线程1"){
			
			@Override
			public void run() {
				try {
					test.testMethod_1();
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
					test.testMethod_2();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		};
		
		t2.start();
		
	}
	
}
