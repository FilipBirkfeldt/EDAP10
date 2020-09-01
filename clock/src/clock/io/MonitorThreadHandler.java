package clock.io;

import java.util.concurrent.Semaphore; 


public class MonitorThreadHandler extends Thread {
	
	private Semaphore mutex = new Semaphore(1); //Mutex - lås race condition kapplöpning
	private int time; 
	private int alarmTime = 0; 
	private boolean alarm = false; 
	private ClockOutput out;


	
	public MonitorThreadHandler(ClockOutput out) {
		this.out=out;
		time = 0;
		
	}
	
	public void setTime(int h, int m, int s) {
		time = h*100000+m*1000+s;
	}
	
	public void setTime(int time) {
		this.time = time;
		out.displayTime(0, 0, time);
		
	}
	
	public int getTime(){
		return time;
	}
	
	
	
	
	
	
	
	
}
