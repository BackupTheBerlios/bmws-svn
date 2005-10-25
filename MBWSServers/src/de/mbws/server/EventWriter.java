package de.mbws.server;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import org.apache.log4j.Logger;

import de.mbws.common.EventQueue;
import de.mbws.common.Globals;
import de.mbws.common.NIOUtils;
import de.mbws.common.Player;
import de.mbws.common.QueueWorker;
import de.mbws.common.events.AbstractGameEvent;
import de.mbws.common.utils.StringUtils;
import de.mbws.server.account.AccountServer;

/**
 * EventWriter.java
 * 
 * @version 1.0
 */
public class EventWriter extends QueueWorker {
    private static Logger logger = Logger.getLogger(EventWriter.class);
    private AccountServer accountServer;

    /**
     * contructor.
     */
    public EventWriter(AccountServer accountServer, EventQueue queue, int numWorkers) {
        this.accountServer = accountServer;
        initWorker(numWorkers,queue);
    }

    /**
     * note we override the Wrap's run method here doing essentially the same
     * thing, but first we allocate a ByteBuffer for this thread to use
     */
    public void run() {
        ByteBuffer writeBuffer = ByteBuffer.allocate(Globals.MAX_EVENT_SIZE);

        AbstractGameEvent event;
        running = true;
        while (running) {
            try {
                if ((event = eventQueue.deQueue()) != null) {
                    processEvent(event, writeBuffer);
                }
            } catch (InterruptedException e) {
            }
        }
    }

    /**
     * our own version of processEvent that takes the additional parameter of
     * the writeBuffer
     */
    protected void processEvent(AbstractGameEvent event, ByteBuffer writeBuffer) {
        NIOUtils.prepBuffer(event, writeBuffer);

        Integer[] recipients = event.getRecipients();
        if (recipients == null) {
            logger.info("writeEvent: type=" + event.getClass().getName() + ", id=" + event.getPlayer().getSessionId()
            		+ ", eventType=" + event.getEventType()
            		+ "payload=" + StringUtils.bytesToString(writeBuffer.array()));
            Integer playerSessionId = event.getPlayer().getSessionId();
            
            write(event.getPlayer().getChannel(), writeBuffer);
        } else {
            for (int i = 0; i < recipients.length; i++) {
                if (recipients[i] != null) {
                    logger.info("writeEvent(B): type=" + event.getClass().getName() + ", id=" + recipients[i]);
                    Player player = accountServer.getPlayerBySessionId(recipients[i]);
                    write(player.getChannel(), writeBuffer);
                }
            }
        }

    }

    /**
     * write the event to the given playerId's channel
     */
    private void write(SocketChannel channel, ByteBuffer writeBuffer) {

        if (channel == null || !channel.isConnected()) {
            logger.error("writeEvent: client channel null or not connected");
            return;
        }

        NIOUtils.channelWrite(channel, writeBuffer);
    }
    // Unused
    protected void processEvent(AbstractGameEvent event) {      
    }

}