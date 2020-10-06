package wash.control;

import actor.ActorThread;
import wash.io.WashingIO;

public class WaterController extends ActorThread<WashingMessage> {

	private WashingIO io;

	public WaterController(WashingIO io) {
		this.io = io;
	}

	@Override
	public void run() {
		try {
			while (true) {
				WashingMessage m = receiveWithTimeout(60000 / Settings.SPEEDUP);

				WashingMessage m2 = new WashingMessage(this, WashingMessage.ACKNOWLEDGMENT, 0);
				m.getSender().send(m2);

				double reqWater = m.getValue();
				System.out.println(reqWater); 

				while (m.getCommand() == WashingMessage.WATER_FILL) {
					double currWater = io.getWaterLevel();

					if ((currWater+0.1*Settings.SPEEDUP) < reqWater) {
						io.fill(true);
						System.out.println("fill true");
					} else {
						io.fill(false);
						System.out.println("fill false");
					}
					m.getSender().send(m2);

				}
				while (m.getCommand() == WashingMessage.WATER_DRAIN) {
					double currWater = io.getWaterLevel();
					if (currWater > 0) {
						io.drain(true);
					} else {
						io.drain(false);
					}
				}
			}

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
