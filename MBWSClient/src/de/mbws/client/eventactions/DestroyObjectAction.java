package de.mbws.client.eventactions;

import java.nio.ByteBuffer;

import de.mbws.client.data.ObjectManager;
import de.mbws.common.events.data.AbstractEventData;
import de.mbws.common.events.data.generated.StaticObject;

public class DestroyObjectAction extends AbstractEventAction {

	public DestroyObjectAction(ByteBuffer payload, AbstractEventData eventData) {
		super(payload, eventData);
	}

	public DestroyObjectAction(AbstractEventData eventData) {
		super(eventData);
	}

	public void performAction() {
		ObjectManager.detach(((StaticObject) getEventData()).getObjectID());
	}

}
