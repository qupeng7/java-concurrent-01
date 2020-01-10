package com.qupeng.concurrent.part1.sync;

/**
 * 一个同步方法是否可以调用另一个同步方法？
 * 这个要分情况讨论：
 * 1、如果在同一个线程的同一把锁，那么就是可以的。
 * 2、如果在同一个线程的不是同一把锁那么就需要看锁是否
 * 被其它线程持有了。
 * 3、不同线程的同一把锁，那么就会产生排队现象（同步）
 * @author Peter
 */
public class SynchronizedTest04 {
	
	public static synchronized void syncMethod01() throws InterruptedException{
		String threadName=Thread.currentThread().getName();
		System.out.println(threadName+"：开始执行syncMethod01方法……");
		//休眠3秒，模拟处理业务逻辑
		Thread.sleep(3_000);
		//调用同一个锁对象的同步方法
		syncMethod02();
		System.out.println(threadName+"：执行syncMethod01方法结束！");
	}
	
	public static synchronized void syncMethod02() throws InterruptedException{
		String threadName=Thread.currentThread().getName();
		System.out.println(threadName+"：开始执行syncMethod02方法……");
		//休眠1秒，模拟处理业务逻辑
		Thread.sleep(1_000);
		System.out.println(threadName+"：执行syncMethod02方法结束！");
	}

	public static void main(String[] args) {
		new Thread("线程1"){
			@Override
			public void run() {
				try {
					syncMethod01();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			};
		}.start();
		
		
	}

}
