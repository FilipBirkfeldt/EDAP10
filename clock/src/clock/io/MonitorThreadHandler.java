package clock.io;

import java.util.concurrent.Semaphore;

public class MonitorThreadHandler extends Thread {

	private Semaphore mutex = new Semaphore(1); // Mutex - lås race condition
												// kapplöpning
	private int time;
	private int alarmTime;
	private boolean alarm;
	private ClockOutput out;

	public MonitorThreadHandler(ClockOutput out) {
		this.out = out;
		time = 0;
		alarm = false;

	}

	public void setTime(int h, int m, int s) {
		mutex.tryAcquire();
		time = h * 10000 + m * 100 + s;
		out.displayTime(h, m, s);
	}
	
	public void setAlarmTime(int h, int m, int s){
		
	}

	public int getTime() {
		return time;
	}

}
