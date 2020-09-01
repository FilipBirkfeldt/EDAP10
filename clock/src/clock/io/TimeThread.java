package clock.io;

import java.util.concurrent.Semaphore; 

public class TimeThread extends Thread{
	
	private MonitorThreadHandler disp_time;
	
	public TimeThread(MonitorThreadHandler disp_time) {
		this.disp_time = disp_time;
	}
	
	@Override
	public void run() {
		try { 
			while(true) {
			disp_time.setTime(disp_time.getTime()+1);
			
				Thread.sleep(1000);
			}
		}catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

}
