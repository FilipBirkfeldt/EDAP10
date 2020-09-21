package train.simulation;

import train.model.Monitor;
import train.model.Route;
import train.model.TrainObject;
import train.view.TrainView;

public class TrainSimulation {

	public static void main(String[] args) {
		TrainView view = new TrainView();
		Monitor m1 = new Monitor();
		

		for (int i = 0; i < 20; i++) {
			Route r = view.loadRoute();
			TrainObject t = new TrainObject(r, 3, m1);
			t.start();

		}

	}
}