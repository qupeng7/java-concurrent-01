package com.qupeng.concurrent.part1.sync;

/**
 * 线程在执行过程中，出现异常，锁会被释放么？
 * 答：synchronized默认抛出异常，锁是会被释放的，
 * 下面代码中，run方法为同步方法，线程1中的run在死循环中
 * 抛出了异常，如果锁不被释放，线程2是无法进入run方法继续执行的。
 * 
 * 所以，在多线程并发执行过程中，对出现的异常一定要做妥善处理，
 * 否则就可能会导致数据不一致的情况；
 * 下面的代码中，线程2拿到的a的值就是从3开始的，这和预期的从0开始
 * 是不一致的。
 * 如果此时如果对a的操作处在一个事务中，那么就应该在出现异常的时候将
 * a的值进行重置（做事务的回滚）
 * @author qupeng
 */
public class SynchronizedTest06{

	public static void main(String[] args) throws InterruptedException {

		Runnable r = new Runnable() {

			private int a = 0;

			@Override
			public synchronized void run() {
				try {
					String threadName = Thread.currentThread().getName();
					System.out.println(threadName + "开始执行线程的run方法……");
					while (true) {
						a++;
						System.out.println(threadName + "：a的值为：" + a);
						// 模拟业务逻辑执行，休息1秒
						Thread.sleep(1_000);
						if (a == 3) {
							throw new RuntimeException();
						}
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			}
		};

		new Thread(r,"线程1").start();
		// 2秒后又新建一个线程进行执行同样的逻辑
		Thread.sleep(2_000);
		
		new Thread(r,"线程2").start();
	}
}
