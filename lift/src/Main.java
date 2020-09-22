import java.util.Random;

import lift.LiftView;
import lift.Passenger;


public class Main {
	public static void main(String[] args) throws InterruptedException {
		LiftView view = new LiftView();
		Random rand = new Random();
		
		Monitor mon = new Monitor();
		LiftThread lift = new LiftThread(view, mon);
		lift.start();

		for (int i = 0; i < 20; i++) {
			
			Passenger pass = view.createPassenger();
			PersonThread pers = new PersonThread(pass, mon);
			pers.start();
			Thread.sleep(rand.nextInt(5000));
		}

	}
}
