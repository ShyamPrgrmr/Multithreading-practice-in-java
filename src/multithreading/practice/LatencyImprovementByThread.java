package multithreading.practice;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

//Create negative of image 
public class LatencyImprovementByThread {
	static final String SOURCE_FILE_PATH ="./src/resources/image.jpg";
	static final String DESTINATION_FILE_PATH_SINGLE_THREADED = "./src/outputs/negativeImageSingle.jpg";
	static final String DESTINATION_FILE_PATH_MULTI_THREADED = "./src/outputs/negativeImageMulti.jpg";
	
	public static class SingleThreaded {
		int width; 
		int height; 
		
		SingleThreaded(int height, int width){
			this.width = width; 
			this.height = height; 
		}
		
		public void negativeImg(BufferedImage  img) throws IOException {
			for (int y = 0; y < height; y++) { 
	            for (int x = 0; x < width; x++) { 
	                int p = img.getRGB(x, y); 
	                int a = (p >> 24) & 0xff; 
	                int r = (p >> 16) & 0xff; 
	                int g = (p >> 8) & 0xff; 
	                int b = p & 0xff; 
	                
	                r = 255 - r; 
	                g = 255 - g; 
	                b = 255 - b; 
	
	                p = (a << 24) | (r << 16) | (g << 8) | b; 
	                img.setRGB(x, y, p); 
	            } 
	        }
			
			
			File f = new File( 
					DESTINATION_FILE_PATH_SINGLE_THREADED); 
	        ImageIO.write(img, "jpg", f);
		}
	} 
	
	public static class WorkerThread extends Thread{
		
		int left; 
		int leftTop; 
		int right;
		int rightBottom; 
		BufferedImage  img; 
		
		WorkerThread(int left, int leftTop, int right, int rightBottom, BufferedImage  img){
			this.left = left ;
			this.leftTop = leftTop; 
			this.right = right; 
			this.rightBottom = rightBottom;
			this.img = img; 
		}
		
		@Override
		public void run() {
			
			for (int y = leftTop; y < leftTop+left; y++) { 
	            for (int x = rightBottom; x < rightBottom + right; x++) { 
	                int p = img.getRGB(x, y); 
	                int a = (p >> 24) & 0xff; 
	                int r = (p >> 16) & 0xff; 
	                int g = (p >> 8) & 0xff; 
	                int b = p & 0xff; 
	                
	                r = 255 - r; 
	                g = 255 - g; 
	                b = 255 - b; 
	
	                p = (a << 24) | (r << 16) | (g << 8) | b; 
	                img.setRGB(x, y, p); 
	            } 
	        }
			
		}
	}

	
	public static class MultiThreaded{
		int height, width, numOfThread ; 
		public MultiThreaded(int height, int width,int numOfThread){
			this.height = height; 
			this.width = width; 
			this.numOfThread = numOfThread; 
		}
		public void negativeImg(BufferedImage  img ) throws InterruptedException, IOException{
			
			width = width/numOfThread; 
			
			List<WorkerThread> threads = new ArrayList<WorkerThread>(); 
			 
			
			for (int i=0;i<numOfThread;i++) {
				
				int threadMultiplier = i; 
				int yOrigin =  0; 
				int xOrigin = width * threadMultiplier;
				
				WorkerThread thread = new  WorkerThread(width, xOrigin,height, yOrigin, img); 
				threads.add(thread); 
			}
			
			for(WorkerThread thread : threads) {
				thread.join();
			}
			
			File f = new File( 
					DESTINATION_FILE_PATH_MULTI_THREADED); 
	        ImageIO.write(img, "jpg", f);
			
		}
	}
	
	public static void main(String args[]) throws IOException, InterruptedException {
		File f = new File(SOURCE_FILE_PATH); 
		BufferedImage img = ImageIO.read(f); 
		
		final int THREAD_COUNT = 1; 
		
		long startTime = System.currentTimeMillis(); 
		new SingleThreaded(img.getHeight(), img.getWidth()).negativeImg(img); 
		long endTime = System.currentTimeMillis();
		
		System.out.println("Single thread took "+(endTime-startTime)+" ms."); 
		
		startTime = System.currentTimeMillis(); 
		new MultiThreaded(img.getHeight(), img.getWidth(), THREAD_COUNT).negativeImg(img); 
		endTime = System.currentTimeMillis();
		
		System.out.println("Multi thread took "+(endTime-startTime)+" ms."); 
		
		
		
	}
	
}
