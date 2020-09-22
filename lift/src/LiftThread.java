import lift.LiftView;
import lift.Passenger;

public class LiftThread extends Thread {
	private LiftView view;
	private Monitor mon;
	private int to;
	private int from;

	public LiftThread(LiftView view, Monitor mon) {
		this.view = view;
		this.mon = mon;
		this.to = 0;
		this.from = 0;

	}
	

	@Override
	public void run() {
		while (true) {

			try {
				to = mon.runElevator(view, to, from);
				view.moveLift(from, to);
				from = to; 
				
				

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
