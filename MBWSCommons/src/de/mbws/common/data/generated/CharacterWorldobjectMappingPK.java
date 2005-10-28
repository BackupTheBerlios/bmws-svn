package de.mbws.common.data.generated;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class CharacterWorldobjectMappingPK implements Serializable {

    /** identifier field */
    private Long worldobjectId;

    /** identifier field */
    private Object characterdataId;

    /** full constructor */
    public CharacterWorldobjectMappingPK(Long worldobjectId, Object characterdataId) {
        this.worldobjectId = worldobjectId;
        this.characterdataId = characterdataId;
    }

    /** default constructor */
    public CharacterWorldobjectMappingPK() {
    }

    public Long getWorldobjectId() {
        return this.worldobjectId;
    }

    public void setWorldobjectId(Long worldobjectId) {
        this.worldobjectId = worldobjectId;
    }

    public Object getCharacterdataId() {
        return this.characterdataId;
    }

    public void setCharacterdataId(Object characterdataId) {
        this.characterdataId = characterdataId;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("worldobjectId", getWorldobjectId())
            .append("characterdataId", getCharacterdataId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof CharacterWorldobjectMappingPK) ) return false;
        CharacterWorldobjectMappingPK castOther = (CharacterWorldobjectMappingPK) other;
        return new EqualsBuilder()
            .append(this.getWorldobjectId(), castOther.getWorldobjectId())
            .append(this.getCharacterdataId(), castOther.getCharacterdataId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getWorldobjectId())
            .append(getCharacterdataId())
            .toHashCode();
    }

}
