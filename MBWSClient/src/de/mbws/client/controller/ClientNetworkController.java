package de.mbws.client.controller;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import org.apache.log4j.Logger;

import com.jme.app.GameStateManager;

import de.mbws.client.MBWSClient;
import de.mbws.client.ValueMapper;
import de.mbws.client.data.ClientGlobals;
import de.mbws.client.data.ClientPlayerData;
import de.mbws.client.net.NIOEventReader;
import de.mbws.client.state.MainMenuState;
import de.mbws.common.EventQueue;
import de.mbws.common.Globals;
import de.mbws.common.NIOUtils;
import de.mbws.common.events.*;
import de.mbws.common.exceptions.InitializationException;

public class ClientNetworkController extends Thread {

	private Logger logger = Logger.getLogger(ClientNetworkController.class);

	SocketChannel channel = null;

	protected boolean running = true;

	/** queue for incoming events */
	protected EventQueue inQueue;

	/** queue for outoging events */
	protected EventQueue outQueue;

	private static ClientNetworkController instance;
	private NIOEventReader eventReader;

	private ClientNetworkController() {
		inQueue = new EventQueue("GameClient-in");
		outQueue = new EventQueue("GameClient-out");
	}

	public static ClientNetworkController getInstance() {
		if (instance == null) {
			instance = new ClientNetworkController();
		}
		return instance;
	}

	public void run() {
		try {

			while (running) {
				processIncomingEvents();
				writeOutgoingEvents();
				// Thread.yield();
				Thread.sleep(10);
				// TODO: use two threads and wait !
				// synchronized (inQueue) {
				// inQueue.wait();
				// }
			}
		} catch (Exception e) {
			logger.error("Error in main loop", e);
			running = false;
		}
	}

	public void connect(String ip, int port) throws InitializationException {
		try {
			if (inQueue == null) {
				inQueue = new EventQueue("GameClient-in");
			}
			if (outQueue == null) {
				outQueue = new EventQueue("GameClient-out");
			}
			if (channel == null || !channel.isConnected()) {
				channel = SocketChannel.open(new InetSocketAddress(ip, port));
				channel.configureBlocking(false);
				// we don't like Nagle's algorithm
				channel.socket().setTcpNoDelay(true);
				eventReader = new NIOEventReader(channel, inQueue,
						MBWSClient.actionQueue);
				eventReader.start();
			}
		} catch (Exception e) {
			logger.error("Error during server connection", e);
			((MainMenuState) GameStateManager.getInstance().getChild("menu"))
					.displayError(ValueMapper.getText(ClientGlobals.ERROR_SERVER_CONNECT));
		}

	}

	public void disconnect() {

		if (channel != null && channel.isConnected()) {
			eventReader.setRunning(false);
			try {
				channel.close();
			} catch (IOException e) {
				logger.error("Error closing connection", e);
			}
			eventReader = null;
			channel = null;
			inQueue = null;
			outQueue = null;

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

	// TODO: Kerim what is done with the "player" ?
	protected void writeEvent(AbstractGameEvent ge) {
		logger.debug("writing event: " + ge.getEventType() + " at: "
				+ System.currentTimeMillis());
		ge.setPlayer(ClientPlayerData.getInstance());
		ByteBuffer writeBuffer = ByteBuffer.allocate(Globals.MAX_EVENT_SIZE);
		NIOUtils.prepBuffer(ge, writeBuffer, ge.getPlayer().getSessionId());
		NIOUtils.channelWrite(channel, writeBuffer);
		logger.debug("wrote event: " + ge.getEventType() + " at: "
				+ System.currentTimeMillis());
	}

	protected void processIncomingEvents() {
		AbstractGameEvent event;
		while (inQueue.size() > 0) {
			try {
				event = inQueue.deQueue();
				logger.info("Event +" + event.getEventType() + " dequeued at: "
						+ System.currentTimeMillis());
				if (event instanceof LoginEvent) {
					AccountController.getInstance().handleLoginEvent((LoginEvent) event);
				} else if (event instanceof AccountEvent) {
					AccountController.getInstance().handleEvent((AccountEvent) event);
				} else if (event instanceof CharacterEvent) {
					CharacterEvent e = (CharacterEvent) event;
					CharacterController.getInstance().handleEvent(e);
				} else if (event instanceof ServerRedirectEvent) {
					ServerRedirectEvent e = (ServerRedirectEvent) event;
					AccountController.getInstance().handleServerRedirectionEvent(e);
				} else if (event instanceof MessageEvent) {
					MessageEvent e = (MessageEvent) event;
					ChatController.getInstance().handleEvent(e);
				}
			} catch (InterruptedException ie) {
				logger.info("InterruptedException in readin in-queue", ie);
			}
		}
	}

	public void handleOutgoingEvent(AbstractGameEvent event) {
		outQueue.enQueue(event);
	}

}