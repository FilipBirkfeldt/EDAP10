package actor;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class ActorThread<M> extends Thread {

	BlockingQueue<M> bq = new ArrayBlockingQueue<M>(5);

	/** Called by another thread, to send a message to this thread. */
	public void send(M message) {
		// TODO: implement this method (one or a few lines)
		try {
			bq.put(message);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/** Returns the first message in the queue, or blocks if none available. */
	protected M receive() throws InterruptedException {
		// TODO: implement this method (one or a few lines)
		return bq.take();
	}

	/** Returns the first message in the queue, or blocks up to 'timeout'
        milliseconds if none available. Returns null if no message is obtained
        within 'timeout' milliseconds. */
    protected M receiveWithTimeout(long timeout) throws InterruptedException {
        // TODO: implement this method (one or a few lines)
        return bq.poll(timeout, TimeUnit.MILLISECONDS); 
    }
}