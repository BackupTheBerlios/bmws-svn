package de.mbws.client.worldloader;

import java.util.Iterator;
import java.util.LinkedList;

public class SyncTaskQueue {

	private class QueueEntry {
		Object identifier;
		Runnable task;

		public QueueEntry(Object taskIdentifier, Runnable task) {
			this.task = task;
			this.identifier = taskIdentifier;
		}
	}

	private LinkedList<QueueEntry> queue = new LinkedList<QueueEntry>();

	public void enqueue(Object taskIdentifier, Runnable task) {
		Iterator<QueueEntry> it = queue.iterator();
		while (it.hasNext()) {
			if (it.next().identifier.equals(taskIdentifier))
				// task already in queue
				return;
		}
		System.err.println("enqueue task: " + taskIdentifier);
		queue.addLast(new QueueEntry(taskIdentifier, task));
	}

	public void process(int millis) {
		long starttime = System.currentTimeMillis();
		while (queue.size() > 0 && starttime + millis > System.currentTimeMillis()) {
			Runnable task = queue.getFirst().task;
			task.run();
			queue.removeFirst();
		}
	}

}
