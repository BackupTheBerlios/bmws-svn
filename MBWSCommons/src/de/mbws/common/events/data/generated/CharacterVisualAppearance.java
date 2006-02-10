/** Generated class. Do not change !!! **/
package de.mbws.common.events.data.generated;

import de.mbws.common.events.data.AbstractEventData;
import java.util.*;
import java.nio.ByteBuffer;

public class CharacterVisualAppearance extends AbstractEventData { 
	private int height;
	private int skinColor;
	private int faceType;
	private int hairColor;
	private int hairStyle;
	private int hairFacial;
	private int itemHead;
	private int itemShoulders;
	private int itemShirt;
	private int itemChest;
	private int itemBelt;
	private int itemLegs;
	private int itemBoots;
	private int itemBracers;
	private int itemGloves;
	private int itemCape;
	private int itemHandLeft;
	private int itemHandRight;


	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	} 

	public int getSkinColor() {
		return skinColor;
	}

	public void setSkinColor(int skinColor) {
		this.skinColor = skinColor;
	} 

	public int getFaceType() {
		return faceType;
	}

	public void setFaceType(int faceType) {
		this.faceType = faceType;
	} 

	public int getHairColor() {
		return hairColor;
	}

	public void setHairColor(int hairColor) {
		this.hairColor = hairColor;
	} 

	public int getHairStyle() {
		return hairStyle;
	}

	public void setHairStyle(int hairStyle) {
		this.hairStyle = hairStyle;
	} 

	public int getHairFacial() {
		return hairFacial;
	}

	public void setHairFacial(int hairFacial) {
		this.hairFacial = hairFacial;
	} 

	public int getItemHead() {
		return itemHead;
	}

	public void setItemHead(int itemHead) {
		this.itemHead = itemHead;
	} 

	public int getItemShoulders() {
		return itemShoulders;
	}

	public void setItemShoulders(int itemShoulders) {
		this.itemShoulders = itemShoulders;
	} 

	public int getItemShirt() {
		return itemShirt;
	}

	public void setItemShirt(int itemShirt) {
		this.itemShirt = itemShirt;
	} 

	public int getItemChest() {
		return itemChest;
	}

	public void setItemChest(int itemChest) {
		this.itemChest = itemChest;
	} 

	public int getItemBelt() {
		return itemBelt;
	}

	public void setItemBelt(int itemBelt) {
		this.itemBelt = itemBelt;
	} 

	public int getItemLegs() {
		return itemLegs;
	}

	public void setItemLegs(int itemLegs) {
		this.itemLegs = itemLegs;
	} 

	public int getItemBoots() {
		return itemBoots;
	}

	public void setItemBoots(int itemBoots) {
		this.itemBoots = itemBoots;
	} 

	public int getItemBracers() {
		return itemBracers;
	}

	public void setItemBracers(int itemBracers) {
		this.itemBracers = itemBracers;
	} 

	public int getItemGloves() {
		return itemGloves;
	}

	public void setItemGloves(int itemGloves) {
		this.itemGloves = itemGloves;
	} 

	public int getItemCape() {
		return itemCape;
	}

	public void setItemCape(int itemCape) {
		this.itemCape = itemCape;
	} 

	public int getItemHandLeft() {
		return itemHandLeft;
	}

	public void setItemHandLeft(int itemHandLeft) {
		this.itemHandLeft = itemHandLeft;
	} 

	public int getItemHandRight() {
		return itemHandRight;
	}

	public void setItemHandRight(int itemHandRight) {
		this.itemHandRight = itemHandRight;
	} 


	public void deserialize(ByteBuffer payload) {
		height = payload.getInt();
		skinColor = payload.getInt();
		faceType = payload.getInt();
		hairColor = payload.getInt();
		hairStyle = payload.getInt();
		hairFacial = payload.getInt();
		itemHead = payload.getInt();
		itemShoulders = payload.getInt();
		itemShirt = payload.getInt();
		itemChest = payload.getInt();
		itemBelt = payload.getInt();
		itemLegs = payload.getInt();
		itemBoots = payload.getInt();
		itemBracers = payload.getInt();
		itemGloves = payload.getInt();
		itemCape = payload.getInt();
		itemHandLeft = payload.getInt();
		itemHandRight = payload.getInt();
	}

	public int serialize(ByteBuffer payload) {
		payload.putInt(height);
		payload.putInt(skinColor);
		payload.putInt(faceType);
		payload.putInt(hairColor);
		payload.putInt(hairStyle);
		payload.putInt(hairFacial);
		payload.putInt(itemHead);
		payload.putInt(itemShoulders);
		payload.putInt(itemShirt);
		payload.putInt(itemChest);
		payload.putInt(itemBelt);
		payload.putInt(itemLegs);
		payload.putInt(itemBoots);
		payload.putInt(itemBracers);
		payload.putInt(itemGloves);
		payload.putInt(itemCape);
		payload.putInt(itemHandLeft);
		payload.putInt(itemHandRight);
		return payload.position();
	}

	public static void serializeList(ByteBuffer payload, List<CharacterVisualAppearance> list) {
		if(list==null) return;
		payload.putInt(list.size());
		Iterator<CharacterVisualAppearance> it = list.iterator();
		while (it.hasNext()) {
			it.next().serialize(payload);
		}
	}

	public static List<CharacterVisualAppearance> deserializeList(ByteBuffer payload) {
		List<CharacterVisualAppearance> list = new LinkedList<CharacterVisualAppearance>();
		int size = payload.getInt();
		for (int i=0; i<size; i++) {
			CharacterVisualAppearance element = new CharacterVisualAppearance();
			element.deserialize(payload);
			list.add(element);
		}
		return list;
	}
}