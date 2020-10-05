package wash.control;

import actor.ActorThread;
import wash.io.WashingIO;

public class SpinController extends ActorThread<WashingMessage> {

	// TODO: add attributes
	private WashingIO io;

	public SpinController(WashingIO io) {
		this.io = io;
	}

	@Override
	public void run() {
		try {

			// ... TODO ...

			while (true) {
				// wait for up to a (simulated) minute for a WashingMessage
				WashingMessage m = receiveWithTimeout(60000 / Settings.SPEEDUP);
				System.out.println("m"); 
				if (m.getCommand() == WashingMessage.SPIN_SLOW) {
					System.out.println("spinCont if "); 
					int i = 0; 
					while (i<5) {
						System.out.println("spinCont"); 
						
						io.setSpinMode(io.SPIN_LEFT);
						Thread.sleep(60000);
						io.setSpinMode(io.SPIN_RIGHT);
						Thread.sleep(60000); 
						i++; 
					}
				}
				// if m is null, it means a minute passed and no message was received
				if (m != null) {
					System.out.println("got " + m);
				}

				// ... TODO ...
			}
		} catch (InterruptedException unexpected) {
			// we don't expect this thread to be interrupted,
			// so throw an error if it happens anyway
			throw new Error(unexpected);
		}
	}
}
