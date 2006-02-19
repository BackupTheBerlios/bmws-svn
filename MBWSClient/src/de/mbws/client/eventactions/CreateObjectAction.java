package de.mbws.client.eventactions;

import java.nio.ByteBuffer;

import de.mbws.client.data.ObjectManager;
import de.mbws.common.events.data.AbstractEventData;
import de.mbws.common.events.data.generated.OCharacterData;

public class CreateObjectAction extends AbstractEventAction {

	public CreateObjectAction(ByteBuffer payload, AbstractEventData eventData) {
		super(payload, eventData);
	}

	public CreateObjectAction(AbstractEventData eventData) {
		super(eventData);
	}

	public void performAction() {
		ObjectManager.createMovableObject((OCharacterData)getEventData());
	}

}
