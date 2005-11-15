package de.mbws.client.net;

import java.nio.ByteBuffer;

import org.apache.log4j.Logger;

import de.mbws.client.data.ClientPlayerData;
import de.mbws.client.eventactions.AbstractEventAction;
import de.mbws.client.eventactions.CreateObjectAction;
import de.mbws.client.eventactions.DestroyObjectAction;
import de.mbws.client.eventactions.MoveObjectAction;
import de.mbws.common.eventdata.generated.MoveData;
import de.mbws.common.eventdata.generated.WorldObject;
import de.mbws.common.events.EventTypes;
import de.mbws.common.utils.StringUtils;

public class ClientGameEventActionFactory {

	private static Logger logger = Logger.getLogger("GameEventActionFactory");

	public ClientGameEventActionFactory() {
		super();
	}

	public static AbstractEventAction getGameEventAction(ByteBuffer payload,
			ClientPlayerData instance) {
		int eventKey = payload.getInt();
		logger.debug("got event " + eventKey + " with payload "
				+ StringUtils.bytesToString(payload.array()));
		AbstractEventAction action = null;
		if (eventKey == EventTypes.MOVEMENT_START_WALK
				|| eventKey == EventTypes.MOVEMENT_START_RUN
				|| eventKey == EventTypes.MOVEMENT_STOP_WALK
				|| eventKey == EventTypes.MOVEMENT_START_TURN_LEFT
				|| eventKey == EventTypes.MOVEMENT_STOP_TURN
				|| eventKey == EventTypes.MOVEMENT_START_TURN_RIGHT
				|| eventKey == EventTypes.MOVEMENT_START_WALK_BACKWARDS) {
			action = new MoveObjectAction(payload, new MoveData());
			action.setEventType(eventKey);
		} else if (eventKey == EventTypes.OBJECT_CREATE) {
			action = new CreateObjectAction(payload, new WorldObject());
			action.setEventType(eventKey);
		} else if (eventKey == EventTypes.OBJECT_DESTROY) {
			action = new DestroyObjectAction(payload, new WorldObject());
			action.setEventType(eventKey);
		} else if (eventKey == EventTypes.MOVABLE_OBJECT_CREATE) {
			// TODO: Kerim Change action !
			action = new CreateObjectAction(payload, new WorldObject());
			action.setEventType(eventKey);
		}
		// if (action != null) {
		// action.setPlayer(p);
		// }
		return action;
	}
}
