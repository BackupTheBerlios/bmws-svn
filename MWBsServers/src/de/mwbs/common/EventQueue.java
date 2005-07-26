package de.mwbs.common;

import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.log4j.Logger;

/**
 * EventQueue.java
 * 
 * @version 1.0
 */
public class EventQueue {
    private Logger logger;

    private ConcurrentLinkedQueue<GameEvent> events;

    private int count = 0;

    /**
     * Constructor. Initializes the logger and event list
     */
    public EventQueue(String name) {
        logger = Logger.getLogger("EventQueue: " + name);
        events = new ConcurrentLinkedQueue<GameEvent>();
    }

    /**
     * add an event to the queue
     */
    public synchronized void enQueue(GameEvent event) {
        // log.debug("enQueue " + event.hashCode());
        events.add(event);
        notifyAll();
    }

    /**
     * blocks until an event is available and then removes and returns the first
     * available event
     */
    public synchronized GameEvent deQueue() throws InterruptedException {
        while (events.size() == 0) {
            count++;
            // log.debug("waiting, count: " + count);
            wait();
            count--;
        }

        GameEvent e = (GameEvent) events.poll();
        // log.debug("deQueue " + e.hashCode());
        return e;
    }

    /**
     * get the current # of events in the queue
     */
    public synchronized int size() {
        return events.size();
    }

}