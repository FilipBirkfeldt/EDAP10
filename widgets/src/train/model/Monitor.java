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

	public synchronized void run(LinkedList<Route> trainList, LinkedList<Segment> segment, Route trainRoute) throws InterruptedException {
		
		Segment first;
		first = trainRoute.next();
		try {
			checkFreeWay(first);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		trainList.add(0, trainRoute);
		segment.add(0, first);
		addSegmentToList(segment);
		first.enter();
		trainList.removeLast();
		removeSegmentInList(segment.getLast());
		segment.getLast().exit();
		segment.removeLast();
		notifyAll(); 
	}

	public synchronized void addSegmentToList(LinkedList<Segment> lista) {
		for (Segment x : lista) {
			busySet.add(x);
		}
	}

	public synchronized void removeSegmentInList(Segment segment) {
		busySet.remove(segment);
		notifyAll();
	}

	public synchronized void checkFreeWay(Segment segment) throws InterruptedException {
		// Kan både kolla & blockera
		while (busySet.contains(segment)) {
			wait();
		}
		return;
	}
}
