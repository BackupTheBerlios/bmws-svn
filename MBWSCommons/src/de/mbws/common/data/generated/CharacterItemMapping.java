package de.mbws.common.data.generated;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class CharacterItemMapping implements Serializable {

    /** identifier field */
    private String position;

    /** identifier field */
    private int currentcondition;

    /** identifier field */
    private int depositItem;

    /** persistent field */
    private de.mbws.common.data.generated.Item item;

    /** persistent field */
    private de.mbws.common.data.generated.Characterdata characterdata;

    /** full constructor */
    public CharacterItemMapping(String position, int currentcondition, int depositItem, de.mbws.common.data.generated.Item item, de.mbws.common.data.generated.Characterdata characterdata) {
        this.position = position;
        this.currentcondition = currentcondition;
        this.depositItem = depositItem;
        this.item = item;
        this.characterdata = characterdata;
    }

    /** default constructor */
    public CharacterItemMapping() {
    }

    public String getPosition() {
        return this.position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getCurrentcondition() {
        return this.currentcondition;
    }

    public void setCurrentcondition(int currentcondition) {
        this.currentcondition = currentcondition;
    }

    public int getDepositItem() {
        return this.depositItem;
    }

    public void setDepositItem(int depositItem) {
        this.depositItem = depositItem;
    }

    public de.mbws.common.data.generated.Item getItem() {
        return this.item;
    }

    public void setItem(de.mbws.common.data.generated.Item item) {
        this.item = item;
    }

    public de.mbws.common.data.generated.Characterdata getCharacterdata() {
        return this.characterdata;
    }

    public void setCharacterdata(de.mbws.common.data.generated.Characterdata characterdata) {
        this.characterdata = characterdata;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("position", getPosition())
            .append("currentcondition", getCurrentcondition())
            .append("depositItem", getDepositItem())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof CharacterItemMapping) ) return false;
        CharacterItemMapping castOther = (CharacterItemMapping) other;
        return new EqualsBuilder()
            .append(this.getPosition(), castOther.getPosition())
            .append(this.getCurrentcondition(), castOther.getCurrentcondition())
            .append(this.getDepositItem(), castOther.getDepositItem())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getPosition())
            .append(getCurrentcondition())
            .append(getDepositItem())
            .toHashCode();
    }

}
