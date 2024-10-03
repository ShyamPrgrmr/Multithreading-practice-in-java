package multithreading.practice;

public class DataRaceCondition {

	private volatile long x,y;  
	
	public DataRaceCondition() {
		x=y=0; 
	}
	
	public synchronized void operation_1() {
		long local = x;
		y = 2; 
		System.out.println(Thread.currentThread().getName() + ": x= "+this.x +", y="+this.y+", local="+local);
	}
	
	//remove this synchronized keyword you will see the difference in output.
	
	public synchronized void operation_2() {
		long local = y;
		x = 2;
		System.out.println(Thread.currentThread().getName() + ": x= "+this.x +", y="+this.y+", local="+local);
	}
	
	
	public static class Check1 extends Thread{
		DataRaceCondition drc; 
		
		public Check1(DataRaceCondition drc){
			this.drc = drc;
		}
		
		@Override
		public void run() {
			drc.operation_1(); 
		}
	}
	
	public static class Check2 extends Thread{
		DataRaceCondition drc; 
		
		public Check2(DataRaceCondition drc){
			this.drc = drc;
		}
		
		@Override
		public void run() {
			drc.operation_2(); 
		}
	}
	
	public static void main(String a[]) throws InterruptedException {
		
		DataRaceCondition drc = new DataRaceCondition(); 
		
		Check1 thread_1 = new Check1(drc);
		Check2 thread_2 = new Check2(drc);
		
		thread_1.start();
		
		thread_2.start(); 
		
		thread_1.join();
		thread_2.join();
		
	}
}
