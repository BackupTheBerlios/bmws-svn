package de.mbws.common.events;

import java.nio.ByteBuffer;

import de.mbws.common.eventdata.AbstractEventData;
import de.mbws.common.eventdata.generated.PlayerInfo;

/**
 * Description: This event is for all objects that can move or be moved
 * @author Kerim
 */
public class MoveEvent extends AbstractGameEvent {

    /**
     * Constructor for the server. Should probably be package visible 
     * @param payload
     */
    MoveEvent(ByteBuffer payload) {
        super(payload, new PlayerInfo());
    }
    
    /**
     * Constructor for the client.
     */
    public MoveEvent() {
        super(null);
    }
    
    public MoveEvent(AbstractEventData eventData) {
        super(eventData);
    }

    public PlayerInfo getPlayerInfo() {
        return (PlayerInfo) eventData;
    }

    //TODO: What is this used for ?
    @Override
    public int getEventId() {
        return 0;
    }

}
