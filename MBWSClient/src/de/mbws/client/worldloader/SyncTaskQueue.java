package de.mbws.client.worldloader;

import java.util.Iterator;

public class SyncTaskQueue extends AbstractTaskQueue {

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
			Runnable task = queue.getFirst().task;
			task.run();
			queue.removeFirst();
		}
	}

}
