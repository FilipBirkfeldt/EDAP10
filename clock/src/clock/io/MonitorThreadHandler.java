package clock.io;

import java.util.concurrent.Semaphore; 


public class MonitorThreadHandler extends Thread {
	
	private ClockOutput clockOutput; 
	private Semaphore mutex = new Semaphore(1); //Mutex - lås race condition kapplöpning
	private int time; 
	private int alarmTime = 0; 
	private boolean alarm = false; 


	
	public MonitorThreadHandler(ClockOutput clockOutput) {
		this.clockOutput = clockOutput; 
	}
	
	public void setTime(int h, int m, int s) {
		
	}
	
	
	
	
	
	
}
