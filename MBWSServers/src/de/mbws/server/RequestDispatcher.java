package de.mbws.server;

import java.io.IOException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

import org.apache.log4j.Logger;

import de.mbws.common.Attachment;
import de.mbws.common.Player;
import de.mbws.common.events.AbstractGameEvent;
import de.mbws.common.events.GameEventFactory;
import de.mbws.common.utils.StringUtils;
import de.mbws.server.account.AccountServer;

/**
 * RequestDispatcher.java handles reading from all clients using a Selector and
 * hands off events to the appropriatae EventControllers
 * 
 * @version 1.0
 */
public class RequestDispatcher extends Thread {
	/** log4j logger */
	private static Logger logger = Logger.getLogger("RequestDispatcher");

	/** pending connections */
	private LinkedList<SocketChannel> newClients;

	/** the selector, multiplexes access to client channels */
	private Selector selector;

	/** reference to the AccountServer */
	private AccountServer accountServer;

	/**
	 * Constructor.
	 */
	public RequestDispatcher(AccountServer accountServer) {
		this.accountServer = accountServer;
		newClients = new LinkedList<SocketChannel>();
	}

	/**
	 * adds to the list of pending clients
	 */
	public void addNewClient(SocketChannel clientChannel) {
		synchronized (newClients) {
			newClients.addLast(clientChannel);
		}
		// force selector to return
		// so our new client can get in the loop right away
		selector.wakeup();
	}

	/**
	 * loop forever, first doing our select() then check for new connections
	 */
	public void run() {
		try {
			selector = Selector.open();

			while (true) {
				select();
				checkNewConnections();

				// sleep just a bit
				try {
					Thread.sleep(30);
				}
				catch (InterruptedException e) {
				}
			}
		}
		catch (IOException e) {
			logger.fatal("exception while opening Selector", e);
		}
	}

	/**
	 * check for new connections and register them with the selector
	 */
	private void checkNewConnections() {
		synchronized (newClients) {
			while (newClients.size() > 0) {
				try {
					SocketChannel clientChannel = newClients.removeFirst();
					clientChannel.configureBlocking(false);
					clientChannel.register(selector, SelectionKey.OP_READ, new Attachment());
				}
				catch (ClosedChannelException cce) {
					logger.error("channel closed", cce);
				}
				catch (IOException ioe) {
					logger.error("ioexception on clientChannel", ioe);
				}
			}
		}
	}

	/**
	 * do our select, read from the channels and hand off events to
	 * GameControllers
	 */
	private void select() {
		try {
			// this is a blocking select call but will
			// be interrupted when new clients come in
			selector.select();
			Set readyKeys = selector.selectedKeys();

			Iterator i = readyKeys.iterator();
			while (i.hasNext()) {
				SelectionKey key = (SelectionKey) i.next();
				i.remove();
				SocketChannel channel = (SocketChannel) key.channel();
				Attachment attachment = (Attachment) key.attachment();

				try {
					// read from the channel
					long nbytes = channel.read(attachment.readBuff);
					// check for end-of-stream condition
					if (nbytes == -1) {
						logger.info("disconnect: " + channel.socket().getRemoteSocketAddress()
								+ ", end-of-stream");
						channel.close();
					}

					// check for a complete event
					try {
						if (attachment.readBuff.position() >= Attachment.HEADER_SIZE) {
							attachment.readBuff.flip();

							// read as many events as are available in the
							// buffer
							while (attachment.eventReady()) {
								logger.debug("Dispatching event");
								// TODO player should be identified through session id
								Player player = new Player();
								player.setChannel(channel);
								player.setSessionId(attachment.sessionId);
								AbstractGameEvent event = GameEventFactory.getGameEvent(attachment.getPayload(),
										player);
								logger.debug("Got Event: "+event);
								if (event != null) {
									accountServer.handleIncomingEvent(event);
								}
								else {
									logger.warn("Could not identify an event for payload '"
											+ StringUtils.bytesToString(attachment.payload) + "'");
								}
								attachment.reset();
							}
							// prepare for more channel reading
							attachment.readBuff.compact();
						}
					}
					catch (IllegalArgumentException e) {
						logger.error("illegal argument exception", e);
					}
				}
				catch (IOException ioe) {
					logger.warn("IOException during read(), closing channel:"
							+ channel.socket().getInetAddress());
					channel.close();
				}
			}
		}
		catch (IOException ioe2) {
			logger.warn("IOException during select(): " + ioe2.getMessage());
		}
		catch (Exception e) {
			logger.error("exception during select()", e);
		}
	}
}