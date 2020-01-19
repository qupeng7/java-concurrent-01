package com.qupeng.concurrent.part2.vltl;

/**
 * volatile关键字，不能保证原子性：
 * 不能保证修饰的变量的非原子性操作的原子性
 * @author qupeng
 */
public class VolatileTest02 {
	
	private static volatile int b=0;
	
	private static synchronized void testMethod(){
		//从1加到10000
		for(int i=0;i<10000;i++){
			/*
			 * 因为这个b++的操作不是一个原子操作
			 *可以拆解为：
			 *1、读：int temp =b;把b的值0读到自己的内存中
			 *2、改：temp=temp+1;让这个值加1，此时temp的值为1，b的值仍然为0
			 *3、写：b=temp;再将1赋值给b
			 */
			b++;
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
