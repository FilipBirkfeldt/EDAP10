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
	private Semaphore sem1;
	private boolean alarm;

	public AlarmThread(MonitorThreadHandler disp_time, ClockInput in, ClockOutput out, Semaphore sem1) {
		this.disp_time = disp_time;
		this.in = in;
		this.out = out; 
		this.sem1 = sem1;
	}

	@Override
	public void run() {
		try {
			while (true) {
				sem1.acquire();
				System.out.println("test");
				if (disp_time.getAlarmTime() == disp_time.getTime()) {
					alarm = true;
					disp_time.alarmOn(alarm);
				}
				if (disp_time.getAlarmTime()+20 == disp_time.getTime() && alarm == true) {
					disp_time.alarmOn(false);
				}

				//disp_time.setTime(h, m, s);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
