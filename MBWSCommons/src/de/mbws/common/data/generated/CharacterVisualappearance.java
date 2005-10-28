package de.mbws.common.data.generated;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class CharacterVisualappearance implements Serializable {

    /** identifier field */
    private Object id;

    /** persistent field */
    private Object height;

    /** persistent field */
    private Object skinColor;

    /** persistent field */
    private Object faceType;

    /** persistent field */
    private Object hairColor;

    /** persistent field */
    private Object hairStyle;

    /** persistent field */
    private Object hairFacial;

    /** persistent field */
    private Object itemHead;

    /** persistent field */
    private Object itemShoulders;

    /** persistent field */
    private Object itemShirt;

    /** persistent field */
    private Object itemChest;

    /** persistent field */
    private Object itemBelt;

    /** persistent field */
    private Object itemLegs;

    /** persistent field */
    private Object itemBoots;

    /** persistent field */
    private Object itemBracers;

    /** persistent field */
    private Object itemGloves;

    /** persistent field */
    private Object itemCape;

    /** persistent field */
    private Object itemHandLeft;

    /** persistent field */
    private Object itemHandRight;

    /** nullable persistent field */
    private de.mbws.common.data.generated.Characterdata characterdata;

    /** full constructor */
    public CharacterVisualappearance(Object id, Object height, Object skinColor, Object faceType, Object hairColor, Object hairStyle, Object hairFacial, Object itemHead, Object itemShoulders, Object itemShirt, Object itemChest, Object itemBelt, Object itemLegs, Object itemBoots, Object itemBracers, Object itemGloves, Object itemCape, Object itemHandLeft, Object itemHandRight, de.mbws.common.data.generated.Characterdata characterdata) {
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
    public CharacterVisualappearance(Object id, Object height, Object skinColor, Object faceType, Object hairColor, Object hairStyle, Object hairFacial, Object itemHead, Object itemShoulders, Object itemShirt, Object itemChest, Object itemBelt, Object itemLegs, Object itemBoots, Object itemBracers, Object itemGloves, Object itemCape, Object itemHandLeft, Object itemHandRight) {
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

    public Object getId() {
        return this.id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public Object getHeight() {
        return this.height;
    }

    public void setHeight(Object height) {
        this.height = height;
    }

    public Object getSkinColor() {
        return this.skinColor;
    }

    public void setSkinColor(Object skinColor) {
        this.skinColor = skinColor;
    }

    public Object getFaceType() {
        return this.faceType;
    }

    public void setFaceType(Object faceType) {
        this.faceType = faceType;
    }

    public Object getHairColor() {
        return this.hairColor;
    }

    public void setHairColor(Object hairColor) {
        this.hairColor = hairColor;
    }

    public Object getHairStyle() {
        return this.hairStyle;
    }

    public void setHairStyle(Object hairStyle) {
        this.hairStyle = hairStyle;
    }

    public Object getHairFacial() {
        return this.hairFacial;
    }

    public void setHairFacial(Object hairFacial) {
        this.hairFacial = hairFacial;
    }

    public Object getItemHead() {
        return this.itemHead;
    }

    public void setItemHead(Object itemHead) {
        this.itemHead = itemHead;
    }

    public Object getItemShoulders() {
        return this.itemShoulders;
    }

    public void setItemShoulders(Object itemShoulders) {
        this.itemShoulders = itemShoulders;
    }

    public Object getItemShirt() {
        return this.itemShirt;
    }

    public void setItemShirt(Object itemShirt) {
        this.itemShirt = itemShirt;
    }

    public Object getItemChest() {
        return this.itemChest;
    }

    public void setItemChest(Object itemChest) {
        this.itemChest = itemChest;
    }

    public Object getItemBelt() {
        return this.itemBelt;
    }

    public void setItemBelt(Object itemBelt) {
        this.itemBelt = itemBelt;
    }

    public Object getItemLegs() {
        return this.itemLegs;
    }

    public void setItemLegs(Object itemLegs) {
        this.itemLegs = itemLegs;
    }

    public Object getItemBoots() {
        return this.itemBoots;
    }

    public void setItemBoots(Object itemBoots) {
        this.itemBoots = itemBoots;
    }

    public Object getItemBracers() {
        return this.itemBracers;
    }

    public void setItemBracers(Object itemBracers) {
        this.itemBracers = itemBracers;
    }

    public Object getItemGloves() {
        return this.itemGloves;
    }

    public void setItemGloves(Object itemGloves) {
        this.itemGloves = itemGloves;
    }

    public Object getItemCape() {
        return this.itemCape;
    }

    public void setItemCape(Object itemCape) {
        this.itemCape = itemCape;
    }

    public Object getItemHandLeft() {
        return this.itemHandLeft;
    }

    public void setItemHandLeft(Object itemHandLeft) {
        this.itemHandLeft = itemHandLeft;
    }

    public Object getItemHandRight() {
        return this.itemHandRight;
    }

    public void setItemHandRight(Object itemHandRight) {
        this.itemHandRight = itemHandRight;
    }

    public de.mbws.common.data.generated.Characterdata getCharacterdata() {
        return this.characterdata;
    }

    public void setCharacterdata(de.mbws.common.data.generated.Characterdata characterdata) {
        this.characterdata = characterdata;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof CharacterVisualappearance) ) return false;
        CharacterVisualappearance castOther = (CharacterVisualappearance) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

}
