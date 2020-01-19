package com.qupeng.concurrent.part1.sync;


/**
 * 一个同步方法是否可以调用另一个同步方法在
 * 子类同步方法中调用父类的同步方法是否可行？
 * @author qupeng
 */
public class SynchronizedTest05 {
	
	public static void main(String[] args) throws InterruptedException {
		/*
		 * 这里创建的是子类的对象，然后是用子类的对象
		 * 去调用方法，所以锁的是同一个对象，可以达到互斥的效果
		 */
		new Child().syncMethod();
		
	}
	
	static class Father{
		
		public synchronized void syncMethod() throws InterruptedException{
			System.out.println("开始执行父类的syncMethod方法……");
			//模拟业务处理，休眠1秒
			Thread.sleep(1_000);
			System.out.println(this.toString()+"父类的syncMethod方法执行结束！");
		}
		
	}
	
	static class Child extends Father{
		
		@Override
		public synchronized void syncMethod() throws InterruptedException{
			System.out.println("开始执行子类的syncMethod方法……");
			super.syncMethod();
			//模拟业务处理，休眠1秒
			Thread.sleep(1_000);
			System.out.println(this.toString()+"子类的syncMethod方法执行结束！");
		}
	}

}
