
import lift.LiftView;
import lift.Passenger;

public class OnePersonRidesLift {

    public static void main(String[] args) {

        LiftView  view = new LiftView();
        
        Monitor mon = new Monitor(view);
        
        for (int i = 0; i>5; i++){
        	Passenger pass = view.createPassenger();
        	PersonThread pers = new PersonThread(pass);
        	pers.start();
        }
        
  
    }
}
