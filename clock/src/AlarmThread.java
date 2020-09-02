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

	public AlarmThread(MonitorThreadHandler disp_time, ClockInput in) {
		this.disp_time = disp_time;
		this.in = in;
	}

	@Override
	public void run() {
		try {
			while (true) {
				
				

				//disp_time.setTime(h, m, s);
				Thread.sleep(1000);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
