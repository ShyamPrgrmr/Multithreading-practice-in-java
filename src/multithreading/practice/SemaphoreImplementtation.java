package multithreading.practice;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SemaphoreImplementtation {

	private int queuelength = 10;
	private Semaphore write = new Semaphore(queuelength);
	private Semaphore read = new Semaphore(0);
	private Queue<Integer> queque = new ArrayDeque<Integer>(queuelength); 
	private Random random = new Random();  
	private Lock lock = new ReentrantLock(); 
	
	public SemaphoreImplementtation() {}
	
	public void add() throws InterruptedException,IllegalMonitorStateException {
		write.acquire();
		int ele = this.random.nextInt(); 
			try {
				if(lock.tryLock()) {
					
						this.queque.add(ele);
						//Thread.currentThread().sleep(100); 
						System.out.println("Queue Length : "+this.queque.size());
						System.out.println("Item "+ ele+" is added by : "+ Thread.currentThread().getName());
					
				}
			}finally {
				lock.unlock();
			}
			
		write.release();
		read.release();
	}
	
	public void get() throws InterruptedException, IllegalMonitorStateException {
		read.acquire();
		int ele = 0;  
			try {
				if(lock.tryLock()) {
					if(this.queque.peek()!= null) {
						ele = this.queque.remove();
						System.out.println("Item "+ ele+" is polled by : " + Thread.currentThread().getName());
					}
				}
					
			}finally {
				lock.unlock();
			}
		read.release();
		write.release();
	}
	
	public static class WriterThread extends Thread{
		
		SemaphoreImplementtation obj; 
		public WriterThread(SemaphoreImplementtation obj){
			this.obj = obj; 
		}
		
		@Override
		public void run() {
			while(true) {
				try {
					sleep(10);
					this.obj.add();
				} catch (InterruptedException | IllegalMonitorStateException e) {} 
			}
		}
	}
	
	public static class ReaderThread extends Thread{
		SemaphoreImplementtation obj; 
		public ReaderThread(SemaphoreImplementtation obj){
			this.obj = obj; 
		}
		
		@Override
		public void run() {
			while(true) {
				try {
					this.obj.get();
					sleep(15); 
				} catch (InterruptedException | IllegalMonitorStateException e) {
					//e.printStackTrace();
				}
			}
			
		}
	}
	
	
	
	public static void main(String[] a) {
		
		SemaphoreImplementtation obj = new SemaphoreImplementtation(); 
		
		WriterThread wt_1 = new WriterThread(obj); 
		WriterThread wt_2 = new WriterThread(obj);
		WriterThread wt_3 = new WriterThread(obj);
		ReaderThread rd_1 = new ReaderThread(obj);
		ReaderThread rd_2 = new ReaderThread(obj);
		ReaderThread rd_3 = new ReaderThread(obj);
		ReaderThread rd_4 = new ReaderThread(obj);
		ReaderThread rd_5 = new ReaderThread(obj);
		ReaderThread rd_6 = new ReaderThread(obj);
		ReaderThread rd_7 = new ReaderThread(obj);
		ReaderThread rd_8 = new ReaderThread(obj);
		
		wt_1.start();
		wt_2.start();
		wt_3.start();
		rd_1.start();
		rd_2.start();
		rd_3.start();
		rd_4.start();
		rd_5.start();
		rd_6.start();
		rd_7.start();
		rd_8.start();
	}

}
