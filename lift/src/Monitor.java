import java.util.ArrayList;

import lift.LiftView;
import lift.Passenger;

public class Monitor {
	private int floor; // the floor the lift is currently on
	private boolean moving; // true if the lift is moving, false if standing
							// still with doors open
	private int direction; // +1 if lift is going up, -1 if going down
	private ArrayList<Integer> waitEntry; // number of passengers waiting to
											// enter the lift
	// at the various floors
	private ArrayList<Integer> waitExit; // number of passengers (in lift)
											// waiting to leave
	// at the various floors
	private int load; // number of passengers currently in the lift
	private LiftView view;

	public Monitor() {
		this.waitEntry = new ArrayList<Integer>();
		this.waitExit = new ArrayList<Integer>();
		this.direction = 1;
	}

	public synchronized void runPassenger(int fromFloor, int toFloor, Passenger pass) throws InterruptedException {
		waitEntry.add(fromFloor);
		while ((fromFloor != floor || waitExit.size() > 3) || moving) {
			wait();
		}

		pass.enterLift();
		notifyAll();
		waitEntry.remove(waitEntry.indexOf(fromFloor));
		waitExit.add(toFloor);
		while (toFloor != floor) {
			wait();
		}
		pass.exitLift();
		notifyAll();
		waitExit.remove(waitExit.indexOf(toFloor));

	}

	public synchronized int runElevator(LiftView view, int to, int from) throws InterruptedException {
		moving = false; 
		floor = from;
		if (floor == 6) {
			direction = -1;
		} else if (floor == 0) {
			direction = 1;
		}

		view.openDoors(floor);
		notifyAll();
		while ((waitEntry.contains(floor) || waitExit.contains(floor)) && waitExit.size()<4) {
			wait();
		}

		view.closeDoors();
		to = to + direction;
		moving = true; 
		return to;

	}
}
