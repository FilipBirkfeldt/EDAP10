package clock.io;

import java.util.concurrent.Semaphore;

public class MonitorThreadHandler extends Thread {

	private Semaphore mutex = new Semaphore(1); // Mutex - lås race condition // kapplöpning
	private int time;
	private int alarmTime;
	private boolean alarm;
	private ClockOutput out;
	private int alarm_time;

	public MonitorThreadHandler(ClockOutput out) {
		this.out = out;
		time = 0;
		alarm = false;

	}

	public void setTime(int h, int m, int s) {

		try {
			mutex.acquire();
			time = h * 10000 + m * 100 + s;
			out.displayTime(h, m, s);

			if (alarm == true) {
				out.alarm();
				// alarm_time = alarm_time - 1;
				//if (alarm_time == 0) {
				//	alarmOn(false);
				//}
			}

			return;
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			mutex.release();

		}
	}

	public void setAlarmTime(int h, int m, int s) {
		try {
			mutex.acquire();
			alarmTime = h * 10000 + m * 100 + s;
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			mutex.release(); 
		}
	}

	public int getAlarmTime() throws InterruptedException {
		try{
			mutex.acquire();
			return alarmTime;
		} finally{
			mutex.release();
		}
	}

	public int getTime() throws InterruptedException {
		try{
			mutex.acquire();
			return time;
		} finally{
			mutex.release();
		}
		
	}

	public void alarmOn(boolean alarm) {
		try {
			mutex.acquire();
			//alarm_time = 20;
			System.out.println(alarm + " denna");
			this.alarm = alarm;
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			mutex.release();
		}
	}

}
