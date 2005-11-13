package de.mbws.client.eventactions;

import java.nio.ByteBuffer;

import de.mbws.common.eventdata.AbstractEventData;

public class MoveObjectAction extends AbstractEventAction {

	public MoveObjectAction(ByteBuffer payload, AbstractEventData eventData) {
		super(payload, eventData);
	}

	public MoveObjectAction(AbstractEventData eventData) {
		super(eventData);
	}

	public void performAction() {
		System.out.println("performing MoveObjectAction at: "+System.currentTimeMillis());
	}

}
