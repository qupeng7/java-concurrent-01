package com.qupeng.concurrent.part3.examination;

import java.util.ArrayList;
import java.util.List;


/**
 * taobao examination
 * 实现一个容器，提供两个方法，add和size
 * 写两个线程，线程1添加10个元素到容器中，
 * 线程2实现监控元素的个数，当个数到5个时，
 * 线程2给出提示并结束
 * @author qupeng
 */
public class Examination02 {

	public static void main(String[] args) {
		final Box_2 box=new Box_2();
		
		new Thread("线程1"){
			
			@Override
			public void run() {
				try {
					for(int i=1;i<=10;i++){
						box.add(new Object());
						System.out.println("线程1：add第"+i+"个元素");
						Thread.sleep(1_000);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}.start();
		
		
		new Thread("线程2"){
				
			@Override
			public void run() {
				try {
					while(true){
						if(box.size()==5){
							Thread.sleep(2_000);
							System.out.println("线程2：我看到容器中已经有5个元素啦！！！");
							break;
						}
					}
					System.out.println("线程2：执行完毕！");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}

}

/**
 * 加上volatile使box的修改可以得到通知
 * 这样实现有什么问题？
 * 
 * 1、由于add和size没有加锁，那么就不具备互斥性，
 * 有可能会出现当线程2判断完size==5后，线程切换了
 * 然后线程1又加了一次已经到6了，然后在切换回线程2
 * 此时，线程2才break，这样就与原逻辑不符。
 * 
 * 2、用死循环的方式十分浪费CPU的资源
 * @author qupeng
 */
class Box_2{
	
	private volatile List<Object> box=new ArrayList<>();
	
	public void add(Object element){
		box.add(element);
	}
	
	public int size(){
		return box.size();
	}
	
}
