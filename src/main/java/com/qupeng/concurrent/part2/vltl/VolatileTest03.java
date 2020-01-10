package com.qupeng.concurrent.part2.vltl;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * volatile关键字，不能保证原子性：
 * 不能保证修饰的变量的非原子性操作的原子性
 * 下面介绍一种比synchronized高效的实现变量原子性操作的
 * 实现方案——使用原子类进行原子操作。
 * @author Peter
 */
public class VolatileTest03 {
	
//	private static volatile int b=0;
	
	private static  AtomicInteger b=new AtomicInteger(0);
	
	
	private static  void testMethod(){
		//从1加到10000
		for(int i=0;i<10000;i++){
			b.getAndIncrement();
		}
	}

	

	public static void main(String[] args) throws InterruptedException {
		
		Thread[] threads=new Thread[5];
		
		//创建5个线程
		for(int i=0;i<5;i++){
			Thread t=new Thread(){
				@Override
				public void run() {
					testMethod();
				}
				
			};
			threads[i]=t;
			t.start();
		}
		
		//等待所有数组中的线程执行结束
		for(Thread thread:threads){
			thread.join();
		}

		System.out.println("多线程并发执行后的b的值："+b);
	}

}
