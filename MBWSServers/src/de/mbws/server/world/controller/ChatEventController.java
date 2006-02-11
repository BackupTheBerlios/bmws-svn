package de.mbws.server.world.controller;

import java.util.ArrayList;

import de.mbws.common.Globals;
import de.mbws.common.events.AbstractGameEvent;
import de.mbws.common.events.EventTypes;
import de.mbws.common.events.MessageEvent;
import de.mbws.server.world.WorldServer;

/**
 * Description:
 * 
 * @author Azarai
 * 
 */
public class ChatEventController extends WorldServerBaseEventController {

    public ChatEventController(WorldServer worldServer) {
        super(worldServer);
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.mbws.server.controller.EventController#handleEvent(de.mbws.common.events.AbstractGameEvent)
     */
    @Override
    public void handleEvent(AbstractGameEvent event) {
        if (event instanceof MessageEvent) {
            MessageEvent me = (MessageEvent) event;
            int type = me.getEventType();

            ArrayList<Integer> receivers = new ArrayList<Integer>() ;
            switch (type) {
                case EventTypes.CHAT_WHISPER:
                    
                    break;
                case EventTypes.CHAT_SAY:
                case EventTypes.CHAT_SHOUT:
                case EventTypes.CHAT_EMOTE:
                    receivers = (ArrayList<Integer>) getWorldServer().getSessionIDOfAllPlayers().clone();
                    if (receivers.size() >= 1) {
                        receivers.remove(me.getPlayer().getSessionId());
                    }
                    break;

                case EventTypes.CHAT_GROUP_SAY:
                    
                    break;
                case EventTypes.CHAT_PM:
                    
                    break;
                case EventTypes.CHAT_ADMIN_COMMAND:
                    
                    break;
                default:
                    return;
            }

            if (receivers.size() >= 1) {
                me.getMessageData().setAuthor(Globals.OBJECT_ID_PREFIX_CHARACTER + me.getPlayer().getSessionId());
                me.setRecipients(receivers.toArray(new Integer[receivers.size()]));
                sendEvent(me);
            }
        }
    }
}