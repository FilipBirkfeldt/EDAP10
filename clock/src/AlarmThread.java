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

	public AlarmThread(MonitorThreadHandler disp_time, ClockInput in, ClockOutput out) {
		this.disp_time = disp_time;
		this.in = in;
		this.out = out; 
	}

	@Override
	public void run() {
		try {
			while (true) {
				
				if (disp_time.getAlarmTime() == disp_time.getTime()) {
					disp_time.alarmOn(true);
					
				}

				//disp_time.setTime(h, m, s);
				Thread.sleep(1000);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
