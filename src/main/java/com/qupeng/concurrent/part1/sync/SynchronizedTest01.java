package com.qupeng.concurrent.part1.sync;

/**
 * synchronized关键字 对某个对象加锁
 * @author Peter
 */
public class SynchronizedTest01 {

	private int count = 10;

	private Object lock = new Object();

	public void test01() {
		/**
		 * 注意：这里锁的是lock这个引用指向的堆内存中的对象 
		 * （也就是new出来的Object）而不是“lock”这个引用
		 */
		synchronized (lock) {
			count--;
			System.out.println(Thread.currentThread().getName() + "：count=" + count);
		}
	}

	public void test02() {
		/**
		 * 这里用this指代当前调用test02的SynchronizedTest的实例对象
		 */
		synchronized (this) {
			count--;
			System.out.println(Thread.currentThread().getName() + "：count=" + count);
		}
	}

	/**
	 * 这里将synchronized关键字放在方法上，也是锁定调用test03的SynchronizedTest的实例对象
	 */
	public synchronized void test03() {
		count--;
		System.out.println(Thread.currentThread().getName() + "：count=" + count);

	}

}
