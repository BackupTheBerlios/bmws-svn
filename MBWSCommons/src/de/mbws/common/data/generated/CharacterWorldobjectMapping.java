package de.mbws.common.data.generated;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class CharacterWorldobjectMapping implements Serializable {

    /** identifier field */
    private de.mbws.common.data.generated.CharacterWorldobjectMappingPK comp_id;

    /** nullable persistent field */
    private de.mbws.common.data.generated.Characterdata characterdata;

    /** nullable persistent field */
    private de.mbws.common.data.generated.Worldobject worldobject;

    /** full constructor */
    public CharacterWorldobjectMapping(de.mbws.common.data.generated.CharacterWorldobjectMappingPK comp_id, de.mbws.common.data.generated.Characterdata characterdata, de.mbws.common.data.generated.Worldobject worldobject) {
        this.comp_id = comp_id;
        this.characterdata = characterdata;
        this.worldobject = worldobject;
    }

    /** default constructor */
    public CharacterWorldobjectMapping() {
    }

    /** minimal constructor */
    public CharacterWorldobjectMapping(de.mbws.common.data.generated.CharacterWorldobjectMappingPK comp_id) {
        this.comp_id = comp_id;
    }

    public de.mbws.common.data.generated.CharacterWorldobjectMappingPK getComp_id() {
        return this.comp_id;
    }

    public void setComp_id(de.mbws.common.data.generated.CharacterWorldobjectMappingPK comp_id) {
        this.comp_id = comp_id;
    }

    public de.mbws.common.data.generated.Characterdata getCharacterdata() {
        return this.characterdata;
    }

    public void setCharacterdata(de.mbws.common.data.generated.Characterdata characterdata) {
        this.characterdata = characterdata;
    }

    public de.mbws.common.data.generated.Worldobject getWorldobject() {
        return this.worldobject;
    }

    public void setWorldobject(de.mbws.common.data.generated.Worldobject worldobject) {
        this.worldobject = worldobject;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("comp_id", getComp_id())
            .toString();
    }

    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof CharacterWorldobjectMapping) ) return false;
        CharacterWorldobjectMapping castOther = (CharacterWorldobjectMapping) other;
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
