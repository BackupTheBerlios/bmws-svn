package de.mwbs.common.data.generated;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class CharacterVisualappearance implements Serializable {

    /** identifier field */
    private de.mwbs.common.data.generated.CharacterVisualappearancePK comp_id;

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
    private de.mwbs.common.data.generated.Characterdata characterdata;

    /** full constructor */
    public CharacterVisualappearance(de.mwbs.common.data.generated.CharacterVisualappearancePK comp_id, int skinColor, int faceType, int hairColor, int hairStyle, int hairFacial, int itemHead, int itemShoulders, int itemShirt, int itemChest, int itemBelt, int itemLegs, int itemBoots, int itemBracers, int itemGloves, int itemCape, int itemHandLeft, int itemHandRight, de.mwbs.common.data.generated.Characterdata characterdata) {
        this.comp_id = comp_id;
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
    public CharacterVisualappearance(de.mwbs.common.data.generated.CharacterVisualappearancePK comp_id, int skinColor, int faceType, int hairColor, int hairStyle, int hairFacial, int itemHead, int itemShoulders, int itemShirt, int itemChest, int itemBelt, int itemLegs, int itemBoots, int itemBracers, int itemGloves, int itemCape, int itemHandLeft, int itemHandRight) {
        this.comp_id = comp_id;
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

    public de.mwbs.common.data.generated.CharacterVisualappearancePK getComp_id() {
        return this.comp_id;
    }

    public void setComp_id(de.mwbs.common.data.generated.CharacterVisualappearancePK comp_id) {
        this.comp_id = comp_id;
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

    public de.mwbs.common.data.generated.Characterdata getCharacterdata() {
        return this.characterdata;
    }

    public void setCharacterdata(de.mwbs.common.data.generated.Characterdata characterdata) {
        this.characterdata = characterdata;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("comp_id", getComp_id())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof CharacterVisualappearance) ) return false;
        CharacterVisualappearance castOther = (CharacterVisualappearance) other;
        return new EqualsBuilder()
            .append(this.getComp_id(), castOther.getComp_id())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getComp_id())
            .toHashCode();
    }

}
