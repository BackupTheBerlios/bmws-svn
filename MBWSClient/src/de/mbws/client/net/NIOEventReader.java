package de.mbws.client.net;

import java.io.IOException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.Logger;

import de.mbws.client.data.ClientPlayerData;
import de.mbws.client.eventactions.AbstractEventAction;
import de.mbws.common.Attachment;
import de.mbws.common.EventQueue;
import de.mbws.common.events.AbstractGameEvent;
import de.mbws.common.events.GameEventFactory;

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
    private EventQueue evenQueue = null;
    private ActionQueue actionQueue = null;
    
    /**
     * constructor.
     */
    public NIOEventReader(SocketChannel channel, EventQueue queue, ActionQueue actions) {
        super("NIOEventReader");
        this.channel = channel;
        this.evenQueue = queue;
        actionQueue = actions;
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
                                	if (attachment.sessionId != 0) {
                                    	if (ClientPlayerData.getInstance().getSessionId()==null || ClientPlayerData.getInstance().getSessionId().intValue()!=attachment.sessionId) {
                                    		ClientPlayerData.getInstance().setSessionId(attachment.sessionId);
                                    	}
                                    }
                                	AbstractGameEvent event = getEvent(attachment);
                                    if (event != null) {
                                        evenQueue.enQueue(event);
                                        log.debug("incoming event = " + event.getEventType());
                                    }
                                    
                                    AbstractEventAction action = getEventAction(attachment);
                                    if (action != null) {
                                    	actionQueue.enQueue(action);
                                        log.debug("incoming actionevent = " + action.getEventType());
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
//TODO Kerim refactor player stuff
    private AbstractGameEvent getEvent(Attachment attachment) {
        if (attachment.sessionId != 0) {
            ClientPlayerData.getInstance().setSessionId(attachment.sessionId);
        }
        return GameEventFactory.getGameEvent(attachment.getPayload(), ClientPlayerData.getInstance());
    }
    
    private AbstractEventAction getEventAction(Attachment attachment) {
        return ClientGameEventActionFactory.getGameEventAction(attachment.getPayload(), ClientPlayerData.getInstance());
    }
}