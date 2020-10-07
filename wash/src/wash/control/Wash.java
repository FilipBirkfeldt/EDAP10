package wash.control;

import wash.io.WashingIO;
import wash.simulation.WashingSimulator;

public class Wash {

	// FRÅGOR
	// HEAT, FILL, DRAIN, LOCK, SPIN-OFF, SPIN-Left, SPIN-RIGHT, SPIN-Fast
	// a) which thread would send it?
	// Lock-main, FILL&DRAIN - WaterController , HEAT-TempController, ,
	// SPINs-SpinController
	// b) Which thread should receive it?
	// Ett tvättprogramm, t.ex WP3
	// c) Does the order need an acknowledgement?
	// Nej, trådarna löser det själva.
	// Låsa innan vi fyller med vatten

	public static void main(String[] args) throws InterruptedException {

		WashingProgram3 wp3 = null;
		WashingProgram1 wp1 = null;
		WashingProgram2 wp2 = null;

		WashingSimulator sim = new WashingSimulator(Settings.SPEEDUP);

		WashingIO io = sim.startSimulation();

		TemperatureController temp = new TemperatureController(io);
		WaterController water = new WaterController(io);
		SpinController spin = new SpinController(io);

		temp.start();
		water.start();
		spin.start();

		wp1 = new WashingProgram1(io, temp, water, spin);
		wp2 = new WashingProgram2(io, temp, water, spin);
		wp3 = new WashingProgram3(io, temp, water, spin);

		while (true) {
			int n = io.awaitButton();
			System.out.println("user selected program " + n);

			if (n == 1) {
				wp1.start();
			}

			if (n == 2) {
				wp2.start();
			}

			if (n == 3) {
				wp3.start();
			}

			if (n == 0) {
				if(wp1.isAlive()) {
					wp1.interrupt();
					
				}
				if(wp2.isAlive()) {
					wp2.interrupt();
				}
				if(wp3.isAlive()) {
					wp3.interrupt();
				}
			}

		}

		// REFLEKTIONE MED BOM
		// R1:

//		if(wp1.isAlive()) {
//		temp.send(new WashingMessage(wp1, WashingMessage.TEMP_IDLE));
//		water.send(new WashingMessage(wp1, WashingMessage.WATER_IDLE));
//		spin.send(new WashingMessage(wp1, WashingMessage.SPIN_OFF));  
//	}
//	if(wp2.isAlive()) {
//		temp.send(new WashingMessage(wp2, WashingMessage.TEMP_IDLE));
//		water.send(new WashingMessage(wp2, WashingMessage.WATER_IDLE));
//		spin.send(new WashingMessage(wp2, WashingMessage.SPIN_OFF));  
//	}
//	if(wp3.isAlive()) {
//		temp.send(new WashingMessage(wp3, WashingMessage.TEMP_IDLE));
//		water.send(new WashingMessage(wp3, WashingMessage.WATER_IDLE));
//		spin.send(new WashingMessage(wp3, WashingMessage.SPIN_OFF));  
//	}

	}
};
