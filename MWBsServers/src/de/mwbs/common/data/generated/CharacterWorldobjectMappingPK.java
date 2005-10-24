package de.mwbs.common.data.generated;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class CharacterWorldobjectMappingPK implements Serializable {

    /** identifier field */
    private String characterdataCharactername;

    /** identifier field */
    private Long worldobjectId;

    /** full constructor */
    public CharacterWorldobjectMappingPK(String characterdataCharactername, Long worldobjectId) {
        this.characterdataCharactername = characterdataCharactername;
        this.worldobjectId = worldobjectId;
    }

    /** default constructor */
    public CharacterWorldobjectMappingPK() {
    }

    public String getCharacterdataCharactername() {
        return this.characterdataCharactername;
    }

    public void setCharacterdataCharactername(String characterdataCharactername) {
        this.characterdataCharactername = characterdataCharactername;
    }

    public Long getWorldobjectId() {
        return this.worldobjectId;
    }

    public void setWorldobjectId(Long worldobjectId) {
        this.worldobjectId = worldobjectId;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("characterdataCharactername", getCharacterdataCharactername())
            .append("worldobjectId", getWorldobjectId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof CharacterWorldobjectMappingPK) ) return false;
        CharacterWorldobjectMappingPK castOther = (CharacterWorldobjectMappingPK) other;
        return new EqualsBuilder()
            .append(this.getCharacterdataCharactername(), castOther.getCharacterdataCharactername())
            .append(this.getWorldobjectId(), castOther.getWorldobjectId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCharacterdataCharactername())
            .append(getWorldobjectId())
            .toHashCode();
    }

}
