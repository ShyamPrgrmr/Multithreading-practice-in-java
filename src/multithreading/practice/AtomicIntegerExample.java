package multithreading.practice;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerExample {

	private int count;
	private AtomicInteger aCount; 
	
	public void integerIncrement() {
		count++;
	}
	
	public void integerDecrement() {
		count--;
	}
	
	public void aIntegerIncrement() {
		this.aCount.getAndIncrement();
	}
	
	public void aIntegerDecrement() {
		this.aCount.getAndDecrement(); 
	}
	
	public AtomicIntegerExample() {
		this.count = 0; 
		this.aCount = new AtomicInteger(0); 
	}
	
	public void printCounters() {
		System.out.println("Normal Counter : " + this.count);
		System.out.println("Atomic Counter : "+ this.aCount.get());
	}
	
	public static class IntegerIncrement extends Thread{
		private AtomicIntegerExample ale; 
		public IntegerIncrement(AtomicIntegerExample ale) {
			this.ale=ale; 
		}
		@Override
		public void run() {
			for(int i=0;i<100000; i++) {
				ale.integerIncrement();
			}
		}
		
	}
	public static class IntegerDecrement extends Thread{
		private AtomicIntegerExample ale; 
		public IntegerDecrement(AtomicIntegerExample ale) {
			this.ale=ale; 
		}
		@Override
		public void run() {
			for(int i=0;i<100000; i++) {
				ale.integerDecrement();
			}
		}
	}
	public static class AIntegerIncrement extends Thread{
		private AtomicIntegerExample ale; 
		public AIntegerIncrement(AtomicIntegerExample ale) {
			this.ale=ale; 
		}
		@Override
		public void run() {
			for(int i=0;i<100000; i++) {
				ale.aIntegerIncrement();
			}
		}
	}
	public static class AIntegerDecrement extends Thread{
		private AtomicIntegerExample ale; 
		public AIntegerDecrement(AtomicIntegerExample ale) {
			this.ale=ale; 
		}
		@Override
		public void run() {
			for(int i=0;i<100000; i++) {
				ale.aIntegerDecrement();
			}
		}
	}
	
	
	
	public static void main(String args[]) throws InterruptedException {
		AtomicIntegerExample ale = new AtomicIntegerExample(); 
		
		IntegerIncrement first = new IntegerIncrement(ale); 
		IntegerDecrement second = new IntegerDecrement(ale); 
		
		AIntegerIncrement first_a = new AIntegerIncrement(ale); 
		AIntegerDecrement second_a = new AIntegerDecrement(ale);
		
		first.start();
		second.start();
		
		first_a.start();
		second_a.start();
		
		ale.printCounters();
	}
	

}
