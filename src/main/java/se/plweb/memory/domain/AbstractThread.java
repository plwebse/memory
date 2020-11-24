package se.plweb.memory.domain;

/**
 * @author Peter Lindblom
 * 
 */

public abstract class AbstractThread implements Runnable {
	protected final Thread thread = new Thread(this);
	protected volatile boolean running = false;
	protected volatile boolean firstTime = true;

	protected synchronized boolean isRunning() {
		return running;
	}

	protected synchronized void setRunning(boolean running) {
		this.running = running;
	}

	protected synchronized boolean isFirstTime() {
		return firstTime;
	}

	protected synchronized void setFirstTime(boolean firstTime) {
		this.firstTime = firstTime;
	}

	public synchronized void stop() {
		if (isRunning()) {
			setRunning(false);
		}
	}
}
