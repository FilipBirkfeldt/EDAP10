import lift.LiftView;
import lift.Passenger;

public class LiftThread extends Thread {
	private LiftView view;
	private Monitor mon;

	public LiftThread(LiftView view, Monitor mon) {
		this.view = view;
		this.mon = mon;
	}

	@Override
	public void run() {
		try {

		if (mon.isEmpty()) {
			// wait();
		}
		int dir = 0;
		while (true) {
			int i = mon.getLiftFloor();
			
			if(i==0){
				dir = 1;
			}
			else if(i==6){
				dir = -1;
			}
			
			mon.setDirecton(dir);
			
			view.openDoors(i);
			mon.setMoving(false);
			
			mon.waitForPers(i);

			view.closeDoors();
		
			view.moveLift(i, i + mon.getDirection());
			mon.setMoving(true);
			
			i = i + mon.getDirection();
			
			mon.setLiftFloor(i);

		}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
