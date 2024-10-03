package multithreading.practice;

public class InterruptExample {
	
	//sleep is in interruptible method. 
	public static class EX1 extends Thread{
		@Override
		public void run(){
			while(true) {
				try {
					sleep(10000000);
				} catch (InterruptedException e) {
					System.out.println("Interrupted "+ this.getName()); 
					return; 
				} 
			}
		}
	}
	
	//Custom method
	public static class EX2 extends Thread{
		@Override
		public void run(){
			while(true) {
				
				//isInturrupted will check if the thread is interrupted or not. 
				if(this.isInterrupted()) {
					System.out.println("Interrupted "+this.getName());
					return; 
				}
			}
		}
	}
	
	
	public static void main(String args[]) throws InterruptedException {
		EX1 ex1 = new EX1(); 
		EX2 ex2 = new EX2(); 
		
		ex1.start();
		ex2.start();
		
		ex1.interrupt();
		Thread.currentThread().sleep(2000);
		ex2.interrupt();
	}
	
}
