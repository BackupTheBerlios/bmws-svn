package de.mwbs.common;

import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.log4j.Logger;

import de.mwbs.server.events.AbstractGameEvent;

/**
 * EventQueue.java
 * 
 * @version 1.0
 */
public class EventQueue {
    private Logger logger;

    private ConcurrentLinkedQueue<AbstractGameEvent> events;

    private int count = 0;

    /**
     * Constructor. Initializes the logger and event list
     */
    public EventQueue(String name) {
        logger = Logger.getLogger("EventQueue: " + name);
        events = new ConcurrentLinkedQueue<AbstractGameEvent>();
    }

    /**
     * add an event to the queue
     */
    public synchronized void enQueue(AbstractGameEvent event) {
        // log.debug("enQueue " + event.hashCode());
        events.add(event);
        notifyAll();
    }

    /**
     * blocks until an event is available and then removes and returns the first
     * available event
     */
    public synchronized AbstractGameEvent deQueue() throws InterruptedException {
        while (events.size() == 0) {
            count++;
            // log.debug("waiting, count: " + count);
            wait();
            count--;
        }

        AbstractGameEvent e = (AbstractGameEvent) events.poll();
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