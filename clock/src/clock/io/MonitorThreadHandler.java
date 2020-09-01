package clock.io;

import java.util.concurrent.Semaphore; 


public class MonitorThreadHandler {
	
	private ClockOutput clockOutput; 
	private Semaphore mutex = new Semaphore(1); //Mutex - lås race condition kapplöpning
	private int time; 
	private int alarmTime = 0; 
	private boolean alarm = false; 


	
	public MonitorThreadHandler(ClockOutput clockOutput, int time) {
		this.clockOutput = clockOutput; 
		this.alarmTime = time; 
	}
	
	public void setTime(int hms) {
		this.time = time; 
		
	}
	
	public int getTime() {
		return time; 
	}
	
	
	
	
	
	
}
