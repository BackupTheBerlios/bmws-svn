package de.mbws.client.eventactions;

import java.nio.ByteBuffer;

import com.jme.math.Quaternion;
import com.jme.math.Vector3f;

import de.mbws.client.data.AbstractGameObject;
import de.mbws.client.data.MovableObject;
import de.mbws.client.data.ObjectManager;
import de.mbws.common.eventdata.AbstractEventData;
import de.mbws.common.eventdata.generated.MoveData;

public class MoveObjectAction extends AbstractEventAction {

	public MoveObjectAction(ByteBuffer payload, AbstractEventData eventData) {
		super(payload, eventData);
	}

	public MoveObjectAction(AbstractEventData eventData) {
		super(eventData);
	}

	public void performAction() {
		System.out.println("performing MoveObjectAction at: "
				+ System.currentTimeMillis());
		MoveData md = (MoveData) eventData;
		AbstractGameObject object = ObjectManager.getObject(Integer.toString(md
				.getObjectID()));
		if (object instanceof MovableObject) {
			System.out.println("instanceof ok !");
			MovableObject mo = (MovableObject) object;
			mo.setMoveStatus(md.getMovementType());
			mo.setTurnStatus(md.getTurnType());
			mo.getModel().setLocalTranslation(
					new Vector3f(md.getLocation().getX(), md.getLocation()
							.getY(), md.getLocation().getZ()));
			mo.getModel().setLocalRotation(
					new Quaternion(md.getHeading().getX(), md.getHeading()
							.getY(), md.getHeading().getZ(),md.getHeading().getW()));
		}
	}

}
