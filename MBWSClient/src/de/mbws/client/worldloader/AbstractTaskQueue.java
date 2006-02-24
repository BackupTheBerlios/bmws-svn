package de.mbws.client.worldloader;

import java.util.LinkedList;

import org.apache.log4j.Logger;


public abstract class AbstractTaskQueue {

	protected static class QueueEntry {
		Object identifier;
		Runnable task;

		public QueueEntry(Object taskIdentifier, Runnable task) {
			this.task = task;
			this.identifier = taskIdentifier;
		}
	}

	protected static Logger logger;
	protected LinkedList<QueueEntry> queue = new LinkedList<QueueEntry>();

	public AbstractTaskQueue() {
		logger = Logger.getLogger(this.getClass());
	}
	public abstract void enqueue(Object taskIdentifier, Runnable task);

}
