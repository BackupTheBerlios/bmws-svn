package de.mbws.server;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import org.apache.log4j.Logger;

import de.mbws.common.EventQueue;
import de.mbws.common.Globals;
import de.mbws.common.NIOUtils;
import de.mbws.common.QueueWorker;
import de.mbws.common.data.AbstractPlayerData;
import de.mbws.common.events.AbstractGameEvent;
import de.mbws.common.utils.StringUtils;

/**
 * EventWriter.java
 * 
 * @version 1.0
 */
public class EventWriter extends QueueWorker {
    private static Logger logger = Logger.getLogger(EventWriter.class);
    private AbstractTcpServer server;

    /**
     * contructor.
     */
    public EventWriter(AbstractTcpServer server, EventQueue queue, int numWorkers) {
        this.server = server;
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
        

        Integer[] recipients = event.getRecipients();
        if (recipients == null) {
            NIOUtils.prepBuffer(event, writeBuffer, event.getPlayer().getSessionId());
            if (logger.isDebugEnabled()) {
                StringBuffer sb = new StringBuffer();
                sb.append("writeEvent: type=");
                sb.append(event.getClass().getName());
                sb.append(", id=");
                sb.append(event.getPlayer().getSessionId());
                sb.append(", eventType=");
                sb.append(event.getEventType());
                sb.append("payload=");
                sb.append(StringUtils.bytesToString(writeBuffer.array()));
                logger.debug(sb.toString());
            }
            write(event.getPlayer().getChannel(), writeBuffer);
        } else {
            for (int i = 0; i < recipients.length; i++) {
                if (recipients[i] != null) {
                    logger.info("writeEvent(B): type=" + event.getClass().getName() + ", id=" + recipients[i]+ " at time: "+System.currentTimeMillis());
                    AbstractPlayerData player = server.getPlayerBySessionId(recipients[i]);
                    NIOUtils.prepBuffer(event, writeBuffer, player.getSessionId());
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
        //logger.debug("writing event at " + System.currentTimeMillis());
        NIOUtils.channelWrite(channel, writeBuffer);
        //logger.debug("writing event at " + System.currentTimeMillis() + "..done");
    }
    // Unused
    protected void processEvent(AbstractGameEvent event) {      
    }
}