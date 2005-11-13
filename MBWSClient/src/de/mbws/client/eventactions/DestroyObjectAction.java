package de.mbws.client.eventactions;

import java.nio.ByteBuffer;

import de.mbws.client.data.ObjectManager;
import de.mbws.common.eventdata.AbstractEventData;
import de.mbws.common.eventdata.generated.WorldObject;

public class DestroyObjectAction extends AbstractEventAction {

	public DestroyObjectAction(ByteBuffer payload, AbstractEventData eventData) {
		super(payload, eventData);
	}

	public DestroyObjectAction(AbstractEventData eventData) {
		super(eventData);
	}

	public void performAction() {
		ObjectManager.detach(((WorldObject)getEventData()).getObjectID());
	}

}
