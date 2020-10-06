package wash.control;

import actor.ActorThread;
import wash.io.WashingIO;

public class TemperatureController extends ActorThread<WashingMessage> {

	private WashingIO io;

	public TemperatureController(WashingIO io) {
		this.io = io;
		// TODO
	}

	@Override
	public void run() {
		try {
			while (true) {
				WashingMessage m = receiveWithTimeout(60000 / Settings.SPEEDUP);
				
				WashingMessage m2 = new WashingMessage(this, WashingMessage.ACKNOWLEDGMENT, 0);
				m.getSender().send(m2);
				
				double reqTemp = m.getValue();
				Long dTemp = (long) 0; 

				while (m.getCommand() == WashingMessage.TEMP_SET) {
					double currTemp = io.getTemperature();
					Long t1 = System.currentTimeMillis(); 
					
					if((currTemp-dTemp*0.0478)<reqTemp-2) {
						io.heat(true);
					}
					
					if((currTemp+dTemp*0.0478)>reqTemp) {
						io.heat(false);
					}
					
					double newCurrTemp = io.getTemperature();
					Long t2 = System.currentTimeMillis(); 
					dTemp = (long) ((newCurrTemp-currTemp)/(t2-t1));  

				}
			}

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
