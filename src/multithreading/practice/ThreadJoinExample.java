package multithreading.practice;

import java.math.BigInteger;

public class ThreadJoinExample {
	
	public static class FactorialCalculation{
		BigInteger number;  
		BigInteger result=BigInteger.ONE;  
		
		FactorialCalculation(BigInteger number){
			this.number = number; 
		}
		
		public BigInteger getResult() {
			for(BigInteger i = BigInteger.ONE; i.compareTo(this.number)!=1 ; i = i.add(BigInteger.ONE)) {
				
				
				this.result = this.result.multiply(i); 
			}
			return this.result; 
		}
	}
	
	public static class FactorialCalculationThread extends Thread{
		
		BigInteger number;  
		BigInteger result=BigInteger.ONE;  
		
		FactorialCalculationThread(BigInteger number){
			this.number = number; 
		}
		
		@Override
		public void run() {
			
			for(BigInteger i = BigInteger.ONE; i.compareTo(this.number)!=1 ; i=i.add(BigInteger.ONE)) {
				this.result = this.result.multiply(i); 
			}
		}
		
		public BigInteger getResult() {
			return this.result; 
		}
	}

	public static void main(String[] args) throws InterruptedException {
		
		long start = System.currentTimeMillis(); 
		BigInteger result = new FactorialCalculation(new BigInteger("19001")).getResult().add(new FactorialCalculation(new BigInteger("16001")).getResult().add(new FactorialCalculation(new BigInteger("18001")).getResult()));
		long end = System.currentTimeMillis();
		
		long time_1 = end-start; 
	
		System.out.println("Factorial Addition is : "+ result); 
		System.out.println("Time took for without multithreading : "+(time_1)+" ms");
		
		
		FactorialCalculationThread fct_1 = new FactorialCalculationThread(new BigInteger("19001"));
		FactorialCalculationThread fct_2 = new FactorialCalculationThread(new BigInteger("16001"));
		FactorialCalculationThread fct_3 = new FactorialCalculationThread(new BigInteger("18001"));
		 
		start = System.currentTimeMillis();
		fct_1.start();
		fct_2.start();
		fct_3.start();
		fct_1.join();
		fct_2.join();
		fct_3.join(); 
		BigInteger result_1 = fct_1.getResult().add(fct_2.getResult().add(fct_3.getResult()));
		end = System.currentTimeMillis();
		
		long time_2 = end-start; 
		
		System.out.println("Multithreaded Factorial Addition is : "+ result_1); 
		System.out.println("Time took for with multithreading : "+(end-start)+" ms");
		
		System.out.println("Results are : "+ (result_1.compareTo(result)==0?"Equal":"Not Equal"));
		
		System.out.println("Performance was improved by : "+(time_1 - time_2)+" ms");
		System.out.println("Performance was improved by : ~"+((float)time_1/((float)time_2)*100)+"%");
		
		
	}

}
