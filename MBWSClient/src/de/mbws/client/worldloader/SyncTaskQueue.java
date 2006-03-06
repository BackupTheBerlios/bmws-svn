package de.mbws.client.worldloader;

import java.util.Iterator;

public class SyncTaskQueue extends AbstractTaskQueue {
	private static SyncTaskQueue instance;
	private long uid;
	
	public static SyncTaskQueue getInstance() {
		if (instance == null) {
			instance = new SyncTaskQueue();
		}
		return instance;
	}

	private SyncTaskQueue() {
	}

	public void enqueue(Object taskIdentifier, Runnable task) {
		Iterator<QueueEntry> it = queue.iterator();
		while (it.hasNext()) {
			if (it.next().identifier.equals(taskIdentifier))
				// task already in queue
				return;
		}
		logger.info("enqueue task: " + taskIdentifier);
		queue.addLast(new QueueEntry(taskIdentifier, task));
	}

	public void process(int millis) {
		long starttime = System.currentTimeMillis();
		while (queue.size() > 0 && starttime + millis > System.currentTimeMillis()) {
			QueueEntry entry = queue.getFirst();
			Runnable task = entry.task;
			task.run();
			queue.removeFirst();
			synchronized (entry.identifier) {
				entry.identifier.notifyAll();
			}
		}
	}

	public void executeSynchronously(Runnable task) {
		String taskIdentifier = String.valueOf(uid++);
		enqueue(taskIdentifier, task);
		synchronized (taskIdentifier) {
			try {
				taskIdentifier.wait();
			}
			catch (InterruptedException e) {
				logger.error(e.getMessage());
			}
		}
	}

}
