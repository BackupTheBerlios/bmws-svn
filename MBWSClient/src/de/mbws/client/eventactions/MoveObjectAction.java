package de.mbws.client.eventactions;

import java.nio.ByteBuffer;

import org.apache.log4j.Logger;

import com.jme.math.Quaternion;
import com.jme.math.Vector3f;

import de.mbws.client.controller.CharacterController;
import de.mbws.client.data.AbstractGameObject;
import de.mbws.client.data.MovableObject;
import de.mbws.client.data.ObjectManager;
import de.mbws.common.eventdata.AbstractEventData;
import de.mbws.common.eventdata.generated.MoveData;

public class MoveObjectAction extends AbstractEventAction {

	private Logger logger = Logger.getLogger(CharacterController.class);

	public MoveObjectAction(ByteBuffer payload, AbstractEventData eventData) {
		super(payload, eventData);
	}

	public MoveObjectAction(AbstractEventData eventData) {
		super(eventData);
	}

	public void performAction() {

		MoveData md = (MoveData) eventData;
		logger.info("performing MoveObjectAction for object: "
				+ md.getObjectID());
		AbstractGameObject object = ObjectManager.getObject(Integer.toString(md
				.getObjectID()));
		if (object instanceof MovableObject) {
			MovableObject mo = (MovableObject) object;
			mo.setMoveStatus(md.getMovementType());
			mo.setTurnStatus(md.getTurnType());
			mo.getModel().setLocalTranslation(
					new Vector3f(md.getLocation().getX(), md.getLocation()
							.getY(), md.getLocation().getZ()));
			mo.getModel().setLocalRotation(
					new Quaternion(md.getHeading().getX(), md.getHeading()
							.getY(), md.getHeading().getZ(), md.getHeading()
							.getW()));
		}
	}

}
