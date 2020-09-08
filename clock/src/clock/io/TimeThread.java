package clock.io;

import java.sql.Time;
import java.time.Instant;
import java.util.concurrent.Semaphore;

public class TimeThread extends Thread {

	private MonitorThreadHandler disp_time;

	public TimeThread(MonitorThreadHandler disp_time) {
		this.disp_time = disp_time;

	}

	@Override
	public void run() {
		try {
			long filipsTid1 = System.currentTimeMillis();
			while (true) {
				disp_time.tick();
				disp_time.checkAlarm();
				long Filipstid2 = System.currentTimeMillis();
				Thread.sleep(1000- (Filipstid2-filipsTid1)%1000);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
