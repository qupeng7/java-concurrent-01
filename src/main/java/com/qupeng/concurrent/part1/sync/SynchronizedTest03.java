package com.qupeng.concurrent.part1.sync;

/**
 * 同步方法是否可以调用非同步方法？
 * 答案是可以的，线程2执行testMechod方法
 * 在线程1没有结束syncMethod方法时就执行完毕了
 * 而没有说去等到线程1执行syncMethod方法完毕后
 * 再去执行testMechod方法，这就说明同步方法中是可以调用非同步方法的

 * @author Peter
 */
public class SynchronizedTest03 {
	
	public static synchronized void syncMethod() throws InterruptedException{
		String threadName=Thread.currentThread().getName();
		System.out.println(threadName+"：开始执行syncMethod方法……");
		//休眠5秒，模拟处理业务逻辑
		Thread.sleep(5000);
		System.out.println(threadName+"：执行syncMethod方法结束！");
	}
	
	public static void testMechod() throws InterruptedException{
		String threadName=Thread.currentThread().getName();
		System.out.println(threadName+"：开始执行testMechod方法……");
		//休眠3秒，模拟处理业务逻辑
		Thread.sleep(3000);
		System.out.println(threadName+"：执行testMechod方法结束！");
	}

	public static void main(String[] args) {
		new Thread("线程1"){
			@Override
			public void run() {
				try {
					syncMethod();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			};
		}.start();
		
		
		new Thread("线程2"){
			@Override
			public void run() {
				try {
					testMechod();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			};
		}.start();
	}

}
