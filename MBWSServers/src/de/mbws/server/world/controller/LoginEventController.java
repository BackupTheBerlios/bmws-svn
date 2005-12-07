package de.mbws.server.world.controller;

import org.apache.log4j.Logger;

import de.mbws.common.events.AbstractGameEvent;
import de.mbws.common.events.EventTypes;
import de.mbws.server.data.ServerCommunicationData;
import de.mbws.server.world.WorldServer;

public class LoginEventController extends WorldServerBaseEventController {
	private static Logger logger = Logger.getLogger(LoginEventController.class);

	public LoginEventController(WorldServer worldServer) {
		super(worldServer);
	}

	public void handleEvent(AbstractGameEvent event) {

        if (EventTypes.S2S_LOGIN_OK == event.getEventType()) {
            if (event.getPlayer() instanceof ServerCommunicationData) {
                Integer session = event.getPlayer().getSessionId();
                getWorldServer().addServer(session, (ServerCommunicationData) event.getPlayer());
            }
            logger.debug(event);
        }
    
	}
}