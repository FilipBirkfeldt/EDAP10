package clock.io;

import java.util.concurrent.Semaphore;

public class MonitorThreadHandler {

	private Semaphore mutex = new Semaphore(1); // Mutex - lås race condition //
												// // kapplöpning
	private Semaphore sem = new Semaphore(1);
	private Semaphore sem1;
	private int time;
	private int alarmTime;
	private int alarmTimeStop;
	private boolean alarm;
	private ClockOutput out;
	private int alarm_time;
	private boolean alarmOn = false;

	public MonitorThreadHandler(ClockOutput out, Semaphore sem1) {
		this.out = out;
		time = 0;
		alarm = false;
		this.sem1 = sem1;

	}

	public void setTime(int h, int m, int s) {
		try {
			mutex.acquire();
			time = h * 10000 + m * 100 + s;
			out.displayTime(h, m, s);

			if (alarmOn == true) {
				if (alarmTimeStop <= time) {
					alarmOn = false;
				}
				out.alarm();
			}
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
			alarmTimeStop = alarmTime + 20;
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			mutex.release();
		}
	}

	public void tick() {
		try {
			mutex.acquire();
			int h = (time / 10000) % 24;
			int m = ((time / 100) - (h * 100)) % 60;
			int s = (((time) - (h * 10000) - (m * 100)) % 60) + 1;

			if (s > 59) {
				s = 0;
				m++;
			}
			if (m > 59) {
				m = 0;
				h++;
			}
			if (h > 23) {
				h = 0;
			}
			//if (alarmOn){
			//	sem1.release();
			//}
			mutex.release();
			setTime(h, m, s);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void checkAlarm() {
		try {
			mutex.acquire();
			if (alarm==true && alarmTime == time) {
				alarmOn = true;
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			mutex.release();
		}
	}

	public void alarmOn(boolean alarm) {
		try {
			mutex.acquire();
			this.alarm = alarm;
			if (alarmOn){
				alarmOn = false;
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			mutex.release();
		}
	}

}
