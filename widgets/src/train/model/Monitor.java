package train.model;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import train.view.TrainView;

public class Monitor {

	private Set<Segment> busySet;

	public Monitor() {
		this.busySet = new HashSet<Segment>();
	}

	public void run(LinkedList<Route> trainList, LinkedList<Segment> segment, Route trainRoute)
			throws InterruptedException {
		
		Segment first = trainRoute.next();

		checkFreeWay(first);

		trainList.add(0, trainRoute);
		segment.add(0, first);

		for (Segment x : segment) {
			busySet.add(x);
		}

		first.enter();

		trainList.removeLast();
		segment.getLast().exit();

		removeSegmentInList(segment.removeLast());

	}

	public synchronized void addSegmentToList(LinkedList<Segment> lista) {
		for (Segment x : lista) {
			busySet.add(x);
		}
	}

	public synchronized void removeSegmentInList(Segment segment) {
		busySet.remove(segment);
		notify();
	}

	public synchronized void checkFreeWay(Segment segment) {
		// Kan både kolla & blockera

		while (busySet.contains(segment)) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		busySet.add(segment);

		return;
	}
}
