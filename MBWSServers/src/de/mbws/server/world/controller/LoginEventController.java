package de.mbws.server.world.controller;

import org.apache.log4j.Logger;

import de.mbws.common.events.AbstractGameEvent;
import de.mbws.common.events.EventTypes;
import de.mbws.server.data.ServerCommunicationData;
import de.mbws.server.world.WorldServer;

public class LoginEventController extends WorldServerBaseEventController {
	private static Logger logger = Logger.getLogger(LoginEventController.class);

	/**
	 * @param accountServer
	 * @param eventType
	 */
	public LoginEventController(WorldServer worldServer) {
		super(worldServer);
	}

	public void handleEvent(AbstractGameEvent event) {

        if (EventTypes.LOGIN_S2S_OK == event.getEventType()) {
            if (event.getPlayer() instanceof ServerCommunicationData) {
                Integer session = event.getPlayer().getSessionId();
                getWorldServer().addPlayer(session, event.getPlayer());
            }
            logger.debug(event);
        }
    
	}
}