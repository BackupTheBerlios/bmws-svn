package de.dynterrain;

import java.util.LinkedList;
import java.util.logging.Logger;


public abstract class AbstractTaskQueue {

	protected static class QueueEntry {
		Object identifier;
		Runnable task;

		public QueueEntry(Object taskIdentifier, Runnable task) {
			this.task = task;
			this.identifier = taskIdentifier;
		}
	}

	protected Logger logger;
	protected LinkedList<QueueEntry> queue = new LinkedList<QueueEntry>();

	public AbstractTaskQueue() {
		logger = Logger.getLogger(this.getClass().getName());
	}
	public abstract void enqueue(Object taskIdentifier, Runnable task);

}
