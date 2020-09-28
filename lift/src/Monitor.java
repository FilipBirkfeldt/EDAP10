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

	private int walkin;

	private LiftView view;

	public Monitor() {
		this.waitEntry = new ArrayList<Integer>();
		this.waitExit = new ArrayList<Integer>();
		this.direction = 1;
	}

	public synchronized void passExit(int toFloor) throws InterruptedException {
		while (toFloor != floor || moving) {
			wait();
		}
		walkin--; 
	}

	public synchronized void passEnter(int fromFloor, int toFloor) throws InterruptedException {
		waitEntry.add(fromFloor);
		while ((fromFloor != floor || waitExit.size() + walkin > 3) || moving) {
			wait();
			System.out.println("rad 39 " +waitExit.size()+" " + walkin);
		}
		walkin++;
		notifyAll(); 
	}

	public synchronized void enterElevator(int fromFloor, int toFloor) throws InterruptedException {
		// kolla hur många platser som finns tillgänliga - om fullt stäng ute
		walkin--; 
		waitExit.add(toFloor);
		waitEntry.remove(waitEntry.indexOf(fromFloor));
		notifyAll();
	}

	public synchronized void exitElevator(int toFloor) throws InterruptedException {
		waitExit.remove(waitExit.indexOf(toFloor));
		walkin++;
		notifyAll();
	}

	public synchronized int runElevator(LiftView view, int to, int from) throws InterruptedException {
		walkin = 0;
		if (!waitEntry.isEmpty() || !waitExit.isEmpty()) {
			moving = false;
			floor = from;
			if (floor == 6) {
				direction = -1;
			} else if (floor == 0) {
				direction = 1;
			}
			if (waitEntry.contains(floor) || waitExit.contains(floor)) {
				view.openDoors(floor);
				notifyAll();
				// !(a && b) == (!a || !b) !(a || b) == (!a && !b)
				// Detta måste kollas //
				while ((waitEntry.contains(floor) && waitExit.size() < 4) || waitExit.contains(floor)) {
					wait();
				}

				view.closeDoors();
			}
			to = to + direction;
			moving = true;
		}
		return to;
	}

}
