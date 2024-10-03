package multithreading.practice;

public class SimpleThreadsAndOperation  {

	public SimpleThreadsAndOperation() {
		
	}
	
	public static void main(String args[]) {
		
		
		Thread thread = new Thread(() -> {
			Thread.currentThread().setName("MAX"); 
			
			System.out.print("\nHello..."+ Thread.currentThread().getName());
		});
		
		Thread thread_1 = new Thread(() -> {
			Thread.currentThread().setName("MIN"); 
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.print("\nHello..."+ Thread.currentThread().getName());
		});
		
		System.out.print("\nHello..."+ Thread.currentThread().getName());
		
		thread_1.start(); 
		thread.setPriority(Thread.MAX_PRIORITY);
		
		thread.start();
		thread.setPriority(Thread.MIN_PRIORITY);
		
		System.out.print("\nHello..."+ Thread.currentThread().getName());
		
		
		
	}

}
