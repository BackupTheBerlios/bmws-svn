package de.terrainer.terrainloader;

import java.util.LinkedList;

public class AsyncTaskQueue {

	private volatile boolean stopping;

	private class QueueEntry {
		String identifier;
		Runnable task;

		public QueueEntry(String taskIdentifier, Runnable task) {
			this.task = task;
			this.identifier = taskIdentifier;
		}
	}

	LinkedList<QueueEntry> queue = new LinkedList<QueueEntry>();

	public void startQueueProcessor() {
		Thread tr = new Thread(new Runnable() {
			public void run() {
				while (!stopping) {
					Runnable task = dequeue();
					if (task != null) {
						System.out.println("processing task");
						task.run();
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
								e.printStackTrace();
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

	public synchronized void enqueue(String taskIdentifier, Runnable task) {
		queue.addLast(new QueueEntry(taskIdentifier, task));
		notify();
	}

	private synchronized Runnable dequeue() {
		if (queue.size() == 0)
			return null;
		Runnable ret = queue.getFirst().task;
		queue.removeFirst();
		return ret;
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
