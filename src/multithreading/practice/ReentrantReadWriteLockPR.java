package multithreading.practice;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;


public class ReentrantReadWriteLockPR {
	
	
	ReentrantReadWriteLock rwlock = new ReentrantReadWriteLock(); 
	
	ReadLock readLock = rwlock.readLock(); 
	WriteLock writeLock = rwlock.writeLock(); 
	int  count; 
	
	public ReentrantReadWriteLockPR(){
		this.count = 0; 
	}
	
	
	public void increase() throws IllegalMonitorStateException{
		try {
			if(writeLock.tryLock()) {
				this.count++; 
			}
		}finally {
			writeLock.unlock();
		}
	}
	
	public void decrease() throws IllegalMonitorStateException {
		try {
			if(writeLock.tryLock()) {
				this.count--; 
			}
		}finally {
			writeLock.unlock();
		}
	}
	
	public void getCount() throws IllegalMonitorStateException {
		try {
			readLock.lock();
			if(count!=0)
				System.out.printf("\nReader thread %s reads the count as : %d",Thread.currentThread().getName(),this.count); 
		}
		finally{
			readLock.unlock();
		}
	}
	
	
	public static class IncreseThread extends Thread{
		ReentrantReadWriteLockPR obj; 
		
		public IncreseThread(ReentrantReadWriteLockPR obj){
			this.obj = obj; 
		}
		
		@Override
		public void run() {
			while(true) {
				try {
					obj.increase();
					sleep(100); 
				}catch(IllegalMonitorStateException | InterruptedException im) {
					
				}
			}
		}
	}
	
	
	public static class DecreseThread extends Thread{
		ReentrantReadWriteLockPR obj; 
		
		public DecreseThread(ReentrantReadWriteLockPR obj){
			this.obj = obj; 
		}
		
		@Override
		public void run() {
			int i=0;
			while(true) {
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
		ReentrantReadWriteLockPR obj; 
		
		public Reader(ReentrantReadWriteLockPR obj){
			this.obj = obj; 
		}
		
		@Override
		public void run() { 
			while(true) {
				try {
					sleep(10);
					this.obj.getCount();
				}catch(IllegalMonitorStateException | InterruptedException im) {
					
				}
			}
		}
	}
	
	
	
	public static void main(String[] args) {
		
		ReentrantReadWriteLockPR obj = new ReentrantReadWriteLockPR(); 
		
		ArrayList<IncreseThread> increaseThreads = new ArrayList<IncreseThread>();
		ArrayList<DecreseThread> decreaseThreads = new ArrayList<DecreseThread>();
		ArrayList<Reader> readers = new ArrayList<Reader>();
		
		for(int i=0;i<5l;i++) {
			increaseThreads.add(new IncreseThread(obj));
			decreaseThreads.add(new DecreseThread(obj));
			readers.add(new Reader(obj));
		}
		
		for(int i=0;i<5l;i++) {
			increaseThreads.get(i).start();
			decreaseThreads.get(i).start();
			readers.get(i).start();
		}
		
	}

}
