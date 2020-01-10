package com.qupeng.concurrent.part3.examination;

import java.util.ArrayList;
import java.util.List;


/**
 * taobao examination
 * 实现一个容器，提供两个方法，add和size
 * 写两个线程，线程1添加10个元素到容器中，
 * 线程2实现监控元素的个数，当个数到5个时，
 * 线程2给出提示并结束
 * @author Peter
 */
public class Examination01 {



	public static void main(String[] args) {
		final Box_1 box=new Box_1();
		
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
				while(true){
					if(box.size()==5){
						break;
					}
				}
				System.out.println("线程2执行完毕！");
			}
		}.start();
	}

}

/**
 * 这样实现有什么问题？
 * @author Peter
 */
class Box_1{
	
	private List<Object> box=new ArrayList<>();
	
	public void add(Object element){
		box.add(element);
	}
	
	public int size(){
		return box.size();
	}
	
}
