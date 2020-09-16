package train.simulation;

import java.util.LinkedList;
import java.util.Queue;

import train.model.Monitor;
import train.model.Route;
import train.model.Segment;
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
		t2.start(); 
		t1.start(); 

	}

}
