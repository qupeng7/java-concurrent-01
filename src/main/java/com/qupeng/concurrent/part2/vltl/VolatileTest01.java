package com.qupeng.concurrent.part2.vltl;


/**
 * volatile关键字，用于修饰变量，是一个变量 在多个线程间是可见的
 * 在多个线程共享同一个变量的时候，可能会出现内存不可见的情况
 * @author Peter
 */
public class VolatileTest01 {

	static class Test {

		public volatile  int b = 0;
//		public  int b = 0;
		
		public void testMethod() {
			String threadNme = Thread.currentThread().getName();
			System.out.println(threadNme + "：开始执行testMethod方法……");
			while (b<2) {
				int a = 1;
				a++;
				a=0;
			}
			System.out.println(threadNme + "：执行testMethod方法结束！");
		}

	}

	public static void main(String[] args) throws InterruptedException {
		
		final Test t=new Test();
		
		new Thread("线程1"){
			
			@Override
			public void run() {
				t.testMethod();
			}
		}.start();

		// 休息1秒
		Thread.sleep(1_000);
		t.b=2;
		System.out.println(Thread.currentThread().getName() + "：此时的b的值是：" + t.b);

	}

}
