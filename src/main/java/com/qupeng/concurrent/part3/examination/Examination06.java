package com.qupeng.concurrent.part3.examination;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;


/**
 *  taobao examination
 * 实现一个容器，提供两个方法，add和size
 * 写两个线程，线程1添加10个元素到容器中，
 * 线程2实现监控元素的个数，当个数到5个时，
 * 线程2给出提示并结束
 * 
 * 这里使用CountDownLatch/CyclicBarrier/Semaphore
 * 都可以简便解决这个需求
 * @author Peter
 */
public class Examination06 {
	

	public static void main(String[] args) {
		final Box_6 box=new Box_6();

		final Semaphore reachSema=new Semaphore(0);	
		
		
//		final Semaphore addSema=new Semaphore(10);
		
		final Semaphore addSema=new Semaphore(1);
		
		
		new Thread("线程2"){
			
			@Override
			public void run() {
					try {
						System.out.println("线程2：开始执行");
						if(box.size()!=5){
							reachSema.acquire();
						}
						System.out.println("线程2：执行完毕！");
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
			}
		}.start();
		
		
		
		
		new Thread("线程1"){
					
			@Override
			public void run() {
					try {
						System.out.println("线程1：开始执行");
						for(int i=1;i<=10;i++){
							addSema.acquire();
							box.add(new Object());
							System.out.println("线程1：add第"+i+"个元素");
							if(box.size()==5){
								reachSema.release();
							}
							addSema.release();
							Thread.sleep(1_000);
						}
//						addSema.release();//这里是创建10个信号灯的实现
						System.out.println("线程1：执行完毕！");
					} catch (Exception e) {
						e.printStackTrace();
					}
			}
		}.start();
		
}

}

/**
 * 加上volatile后使box的修改可以得到通知
 * 
 * @author Peter
 */
class Box_6{
	
	private volatile List<Object> box=new ArrayList<>();
	
	public void add(Object element){
		box.add(element);
	}
	
	public int size(){
		return box.size();
	}
	
}
