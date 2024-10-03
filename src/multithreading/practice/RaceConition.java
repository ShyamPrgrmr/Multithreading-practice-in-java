package multithreading.practice;

import java.util.concurrent.atomic.AtomicInteger;

public class RaceConition {
	
	int sharedResource=0; 
	AtomicInteger sharedAtomic = new AtomicInteger(); 
	
	public RaceConition(){
		this.sharedResource = 0; 
	}
	
	public void increment() {
		sharedResource++; 
	}
	
	public void decrement() {
		sharedResource--; 
	}
	
	public  void SYNincrement() {
		
		synchronized(this) {
			sharedAtomic.incrementAndGet(); 
		}
		 
	}
	
	public void SYNdecrement() {
		synchronized(this) {
			sharedAtomic.decrementAndGet(); 
		}
	}
	
	
	public boolean checkAt() {
		if(this.sharedAtomic.toString().equals(new String("0"))) {
			return true; 
		}
		return false; 
	}
	
	public int getValAt() {
		return (int) Integer.parseInt(this.sharedAtomic.toString()); 
	}
	
	public boolean check() {
		if(this.sharedResource == 0) {
			return true; 
		}
		return false; 
	}
	
	public int getVal() {
		return this.sharedResource;  
	}
	
	public static class IncrementThread extends Thread{
		RaceConition val; 
		
		public IncrementThread(RaceConition raceObj){
			val = raceObj; 
		}
		
		@Override
		public void run() {
			int i = 1000; 
			while(i>0) {
				 val.increment();
				 i--; 
			 }
		}
	}

	public static class DecrementThread extends Thread{
		RaceConition val; 
		
		public DecrementThread(RaceConition raceObj){
			val = raceObj; 
		}
		
		@Override
		public void run() {
			int i = 1000; 
			while(i>0) {
				 val.decrement();
				 i--; 
			 }
		}
	}

	public static class SYNIncrementThread extends Thread{
		RaceConition val; 
		
		public SYNIncrementThread(RaceConition raceObj){
			val = raceObj; 
		}
		
		@Override
		public void run() {
			int i = 1000; 
			while(i>0) {
				 val.SYNincrement();
				 i--; 
			 }
		}
	}

	public static class SYNDecrementThread extends Thread{
		RaceConition val; 
		
		public SYNDecrementThread(RaceConition raceObj){
			val = raceObj; 
		}
		
		@Override
		public void run() {
			int i = 1000; 
			while(i>0) {
				 val.SYNdecrement();
				 i--; 
			 }
		}
	}
	
	public static void main(String args[]) throws InterruptedException {
		
		
		RaceConition raceObj = new RaceConition(); 
		
		IncrementThread it = new IncrementThread(raceObj);
		DecrementThread dt = new DecrementThread(raceObj);
		
		it.start();
		dt.start();
		
		System.out.println("Started threads without join");
		System.out.println("Is the shared resource value zero ? "+ raceObj.check()+" {"+raceObj.getVal()+"}");
		
		
		RaceConition raceObj_1 = new RaceConition(); 
		
		IncrementThread it_1 = new IncrementThread(raceObj_1);
		DecrementThread dt_1 = new DecrementThread(raceObj_1);
		
		it_1.start();
		it_1.join();
		dt_1.start();
		dt_1.join();
		
		System.out.println("\nStarted threads with join");
		System.out.println("Is the shared resource value zero ? "+ raceObj_1.check()+" {"+raceObj_1.getVal()+"}");
		
		
		RaceConition raceObj_2 = new RaceConition(); 
		
		SYNIncrementThread it_2 = new SYNIncrementThread(raceObj_2);
		SYNDecrementThread dt_2 = new SYNDecrementThread(raceObj_2);
		
		it_2.start();
		dt_2.start();
		
		it_2.join();
		dt_2.join();
		
		System.out.println("\nSynchronized Keyword");
		System.out.println("Is the shared resource value =  {"+raceObj_2.getValAt()+"}");
		
		
		
	}
	
}
