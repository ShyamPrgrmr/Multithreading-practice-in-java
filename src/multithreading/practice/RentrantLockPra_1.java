package multithreading.practice;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class RentrantLockPra_1 {
	private int count; 
	private Lock countLock = new ReentrantLock(); 
	
	public RentrantLockPra_1() {
		this.count = 0; 	
	}
	
	public void increase() throws IllegalMonitorStateException{
		try {
			if(countLock.tryLock()) {
				this.count++; 
			}else {
				//System.out.println("Not able to get lock! " + Thread.currentThread().getName()); 
			}
		}finally {
			
			countLock.unlock();
		}
	}
	
	public void decrease() throws IllegalMonitorStateException {
		try {
			if(countLock.tryLock()) {
				this.count--; 
			}else {
				//System.out.println("Not able to get lock! " + Thread.currentThread().getName()); 
			}
		}finally {
			countLock.unlock();
		}
	}
	
	public int getCount() throws IllegalMonitorStateException {
		try {
			countLock.lock();
			return this.count; 
		}finally {
			countLock.unlock();
		}
	}
	
	
	public static class IncreseThread extends Thread{
		RentrantLockPra_1 obj; 
		
		public IncreseThread(RentrantLockPra_1 obj){
			this.obj = obj; 
		}
		
		@Override
		public void run() {
			int i=0;
			while(i<1000) {
				try {
					obj.increase();
					sleep(100); 
				}catch(IllegalMonitorStateException | InterruptedException im) {
					
				}
				i++;
			}
		}
	}
	
	
	public static class DecreseThread extends Thread{
		RentrantLockPra_1 obj; 
		
		public DecreseThread(RentrantLockPra_1 obj){
			this.obj = obj; 
		}
		
		@Override
		public void run() {
			int i=0;
			while(i<1000) {
				try {
					obj.decrease();
					sleep(100); 
				}catch(IllegalMonitorStateException | InterruptedException im) {
					
				}
				i++;
			}
		}
	}
	
	
	public static class Reader extends Thread{
		RentrantLockPra_1 obj; 
		
		public Reader(RentrantLockPra_1 obj){
			this.obj = obj; 
		}
		
		@Override
		public void run() {
			int i = 0; 
			while(i<1999) {
				try {
					System.out.println("Current Count is : "+obj.getCount()); 
					sleep(100); 
				}catch(IllegalMonitorStateException | InterruptedException im) {
					
				}
				i++;
			}
		}
	}
	
	
	public static void main(String[] args) throws InterruptedException {
		RentrantLockPra_1 obj = new RentrantLockPra_1(); 
		
		IncreseThread increase = new IncreseThread(obj); 
		DecreseThread decrease = new DecreseThread(obj); 
		Reader reader = new Reader(obj); 
		
		
		increase.start(); 
		decrease.start();
		reader.start(); 
		
	}

}
