import java.util.concurrent.Semaphore;

import clock.io.ClockInput;
import clock.io.ClockOutput;
import clock.io.MonitorThreadHandler;
import clock.io.TimeThread;

public class AlarmThread extends Thread {
	private ClockInput in;
	private MonitorThreadHandler disp_time;
	private int h;
	private int m;
	private int s;
	private int alarmTime; 
	private ClockOutput out; 
	private Semaphore mutex = new Semaphore(1); 
	private boolean alarm;

	public AlarmThread(MonitorThreadHandler disp_time, ClockInput in, ClockOutput out) {
		this.disp_time = disp_time;
		this.in = in;
		this.out = out; 
	}

	@Override
	public void run() {
		try {
			while (true) {
				
				mutex.acquire();
				
				if (disp_time.getAlarmTime() == disp_time.getTime()) {
					alarm = true;
					disp_time.alarmOn(alarm);
				}
				if (disp_time.getAlarmTime()+20 == disp_time.getTime() && alarm == true) {
					disp_time.alarmOn(false);
				}

				//disp_time.setTime(h, m, s);
				mutex.release(); 
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
