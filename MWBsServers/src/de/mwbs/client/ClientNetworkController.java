package de.mwbs.client;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import org.apache.log4j.Logger;

import de.mwbs.common.EventQueue;
import de.mwbs.common.Globals;
import de.mwbs.common.NIOUtils;
import de.mwbs.common.events.AbstractGameEvent;
import de.mwbs.server.exceptions.InitializationException;

public class ClientNetworkController extends Thread {

    private Logger logger = Logger.getLogger(ClientNetworkController.class);
    
    SocketChannel channel = null;

    protected boolean running = true;

    /** queue for incoming events */
    protected EventQueue inQueue;

    /** queue for outoging events */
    protected EventQueue outQueue;
    
    private Client client = null;
    
    public ClientNetworkController(Client client) {
        this.client = client;
        inQueue = new EventQueue("GameClient-in");
        outQueue = new EventQueue("GameClient-out");
    }

    public void run() {
        try {

            while (running) {
                processIncomingEvents();
                writeOutgoingEvents();
                Thread.sleep(50);
            }
        } catch (Exception e) {
            logger.error("Error in main loop", e);
        }
    }

    public void connect() throws InitializationException  {
        try {            
            channel = SocketChannel.open(new InetSocketAddress("localhost", 5000));
            channel.configureBlocking(false);
            // we don't like Nagle's algorithm
            channel.socket().setTcpNoDelay(true);
            NIOEventReader n = new NIOEventReader(channel, inQueue);
            n.start();
        } catch (Exception e) {
            logger.error("Error during server connection", e);
            throw new InitializationException("Error during server connection. see log file for more information");
        }
        
    }

    public void writeOutgoingEvents() {
    	AbstractGameEvent outEvent;
        while (outQueue.size() > 0) {
            try {
                outEvent = outQueue.deQueue();
                writeEvent(outEvent);
            } catch (InterruptedException ie) {
                logger.info("InterruptedException in readin out-queue", ie);
            }
        }
    }

    protected void writeEvent(AbstractGameEvent ge) {
        ge.setPlayer(Client.getPlayer());
        ByteBuffer writeBuffer = ByteBuffer.allocate(Globals.MAX_EVENT_SIZE);
        NIOUtils.prepBuffer(ge, writeBuffer);
        NIOUtils.channelWrite(channel, writeBuffer);
    }

    protected void processIncomingEvents() {
    	AbstractGameEvent inEvent;
        while (inQueue.size() > 0) {
            try {
                inEvent = inQueue.deQueue();
                client.processIncomingEvents(inEvent);
            } catch (InterruptedException ie) {
                logger.info("InterruptedException in readin in-queue", ie);
            }
        }
    }
    
    public void handleOutgoingEvent(AbstractGameEvent event) {
        outQueue.enQueue(event);
    }
}