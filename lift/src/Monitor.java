import java.util.ArrayList;

import lift.LiftView;

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
		this.view = view;
		this.waitEntry = new ArrayList<Integer>();
		this.waitExit = new ArrayList<Integer>();
		this.floor = 0;
	}

	public synchronized void addWaitingPers(int waitFloor) {
		waitEntry.add(waitFloor);
		notifyAll();

	}
	
	public synchronized void removeWaitingPers(int waitFloor) throws InterruptedException {
		while(!moving){
			wait();
		}
		waitEntry.remove(waitEntry.indexOf(waitFloor));
		notifyAll();

	}

	public synchronized void addLiftPers(int toFloor) {
		waitExit.add(toFloor);
		notifyAll();
	}

	public synchronized boolean isEmpty() {
		if (waitEntry.isEmpty() && waitExit.isEmpty()) {
			return true;
		}
		return false;
	}

	public synchronized int getLiftFloor() {
		return floor;
	}

	public synchronized void setLiftFloor(int i) {
		this.floor = i;
		notifyAll();
	}

	public synchronized void checkFloor(int persOnFloor) throws InterruptedException {
		while (this.floor != persOnFloor) {
			wait();
		}
	}

	public synchronized void waitForPers(int i) throws InterruptedException {
		while (waitEntry.contains(i)) {
			wait();
		}

	}

	public synchronized void setDirecton(int i) {
		if (i == 0) {
			return;
		}
		direction = i;
	}

	public synchronized int getDirection() {
		return direction;
	}
	
	public synchronized void setMoving(boolean moving) {
		this.moving = moving;
	}

}
