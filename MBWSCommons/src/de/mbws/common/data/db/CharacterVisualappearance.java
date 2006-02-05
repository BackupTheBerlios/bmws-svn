package de.mbws.common.data.db;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class CharacterVisualappearance implements Serializable {

    /** identifier field */
    private Long id;

    /** persistent field */
    private int height;

    /** persistent field */
    private int skinColor;

    /** persistent field */
    private int faceType;

    /** persistent field */
    private int hairColor;

    /** persistent field */
    private int hairStyle;

    /** persistent field */
    private int hairFacial;

    /** persistent field */
    private int itemHead;

    /** persistent field */
    private int itemShoulders;

    /** persistent field */
    private int itemShirt;

    /** persistent field */
    private int itemChest;

    /** persistent field */
    private int itemBelt;

    /** persistent field */
    private int itemLegs;

    /** persistent field */
    private int itemBoots;

    /** persistent field */
    private int itemBracers;

    /** persistent field */
    private int itemGloves;

    /** persistent field */
    private int itemCape;

    /** persistent field */
    private int itemHandLeft;

    /** persistent field */
    private int itemHandRight;

    /** nullable persistent field */
    private de.mbws.common.data.db.Characterdata characterdata;

    /** full constructor */
    public CharacterVisualappearance(Long id, int height, int skinColor, int faceType, int hairColor, int hairStyle, int hairFacial, int itemHead, int itemShoulders, int itemShirt, int itemChest, int itemBelt, int itemLegs, int itemBoots, int itemBracers, int itemGloves, int itemCape, int itemHandLeft, int itemHandRight, de.mbws.common.data.db.Characterdata characterdata) {
        this.id = id;
        this.height = height;
        this.skinColor = skinColor;
        this.faceType = faceType;
        this.hairColor = hairColor;
        this.hairStyle = hairStyle;
        this.hairFacial = hairFacial;
        this.itemHead = itemHead;
        this.itemShoulders = itemShoulders;
        this.itemShirt = itemShirt;
        this.itemChest = itemChest;
        this.itemBelt = itemBelt;
        this.itemLegs = itemLegs;
        this.itemBoots = itemBoots;
        this.itemBracers = itemBracers;
        this.itemGloves = itemGloves;
        this.itemCape = itemCape;
        this.itemHandLeft = itemHandLeft;
        this.itemHandRight = itemHandRight;
        this.characterdata = characterdata;
    }

    /** default constructor */
    public CharacterVisualappearance() {
    }

    /** minimal constructor */
    public CharacterVisualappearance(Long id, int height, int skinColor, int faceType, int hairColor, int hairStyle, int hairFacial, int itemHead, int itemShoulders, int itemShirt, int itemChest, int itemBelt, int itemLegs, int itemBoots, int itemBracers, int itemGloves, int itemCape, int itemHandLeft, int itemHandRight) {
        this.id = id;
        this.height = height;
        this.skinColor = skinColor;
        this.faceType = faceType;
        this.hairColor = hairColor;
        this.hairStyle = hairStyle;
        this.hairFacial = hairFacial;
        this.itemHead = itemHead;
        this.itemShoulders = itemShoulders;
        this.itemShirt = itemShirt;
        this.itemChest = itemChest;
        this.itemBelt = itemBelt;
        this.itemLegs = itemLegs;
        this.itemBoots = itemBoots;
        this.itemBracers = itemBracers;
        this.itemGloves = itemGloves;
        this.itemCape = itemCape;
        this.itemHandLeft = itemHandLeft;
        this.itemHandRight = itemHandRight;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getSkinColor() {
        return this.skinColor;
    }

    public void setSkinColor(int skinColor) {
        this.skinColor = skinColor;
    }

    public int getFaceType() {
        return this.faceType;
    }

    public void setFaceType(int faceType) {
        this.faceType = faceType;
    }

    public int getHairColor() {
        return this.hairColor;
    }

    public void setHairColor(int hairColor) {
        this.hairColor = hairColor;
    }

    public int getHairStyle() {
        return this.hairStyle;
    }

    public void setHairStyle(int hairStyle) {
        this.hairStyle = hairStyle;
    }

    public int getHairFacial() {
        return this.hairFacial;
    }

    public void setHairFacial(int hairFacial) {
        this.hairFacial = hairFacial;
    }

    public int getItemHead() {
        return this.itemHead;
    }

    public void setItemHead(int itemHead) {
        this.itemHead = itemHead;
    }

    public int getItemShoulders() {
        return this.itemShoulders;
    }

    public void setItemShoulders(int itemShoulders) {
        this.itemShoulders = itemShoulders;
    }

    public int getItemShirt() {
        return this.itemShirt;
    }

    public void setItemShirt(int itemShirt) {
        this.itemShirt = itemShirt;
    }

    public int getItemChest() {
        return this.itemChest;
    }

    public void setItemChest(int itemChest) {
        this.itemChest = itemChest;
    }

    public int getItemBelt() {
        return this.itemBelt;
    }

    public void setItemBelt(int itemBelt) {
        this.itemBelt = itemBelt;
    }

    public int getItemLegs() {
        return this.itemLegs;
    }

    public void setItemLegs(int itemLegs) {
        this.itemLegs = itemLegs;
    }

    public int getItemBoots() {
        return this.itemBoots;
    }

    public void setItemBoots(int itemBoots) {
        this.itemBoots = itemBoots;
    }

    public int getItemBracers() {
        return this.itemBracers;
    }

    public void setItemBracers(int itemBracers) {
        this.itemBracers = itemBracers;
    }

    public int getItemGloves() {
        return this.itemGloves;
    }

    public void setItemGloves(int itemGloves) {
        this.itemGloves = itemGloves;
    }

    public int getItemCape() {
        return this.itemCape;
    }

    public void setItemCape(int itemCape) {
        this.itemCape = itemCape;
    }

    public int getItemHandLeft() {
        return this.itemHandLeft;
    }

    public void setItemHandLeft(int itemHandLeft) {
        this.itemHandLeft = itemHandLeft;
    }

    public int getItemHandRight() {
        return this.itemHandRight;
    }

    public void setItemHandRight(int itemHandRight) {
        this.itemHandRight = itemHandRight;
    }

    public de.mbws.common.data.db.Characterdata getCharacterdata() {
        return this.characterdata;
    }

    public void setCharacterdata(de.mbws.common.data.db.Characterdata characterdata) {
        this.characterdata = characterdata;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

}
