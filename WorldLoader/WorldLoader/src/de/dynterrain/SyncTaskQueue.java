package de.dynterrain;

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

	public boolean process(int millis) {
		boolean ret = false;
		long breaktime = System.currentTimeMillis() + millis;
		while (queue.size() > 0 && breaktime > System.currentTimeMillis()) {
			long tasktime = System.currentTimeMillis();
			QueueEntry entry = queue.getFirst();
			logger.info("Processing sync task "+entry.identifier);
			Runnable task = entry.task;
			task.run();
			queue.removeFirst();
			synchronized (entry.identifier) {
				entry.identifier.notifyAll();
			}
			logger.info("Finished sync task " + entry.identifier + " in "
					+ (System.currentTimeMillis() - tasktime) + " ms");
			ret = true;
		}
		if (breaktime < System.currentTimeMillis()) {
			logger.warning("Overrun processing time limit by "
					+ (System.currentTimeMillis() - breaktime) + " ms");
		}
		return ret;
	}

	public void executeSynchronously(Runnable task) {
		String taskIdentifier = String.valueOf(uid++);
		enqueue(taskIdentifier, task);
		synchronized (taskIdentifier) {
			try {
				taskIdentifier.wait();
			}
			catch (InterruptedException e) {
				logger.severe(e.getMessage());
			}
		}
	}

}
