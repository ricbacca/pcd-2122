package pcd.lab04.monitors.barrier;

import java.util.LinkedList;
import java.util.List;

/*
 * Barrier - to be implemented
 */
public class FakeBarrier implements Barrier {

	private final int nParticipants;
	private int counter;
	
	public FakeBarrier(int nParticipants) {
		this.nParticipants = nParticipants;
		this.counter = 0;
	}
	
	@Override
	public synchronized void hitAndWaitAll() throws InterruptedException {
		counter++;
		if(counter == nParticipants) {
			counter = 0;
			notifyAll();
		}
		wait();
	}

	
}
