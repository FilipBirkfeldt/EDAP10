package clock.io;

import java.util.concurrent.Semaphore; 


public class Monitor {
	
	private ClockOutput clockOutput; 
	private Semaphore mutex = new Semaphore(1); //race condition kapplöpning
	private int time; 
	private int alarmTime = 0; 
	private boolean alarm = false; 

	
	public Monitor(ClockOutput clockOutput) {
		this.clockOutput = clockOutput; 
	}
	
	public int retrieveTime() {
		return time; 
	}
	
	
	
	
}
