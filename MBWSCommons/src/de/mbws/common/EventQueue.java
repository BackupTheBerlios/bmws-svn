package de.mbws.common;

import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;

import de.mbws.common.events.AbstractGameEvent;


/**
 * Description: EventQueue based on ConcurrentLinkedQueue with additional debug infos
 * @author Azarai
 *
 */
public class EventQueue {
    private Logger logger;

    private LinkedBlockingQueue<AbstractGameEvent> events;

    /**
     * Constructor. Initializes the logger and event list
     */
    public EventQueue(String name) {
        logger = Logger.getLogger("EventQueue: " + name);
        events = new LinkedBlockingQueue<AbstractGameEvent>();
    }

    /**
     * add an event to the queue
     */
    public void enQueue(AbstractGameEvent event) {
        // log.debug("enQueue " + event.hashCode());
        events.add(event);
        if (logger.isDebugEnabled()) {
            StringBuilder sb = new StringBuilder();
            sb.append("enQueued ");
            sb.append(event);
            logger.debug(sb.toString());
        }
    }

    /**
     * blocks until an event is available and then removes and returns the first
     * available event
     */
    public AbstractGameEvent deQueue() throws InterruptedException {
        AbstractGameEvent event = events.take();
        if (logger.isDebugEnabled()) {
            StringBuilder sb = new StringBuilder();
            sb.append("deQueued ");
            sb.append(event);
            logger.debug(sb.toString());
        }
        return event;
    }

    /**
     * get the current # of events in the queue
     */
    public int size() {
        return events.size();
    }
}