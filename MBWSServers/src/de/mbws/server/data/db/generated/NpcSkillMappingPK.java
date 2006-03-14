package de.mbws.server.data.db.generated;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class NpcSkillMappingPK implements Serializable {

    /** identifier field */
    private Long npcId;

    /** identifier field */
    private Integer skillId;

    /** full constructor */
    public NpcSkillMappingPK(Long npcId, Integer skillId) {
        this.npcId = npcId;
        this.skillId = skillId;
    }

    /** default constructor */
    public NpcSkillMappingPK() {
    }

    public Long getNpcId() {
        return this.npcId;
    }

    public void setNpcId(Long npcId) {
        this.npcId = npcId;
    }

    public Integer getSkillId() {
        return this.skillId;
    }

    public void setSkillId(Integer skillId) {
        this.skillId = skillId;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("npcId", getNpcId())
            .append("skillId", getSkillId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof NpcSkillMappingPK) ) return false;
        NpcSkillMappingPK castOther = (NpcSkillMappingPK) other;
        return new EqualsBuilder()
            .append(this.getNpcId(), castOther.getNpcId())
            .append(this.getSkillId(), castOther.getSkillId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getNpcId())
            .append(getSkillId())
            .toHashCode();
    }

}
