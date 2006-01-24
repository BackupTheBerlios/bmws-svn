package de.terrainer.terrainloader;

import java.util.Iterator;
import java.util.LinkedList;

public class AsyncTaskQueue {

	private volatile boolean stopping;

	private class QueueEntry {
		Object identifier;
		Runnable task;

		public QueueEntry(Object taskIdentifier, Runnable task) {
			this.task = task;
			this.identifier = taskIdentifier;
		}
	}

	LinkedList<QueueEntry> queue = new LinkedList<QueueEntry>();

	public void startQueueProcessor() {
		Thread tr = new Thread(new Runnable() {
			public void run() {
				while (!stopping) {
					QueueEntry entry = dequeue();
					if (entry != null) {
						System.out.println("processing task");
						entry.task.run();
						synchronized (entry) {
							entry.notifyAll();
						}
					}
					else {
						// nothing to do, go to sleep
						synchronized (AsyncTaskQueue.this) {
							try {
								System.out.println("going to sleep");
								AsyncTaskQueue.this.wait();
								System.out.println("woke up");
							}
							catch (InterruptedException e) {
							}
						}
					}
				}
				System.out.println("task queue finished");
			}
		});
		tr.start();
	}

	public synchronized void shutdownQueueProcessor() {
		stopping = true;
		notify();
	}

	public synchronized void enqueue(Object taskIdentifier, Runnable task) {
		queue.addLast(new QueueEntry(taskIdentifier, task));
		notify();
	}

	private synchronized QueueEntry dequeue() {
		if (queue.size() == 0)
			return null;
		QueueEntry ret = queue.getFirst();
		queue.removeFirst();
		return ret;
	}

	/** 
	 * Waits for the first entry in the queue matching the identifier.
	 * @param taskIdentifier
	 */
	public void waitForTask(Object taskIdentifier) {
		Iterator<QueueEntry> it = queue.iterator();
		while (it.hasNext()) {
			QueueEntry entry = it.next();
			if (entry.identifier.equals(taskIdentifier)) {
				synchronized (entry) {
					try {
						entry.wait();
					}
					catch (InterruptedException e) {
					}
				}
				return;
			}
		}
		throw new RuntimeException("Unknown task identifier: "+taskIdentifier);
	}

	public static void main(String[] args) {
		AsyncTaskQueue queue = new AsyncTaskQueue();
		queue.startQueueProcessor();
		for (int j = 0; j < 2; j++) {
			for (int i = 0; i < 10; i++) {
				System.out.println("adding task");
				queue.enqueue("test", new Runnable() {
					public void run() {
						try {
							System.out.println("task started");
							Thread.sleep(1000);
							System.out.println("task finished");
						}
						catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
			}
			try {
				Thread.sleep(15000);
			}
			catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		queue.shutdownQueueProcessor();
	}
}
