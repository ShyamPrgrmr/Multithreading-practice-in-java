package multithreading.practice;

import java.util.Random;

public class PoliceHackerExample {
	
	public static class Vault {
		int password; 
		Vault(int num){
			this.password = num; 
		}
		public int getPassword() throws InterruptedException {
			Thread.sleep(15);
			return password;
		}
		
	}
	
	public static abstract class Hacker extends Thread{
		
		Vault vault; 
		
		Hacker(Vault vault){
			this.vault = vault; 
		}
				
		protected void checkPassword(int guess) throws InterruptedException{
			if(guess == vault.getPassword()) {
				System.out.println("\n"+this.getName()+" guessed the password correctly : " + guess); 
				System.exit(0);
			}
		}
		
	}
	
	public static class ASCHacker extends Hacker{
		ASCHacker(Vault vault) {
			super(vault);
		}
		
		@Override
		public void run() {
			
			this.setName("Ascending Hacker");
			for(int i=0;i<999;i++) {
				try {
					this.checkPassword(i);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}	
	}
	
	public static class DSCHacker extends Hacker{
		DSCHacker(Vault vault) {
			super(vault);
		}
		@Override
		public void run() {
			this.setName("Descending Hacker");
			for(int i=999;i>=0;i--) {
				try {
					this.checkPassword(i);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	public static class Police extends Thread{
		int counter = 0; 
		@Override
		public void run() {
			
			for(int i=10;i>=0;i--) {
				try {
					Thread.sleep(1000);
					System.out.println("You have "+i+" sec.");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if(i==0) {
					System.out.println("Game Over");
					System.exit(0);
				}
			}
		}
	}
	
	public static void main(String args[]) {
		
		Random random = new Random(); 
		int pass = random.nextInt(0, 1000); 
		
		Vault v = new Vault(pass); 
		
		ASCHacker hacker_1 = new ASCHacker(v); 
		DSCHacker hacker_2 = new DSCHacker(v); 
		Police police = new Police(); 
		
		hacker_1.start();
		hacker_2.start();
		police.start();
		
	}
	
}
