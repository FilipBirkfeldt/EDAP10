package train.simulation;
import train.model.Monitor;
import train.model.Route;
import train.model.TrainObject;
import train.view.TrainView;


public class TrainSimulation {
	
	public static void main(String[] args) {
		TrainView view = new TrainView();
		Monitor m1 = new Monitor();
		Route route = view.loadRoute();
		TrainObject t1 = new TrainObject(route, 3, m1);
		Route r1 = view.loadRoute();
		TrainObject t2 = new TrainObject(r1, 3, m1); 
		Route r2 = view.loadRoute();
		TrainObject t3 = new TrainObject(r2, 3, m1); 
		Route r3 = view.loadRoute();
		TrainObject t4 = new TrainObject(r3, 3, m1); 
		
		Route r5 = view.loadRoute();
		TrainObject t5 = new TrainObject(r5, 3, m1); 
		
		Route r6 = view.loadRoute();
		TrainObject t6 = new TrainObject(r6, 3, m1); 
		
		Route r7 = view.loadRoute();
		TrainObject t7 = new TrainObject(r7, 3, m1); 
		
		Route r8 = view.loadRoute();
		TrainObject t8 = new TrainObject(r8, 3, m1); 
		
		Route r9 = view.loadRoute();
		TrainObject t9 = new TrainObject(r9, 3, m1); 
		
		Route r10 = view.loadRoute();
		TrainObject t10 = new TrainObject(r10, 7, m1); 
		
		t1.start(); 
		t2.start(); 
		t3.start();
		t4.start();
		t5.start(); 
		t6.start(); 
		t7.start();
		t8.start();
		t9.start(); 
		t10.start(); 
	}
}