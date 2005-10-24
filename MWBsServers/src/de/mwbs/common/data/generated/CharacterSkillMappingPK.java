package de.mwbs.common.data.generated;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class CharacterSkillMappingPK implements Serializable {

    /** identifier field */
    private Integer skillId;

    /** identifier field */
    private String charactername;

    /** full constructor */
    public CharacterSkillMappingPK(Integer skillId, String charactername) {
        this.skillId = skillId;
        this.charactername = charactername;
    }

    /** default constructor */
    public CharacterSkillMappingPK() {
    }

    public Integer getSkillId() {
        return this.skillId;
    }

    public void setSkillId(Integer skillId) {
        this.skillId = skillId;
    }

    public String getCharactername() {
        return this.charactername;
    }

    public void setCharactername(String charactername) {
        this.charactername = charactername;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("skillId", getSkillId())
            .append("charactername", getCharactername())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof CharacterSkillMappingPK) ) return false;
        CharacterSkillMappingPK castOther = (CharacterSkillMappingPK) other;
        return new EqualsBuilder()
            .append(this.getSkillId(), castOther.getSkillId())
            .append(this.getCharactername(), castOther.getCharactername())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getSkillId())
            .append(getCharactername())
            .toHashCode();
    }

}
