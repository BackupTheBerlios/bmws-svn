package de.mbws.common.data.db.generated;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class CharacterSkillMappingPK implements Serializable {

    /** identifier field */
    private Long characterdataId;

    /** identifier field */
    private Integer skillId;

    /** full constructor */
    public CharacterSkillMappingPK(Long characterdataId, Integer skillId) {
        this.characterdataId = characterdataId;
        this.skillId = skillId;
    }

    /** default constructor */
    public CharacterSkillMappingPK() {
    }

    public Long getCharacterdataId() {
        return this.characterdataId;
    }

    public void setCharacterdataId(Long characterdataId) {
        this.characterdataId = characterdataId;
    }

    public Integer getSkillId() {
        return this.skillId;
    }

    public void setSkillId(Integer skillId) {
        this.skillId = skillId;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("characterdataId", getCharacterdataId())
            .append("skillId", getSkillId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
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
