package train.model;

import java.util.LinkedList;

import train.view.TrainView;

public class TrainObject extends Thread {

	private LinkedList<Route> trainList;
	private int size;
	private Route trainRoute;
	private Monitor m1;
	private LinkedList<Segment> segment;

	public TrainObject(Route trainRoute, int size, Monitor m1) {
		this.trainRoute = trainRoute;
		this.size = size;
		this.m1 = m1;
		this.trainList = new LinkedList<Route>();
		this.segment = new LinkedList<Segment>();
	}

	@Override
	public void run() {

		Segment first;

		for (int i = 0; i < size; i++) {
			first = trainRoute.next();
			segment.add(first);
			trainList.add(trainRoute);
			first.enter();
		}
		while (true) {
			try {
				m1.run(trainList, segment, trainRoute);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}
}
