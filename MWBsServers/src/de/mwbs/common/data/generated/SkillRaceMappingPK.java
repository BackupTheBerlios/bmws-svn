package de.mwbs.common.data.generated;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class SkillRaceMappingPK implements Serializable {

    /** identifier field */
    private Integer raceId;

    /** identifier field */
    private Integer skillId;

    /** full constructor */
    public SkillRaceMappingPK(Integer raceId, Integer skillId) {
        this.raceId = raceId;
        this.skillId = skillId;
    }

    /** default constructor */
    public SkillRaceMappingPK() {
    }

    public Integer getRaceId() {
        return this.raceId;
    }

    public void setRaceId(Integer raceId) {
        this.raceId = raceId;
    }

    public Integer getSkillId() {
        return this.skillId;
    }

    public void setSkillId(Integer skillId) {
        this.skillId = skillId;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("raceId", getRaceId())
            .append("skillId", getSkillId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof SkillRaceMappingPK) ) return false;
        SkillRaceMappingPK castOther = (SkillRaceMappingPK) other;
        return new EqualsBuilder()
            .append(this.getRaceId(), castOther.getRaceId())
            .append(this.getSkillId(), castOther.getSkillId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getRaceId())
            .append(getSkillId())
            .toHashCode();
    }

}
