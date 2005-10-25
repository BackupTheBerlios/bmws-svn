package de.mbws.common;

import org.apache.log4j.Logger;

import de.mbws.common.events.AbstractGameEvent;

/**
 * QueueWorker QueueWorker is a thread pool with an incoming BlockingQueue of
 * GameEvents
 * 
 * @author <a href="mailto:bret@hypefiend.com">bret barker</a>
 * @version 1.0
 */
public abstract class QueueWorker implements Runnable {
    /** log4j logger */
    protected Logger logger;

    /** milliseconds to sleep between processing runs */
    protected static final long WORKER_SLEEP_MILLIS = 10;

    /** incoming event queue */
    protected EventQueue eventQueue;

    /** are we running? * */
    protected boolean running = false;

    /** our pool of worker threads */
    private Thread workers[];

    /** short Class name of the implementing class */
    private String shortname;

    /**
     * @param numWorkers
     *            number of worker threads to spawn
     */
    public final void initWorker(int numWorkers, EventQueue queue) {
        // setup the log4j Logger
        shortname = this.getClass().getName().substring(this.getClass().getName().lastIndexOf(".") + 1);
        logger = Logger.getLogger(shortname);
        logger.info("initWrap - " + shortname);

        eventQueue = queue;

        // spawn worker threads
        workers = new Thread[numWorkers];
        for (int i = 0; i < numWorkers; i++) {
            workers[i] = new Thread(this, shortname + "-" + (i + 1));
            workers[i].setDaemon(true);
            workers[i].start();
        }
    }

    /**
     * shutdown the worker threads
     */
    public void shutdown() {
        running = false;
        if (workers != null) {
            for (int i = 0; i < workers.length; i++) {
                workers[i].interrupt();
            }
        }
    }

    /**
     * retrieve events from the queue and process.
     */
    public void run() {
        AbstractGameEvent event;
        running = true;
        while (running) {
            try {
                if ((event = eventQueue.deQueue()) != null) {
                    processEvent(event);
                }
            } catch (InterruptedException e) {
            }
        }
    }

    /**
     * subclasses must implement to do their processing
     */
    protected abstract void processEvent(AbstractGameEvent event);

}
