package com.qupeng.concurrent.part1.sync;

/**
 * 看这个程序的输出
 * @author Peter
 */
public class SynchronizedTest02 {
	
	private static int a = 5;

	public static void main(String[] args) {
		//创建5个线程
		for(int i=1;i<10;i++){
			new Thread("线程"+i){
				@Override
				public  void run() {
					a--;
					System.out.println(Thread.currentThread().getName() + "：a=" + a);
				}
			}.start();
		}
	}

}
