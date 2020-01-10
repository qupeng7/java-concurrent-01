package com.qupeng.concurrent.part3.examination;

import java.util.ArrayList;
import java.util.List;


/**
 * taobao examination
 * 实现一个容器，提供两个方法，add和size
 * 写两个线程，线程1添加10个元素到容器中，
 * 线程2实现监控元素的个数，当个数到5个时，
 * 线程2给出提示并结束
 * 
 * 这里使用wait()和notify()方法实现，
 * 注意：wait()会释放锁，而nodify()不会释放锁。
 * 
 * 该例中线程2必须比线程1先启动，才能保持监听状态
 * 因为必须先wait，才有nodify。
 * 
 * 但是本程序的输出还是没有达到预期效果：
 * size=5的时候线程2没有继续往下执行，而是
 * 等到线程1执行完毕后才往下执行的，因为
 * nodify是不释放锁的，而wait被nodify后是需要
 * 重新持有锁的，但是现在这把锁被线程1持有着
 * 所以只能等到线程1离开同步代码块时，线程2
 * 才能继续往下执行。
 * 
 * 解决方案：
 * 线程1在nodify后必须要释放锁，那么就得wait了，
 * 然后线程2被唤醒后也必须再nodify通知线程2。
 * @author Peter
 */
public class Examination04 {

	public static void main(String[] args) {
		final Box_4 box=new Box_4();
		final Object obj=new Object();
		
		new Thread("线程2"){
				
			@Override
			public void run() {
				synchronized (obj) {
					try {
						System.out.println("线程2：开始执行");
						if(box.size()!=5){
							obj.wait();
						}
						System.out.println("线程2：执行完毕！");
						//通知线程2我已经被唤醒了，你也别等着了
						obj.notify();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
		
		
		
		new Thread("线程1"){
					
			@Override
			public void run() {
				synchronized (obj) {
					try {
						System.out.println("线程1：开始执行");
						for(int i=1;i<=10;i++){
							box.add(new Object());
							System.out.println("线程1：add第"+i+"个元素");
							if(box.size()==5){
								obj.notify();
								//释放锁，让线程2得以执行。
								obj.wait();
							}
							Thread.sleep(1_000);
						}
						System.out.println("线程1：执行完毕！");
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
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
class Box_4{
	
	private volatile List<Object> box=new ArrayList<>();
	
	public void add(Object element){
		box.add(element);
	}
	
	public int size(){
		return box.size();
	}
	
}
