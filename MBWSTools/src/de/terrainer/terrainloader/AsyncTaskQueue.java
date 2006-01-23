package de.terrainer.terrainloader;

public class AsyncTaskQueue {
	
	public synchronized void enqueue(String taskIdentifier, Runnable task) {
		task.run();
	}

}
