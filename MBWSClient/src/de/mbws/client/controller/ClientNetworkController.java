package de.mbws.client.controller;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import org.apache.log4j.Logger;

import de.mbws.client.data.ClientPlayerData;
import de.mbws.client.net.NIOEventReader;
import de.mbws.common.EventQueue;
import de.mbws.common.Globals;
import de.mbws.common.NIOUtils;
import de.mbws.common.events.AbstractGameEvent;
import de.mbws.common.events.AccountEvent;
import de.mbws.common.events.LoginEvent;
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
				Thread.sleep(50);
			}
		} catch (Exception e) {
			logger.error("Error in main loop", e);
		}
	}

	public void connect() throws InitializationException {
		try {
			if (channel == null || !channel.isConnected()) {
				// TODO: Kerim: FIX THAT ADRESS
				channel = SocketChannel.open(new InetSocketAddress("localhost",
						5000));
				channel.configureBlocking(false);
				// we don't like Nagle's algorithm
				channel.socket().setTcpNoDelay(true);
				NIOEventReader n = new NIOEventReader(channel, inQueue);
				n.start();
			}
		} catch (Exception e) {
			logger.error("Error during server connection", e);
			throw new InitializationException(
					"Error during server connection. see log file for more information");
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
//TODO: Kerim what is done with the "player" ?
	protected void writeEvent(AbstractGameEvent ge) {
		ge.setPlayer(new ClientPlayerData());
		ByteBuffer writeBuffer = ByteBuffer.allocate(Globals.MAX_EVENT_SIZE);
		NIOUtils.prepBuffer(ge, writeBuffer);
		NIOUtils.channelWrite(channel, writeBuffer);
	}

	protected void processIncomingEvents() {
		AbstractGameEvent event;
		while (inQueue.size() > 0) {
			try {
				event = inQueue.deQueue();
				// TODO Kerim : process the events HERE
				if (event instanceof LoginEvent) {
					LoginController.getInstance().handleEvent(
							(LoginEvent) event);
				} else if (event instanceof AccountEvent) {
					AccountController.getInstance().handleEvent(
							(AccountEvent) event);
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