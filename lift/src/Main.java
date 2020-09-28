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

		for (int i = 0; i < 40; i++) {
			
			Passenger pass = view.createPassenger();
			PersonThread pers = new PersonThread(pass, mon);
			pers.start();
			
			Thread.sleep(rand.nextInt(2000));
		}

	}
}
// R1. för personerna och för hissen. Gemensamma attribut 
// R2. Med if så kollas de bara första veckan - då kollas inte tråden igen 
// R3. En är uppfylld men inte båda. passEntry & passExit skulle kunna vara en metod. Fråga om hjälp
// R4. För moveLift() tar för lång tid
// R5. modify - notifyAll()
// R6. wait() lägger sig utanför monitorn. Synchronize o monitor hör ihop. 

