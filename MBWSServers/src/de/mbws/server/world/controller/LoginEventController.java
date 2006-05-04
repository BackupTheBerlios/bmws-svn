package de.mbws.server.world.controller;

import org.apache.log4j.Logger;

import de.mbws.common.events.AbstractGameEvent;
import de.mbws.common.events.EventTypes;
import de.mbws.server.AbstractTcpServer;
import de.mbws.server.data.ServerCommunicationData;

public class LoginEventController extends WorldServerBaseEventController {
    private static Logger logger = Logger.getLogger(LoginEventController.class);

    private static final Integer[] supportedEventTypes = { EventTypes.S2S_LOGIN_OK };

    /**
     * @param server
     */
    public LoginEventController(AbstractTcpServer server) {
        super(server);
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

    /*
     * (non-Javadoc)
     * 
     * @see de.mbws.server.controller.AbstractEventController#getSupportedEventTypes()
     */
    @Override
    public Integer[] getSupportedEventTypes() {
        return supportedEventTypes;
    }
}