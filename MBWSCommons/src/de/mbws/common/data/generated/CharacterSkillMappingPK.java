package de.mbws.common.data.generated;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class CharacterSkillMappingPK implements Serializable {

    /** identifier field */
    private Object characterdataId;

    /** identifier field */
    private Object skillId;

    /** full constructor */
    public CharacterSkillMappingPK(Object characterdataId, Object skillId) {
        this.characterdataId = characterdataId;
        this.skillId = skillId;
    }

    /** default constructor */
    public CharacterSkillMappingPK() {
    }

    public Object getCharacterdataId() {
        return this.characterdataId;
    }

    public void setCharacterdataId(Object characterdataId) {
        this.characterdataId = characterdataId;
    }

    public Object getSkillId() {
        return this.skillId;
    }

    public void setSkillId(Object skillId) {
        this.skillId = skillId;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("characterdataId", getCharacterdataId())
            .append("skillId", getSkillId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof CharacterSkillMappingPK) ) return false;
        CharacterSkillMappingPK castOther = (CharacterSkillMappingPK) other;
        return new EqualsBuilder()
            .append(this.getCharacterdataId(), castOther.getCharacterdataId())
            .append(this.getSkillId(), castOther.getSkillId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCharacterdataId())
            .append(getSkillId())
            .toHashCode();
    }

}
