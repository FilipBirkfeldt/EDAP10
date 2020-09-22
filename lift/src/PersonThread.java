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
		
		
			int fromFloor = pass.getStartFloor();
			int toFloor = pass.getDestinationFloor();
			

			pass.begin(); // walk in (from left)
			try {
				mon.runPassenger(fromFloor, toFloor, pass);
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			pass.end(); // walk out (to the right)
		}
}
