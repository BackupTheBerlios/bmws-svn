package de.mbws.client.worldloader;

import java.util.Iterator;
import java.util.LinkedList;

import org.apache.log4j.BasicConfigurator;

public class AsyncTaskQueue extends AbstractTaskQueue {
	private static AsyncTaskQueue instance;
	protected volatile boolean stopping;
	LinkedList<QueueEntry> queue = new LinkedList<QueueEntry>();

	public static AsyncTaskQueue getInstance() {
		if (instance == null) {
			instance = new AsyncTaskQueue();
		}
		return instance;
	}

	private AsyncTaskQueue() {
		logger.info("Starting queue processor thread");
		Thread tr = new Thread(new Runnable() {
			public void run() {
				while (!stopping) {
					try {
						QueueEntry entry = peek();
						if (entry != null) {
							long time = System.currentTimeMillis();
							logger.debug("Processing task: " + entry.identifier);
							entry.task.run();
							dequeue();
							logger.debug("Finished task: " + entry.identifier + " in "
									+ (System.currentTimeMillis() - time) + " ms");
							synchronized (entry) {
								entry.notifyAll();
							}
						}
						else {
							// nothing to do, go to sleep
							synchronized (AsyncTaskQueue.this) {
								try {
									logger.info("Task processor is going to sleep");
									AsyncTaskQueue.this.wait();
									logger.info("Task processor woke up");
								}
								catch (InterruptedException e) {
								}
							}
						}
					}
					catch (Exception e) {
						logger.error(e);
					}
				}
				logger.info("Task queue finished");
			}
		});
		//tr.setPriority(Thread.MIN_PRIORITY);
		tr.start();
	}

	public synchronized void shutdownQueueProcessor() {
		stopping = true;
		notify();
	}

	public synchronized void enqueue(Object taskIdentifier, Runnable task) {
		Iterator<QueueEntry> it = queue.iterator();
		while (it.hasNext()) {
			if (it.next().identifier.equals(taskIdentifier))
				// task already in queue
				// logger.debug("Task "+taskIdentifier+" ignored (already in queue)");
				return;
		}
		logger.info("Enqueue task: " + taskIdentifier);
		queue.addLast(new QueueEntry(taskIdentifier, task));
		notify();
	}

	protected QueueEntry peek() {
		if (queue.size() == 0)
			return null;
		QueueEntry ret = queue.getFirst();
		return ret;
	}

	protected synchronized void dequeue() {
		queue.removeFirst();
	}

	/**
	 * Waits for the first entry in the queue matching the identifier.
	 * 
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
		throw new RuntimeException("Unknown task identifier: " + taskIdentifier);
	}

	public void waitForEmptyQueue() {
		while (queue.size() > 0) {
			QueueEntry entry = queue.getLast();
			synchronized (entry) {
				try {
					entry.wait(100);
					SyncTaskQueue.getInstance().process(1000);
				}
				catch (InterruptedException e) {
				}
			}
		}
	}

	public static void main(String[] args) {
		BasicConfigurator.configure();
		AsyncTaskQueue queue = new AsyncTaskQueue();
		for (int j = 0; j < 2; j++) {
			for (int i = 0; i < 10; i++) {
				// System.out.println("adding task");
				queue.enqueue("test" + i, new Runnable() {
					public void run() {
						try {
							// System.out.println("task started");
							Thread.sleep(1000);
							// System.out.println("task finished");
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
