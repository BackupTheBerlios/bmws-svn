package de.mbws.common.data.generated;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class CharacterItemMapping implements Serializable {

    /** identifier field */
    private Long id;

    /** persistent field */
    private String position;

    /** persistent field */
    private Object currentcondition;

    /** persistent field */
    private Object depositItem;

    /** persistent field */
    private de.mbws.common.data.generated.Item item;

    /** persistent field */
    private de.mbws.common.data.generated.Characterdata characterdata;

    /** full constructor */
    public CharacterItemMapping(Long id, String position, Object currentcondition, Object depositItem, de.mbws.common.data.generated.Item item, de.mbws.common.data.generated.Characterdata characterdata) {
        this.id = id;
        this.position = position;
        this.currentcondition = currentcondition;
        this.depositItem = depositItem;
        this.item = item;
        this.characterdata = characterdata;
    }

    /** default constructor */
    public CharacterItemMapping() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPosition() {
        return this.position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Object getCurrentcondition() {
        return this.currentcondition;
    }

    public void setCurrentcondition(Object currentcondition) {
        this.currentcondition = currentcondition;
    }

    public Object getDepositItem() {
        return this.depositItem;
    }

    public void setDepositItem(Object depositItem) {
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
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof CharacterItemMapping) ) return false;
        CharacterItemMapping castOther = (CharacterItemMapping) other;
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
