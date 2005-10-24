package de.mwbs.client;

import java.io.IOException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.Logger;

import de.mwbs.common.Attachment;
import de.mwbs.common.EventQueue;
import de.mwbs.common.events.AbstractGameEvent;
import de.mwbs.common.events.GameEventFactory;

public class NIOEventReader extends Thread {
    /** log4j logger */
    private Logger log = Logger.getLogger(NIOEventReader.class);

    /** connection to server */
    private SocketChannel channel;

    /** selector to manage server connection */
    private Selector selector;

    /** still running? */
    private boolean running;
    
    /** queue for incoming events */
    private EventQueue queue = null;
    
    /**
     * constructor.
     */
    public NIOEventReader(SocketChannel channel, EventQueue queue) {
        super("NIOEventReader");
        this.channel = channel;
        this.queue = queue;
    }

    /**
     * this is nearly identical to the SelectAndRead.select() method
     */
    public void run() {
        try {
            selector = Selector.open();
            channel.register(selector, SelectionKey.OP_READ, new Attachment());
        } catch (ClosedChannelException cce) {
            log.error("closedchannelexception while registering channel with selector", cce);
            return;
        } catch (IOException ioe) {
            log.error("ioexception while registering channel with selector", ioe);
            return;
        }

        running = true;
        while (running) {
            try {
                selector.select();
                Set readyKeys = selector.selectedKeys();

                Iterator i = readyKeys.iterator();
                while (i.hasNext()) {
                    SelectionKey key = (SelectionKey) i.next();
                    i.remove();

                    SocketChannel channel = (SocketChannel) key.channel();
                    Attachment attachment = (Attachment) key.attachment();

                    try {
                        long nbytes = channel.read(attachment.readBuff);
                        if (nbytes == -1) {
                            channel.close();

                        }

                        try {
                            if (attachment.readBuff.position() >= Attachment.HEADER_SIZE) {
                                attachment.readBuff.flip();

                                while (attachment.eventReady()) {
                                	AbstractGameEvent event = getEvent(attachment);
                                    if (event != null) {
                                        queue.enQueue(event);
                                        log.debug("incoming event = " + event.getEventType());
                                    }
                                    
                                    attachment.reset();
                                }
                                attachment.readBuff.compact();
                            }
                        } catch (IllegalArgumentException e) {
                            log.error("illegalargument while parsing incoming event", e);
                        }
                    } catch (IOException ioe) {
                        log.warn("IOException during read(), closing channel:" + channel.socket().getInetAddress());
                        channel.close();
                    }
                }
            } catch (IOException ioe2) {
                log.warn("error during select(): " + ioe2.getMessage());
            } catch (Exception e) {
                log.error("exception during select()", e);
            }
        }
    }

    private AbstractGameEvent getEvent(Attachment attachment) {
        if (Client.getPlayer() != null) {
            Client.getPlayer().setSessionId(attachment.sessionId);
        }
        return GameEventFactory.getGameEvent(attachment.getPayload(), Client.getPlayer());
    }
}