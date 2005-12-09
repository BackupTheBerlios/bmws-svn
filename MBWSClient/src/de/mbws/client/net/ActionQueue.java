package de.mbws.client.net;

import java.util.concurrent.ConcurrentLinkedQueue;

import de.mbws.client.eventactions.AbstractEventAction;

/**
 * EventQueue.java
 * 
 * @version 1.0
 */
public class ActionQueue {
	private ConcurrentLinkedQueue<AbstractEventAction> actions;

	/**
	 * Constructor. Initializes the logger and event list
	 */
	public ActionQueue() {
		actions = new ConcurrentLinkedQueue<AbstractEventAction>();
	}

	/**
	 * add an event to the queue
	 */
	public synchronized void enQueue(AbstractEventAction event) {
		actions.add(event);
		notifyAll();
	}

	/**
	 * nonblocking. Returns null if no event is found.
	 */
	public synchronized AbstractEventAction deQueue() {
		if (actions.size() == 0) {
			return null;
		}

		AbstractEventAction e = actions.poll();
		return e;
	}

	/**
	 * get the current # of events in the queue
	 */
	public synchronized int size() {
		return actions.size();
	}

}