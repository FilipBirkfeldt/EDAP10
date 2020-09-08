package clock.io;

import java.sql.Time;
import java.time.Instant;
import java.util.concurrent.Semaphore;

public class TimeThread extends Thread {

	private MonitorThreadHandler disp_time;
	private ClockOutput out;
	private Semaphore sem1;

	public TimeThread(MonitorThreadHandler disp_time, ClockOutput out, Semaphore sem1) {
		this.disp_time = disp_time;
		this.out = out;
		this.sem1 = sem1;
	}

	@Override
	public void run() {
		try {
			long filipsTid1 = System.currentTimeMillis();
			while (true) {
				//int time = disp_time.getTime();
				//int h = (time / 10000) % 24;
				//int m = ((time / 100) - (h * 100)) % 60;
				//int s = (((time) - (h * 10000) - (m * 100)) % 60)+1;

				//if (s > 59) {
				//	s = 0;
				//	m++;
				//}
				//if (m > 59) {
				//	m = 0;
				//	h++;
				//}

				//if (h > 23) {
				//	h = 0;
				//}
				//disp_time.setTime(h, m, s);
				
				disp_time.tick();
				disp_time.checkAlarm();
				long Filipstid2 = System.currentTimeMillis();
				Thread.sleep(1000- (Filipstid2-filipsTid1)%1000);
				// från quiz  (1000-(now-t0)%1000)
				sem1.release();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
