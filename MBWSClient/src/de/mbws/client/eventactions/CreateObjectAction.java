package de.mbws.client.eventactions;

import java.nio.ByteBuffer;

import de.mbws.client.data.ObjectManager;
import de.mbws.common.eventdata.AbstractEventData;
import de.mbws.common.eventdata.generated.WorldObject;

public class CreateObjectAction extends AbstractEventAction {

	public CreateObjectAction(ByteBuffer payload, AbstractEventData eventData) {
		super(payload, eventData);
	}

	public CreateObjectAction(AbstractEventData eventData) {
		super(eventData);
	}

	public void performAction() {
		ObjectManager.create((WorldObject)getEventData());
	}

}
