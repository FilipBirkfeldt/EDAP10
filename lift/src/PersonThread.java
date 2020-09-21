import lift.Passenger;

public class PersonThread extends Thread {
	private Passenger pass;
	private Monitor mon;

	public PersonThread(Passenger pass, Monitor mon) {
		this.pass = pass;
		this.mon = mon;
	}

	@Override
	public void run() {
		try {
			int fromFloor = pass.getStartFloor();
			int toFloor = pass.getDestinationFloor();

			pass.begin(); // walk in (from left)

			mon.addWaitingPers(fromFloor);

			mon.checkFloor(fromFloor);
			
			pass.enterLift(); // step inside
			
			mon.addLiftPers(toFloor);
			mon.removeWaitingPers(fromFloor);
			
			mon.checkFloor(toFloor);
			
			pass.exitLift(); // leave lift
			pass.end(); // walk out (to the right)
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
