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
				WashingMessage m = receiveWithTimeout(60000 / Settings.SPEEDUP);

				if (m != null) {

					System.out.println("got " + m);
					// wait for up to a (simulated) minute for a WashingMessage
					if (m.getCommand() == WashingMessage.SPIN_SLOW) {
						WashingMessage m2 = new WashingMessage(this, WashingMessage.ACKNOWLEDGMENT, 0);

						while (m.getCommand() == WashingMessage.SPIN_SLOW) {

							io.setSpinMode(io.SPIN_LEFT);
							Thread.sleep(1000);
							io.setSpinMode(io.SPIN_RIGHT);
							Thread.sleep(1000);
							m.getSender().send(m2);
						}
						m = receive();
					}
					// if m is null, it means a minute passed and no message was received

					if (m.getCommand() == WashingMessage.SPIN_FAST) {
						io.setSpinMode(io.SPIN_FAST);
						WashingMessage m2 = new WashingMessage(this, WashingMessage.ACKNOWLEDGMENT, 0);
						m.getSender().send(m2);
					}
					m = receive();

					if (m.getCommand() == WashingMessage.SPIN_OFF) {
						io.setSpinMode(io.SPIN_IDLE);
						WashingMessage m2 = new WashingMessage(this, WashingMessage.ACKNOWLEDGMENT, 0);
						m.getSender().send(m2);
					}
					m = receive();

				}

				// ... TODO ...
			}
		} catch (

		InterruptedException unexpected) {
			// we don't expect this thread to be interrupted,
			// so throw an error if it happens anyway
			throw new Error(unexpected);
		}
	}
}
