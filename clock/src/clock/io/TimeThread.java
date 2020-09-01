package clock.io;

import java.util.concurrent.Semaphore; 

public class TimeThread extends Thread{
	
	private ClockOutput clockOutput; 
	private MonitorThreadHandler monThread; 
	
	public TimeThread(ClockOutput clockOutput, MonitorThreadHandler monThread) {
		this.clockOutput = clockOutput; 
		this.monThread = monThread; 
	}
	
	public void run() {
		
		while(true) {
			clockOutput.
		}
	}

}
