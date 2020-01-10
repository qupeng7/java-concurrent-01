package com.qupeng.concurrent.part1.sync;


/**
 * 从synchronized思考合理地设置锁的粒度
 * 尽量使synchronized的代码区域小，减小锁的粒度
 * @author Peter
 */
public class SynchronizedTest07{

	public static void main(String[] args)  {
		//创建服装店对象
		final Test clothingStore=new Test();

		//创建第一个线程：模拟第一个人进入服装店
		new Thread("线程1"){
			
			@Override
			public void run() {
				clothingStore.fineGrain();
			}
			
		}.start();
		
		//创建第二个线程：模拟第二个人进入服装店
		new Thread("线程2"){
			
			@Override
			public void run() {
				clothingStore.fineGrain();
			}
			
		}.start();
		
		
		
	}
	
	static class Test{
		
		
		public   void coarseGrain(){
			synchronized (this) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				String threadName = Thread.currentThread().getName();
				System.out.println(threadName+"：1、进入服装店……");
				System.out.println(threadName+"：2、挑选衣服……");
				System.out.println(threadName+"：3、进入试衣间换衣服……");
				System.out.println(threadName+"：4、离开服装店……");
			}
		}


		public   void fineGrain(){
			String threadName = Thread.currentThread().getName();
			System.out.println(threadName+"：1、进入服装店……");
			System.out.println(threadName+"：2、挑选衣服……");
			synchronized (this) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(threadName+"：3、进入试衣间换衣服……");
			}
			System.out.println(threadName+"：4、离开服装店……");
		}
	}
	
}
