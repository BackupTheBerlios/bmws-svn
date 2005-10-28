package de.mbws.common.data.generated;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class SkillRaceMappingPK implements Serializable {

    /** identifier field */
    private Object raceId;

    /** identifier field */
    private Object skillId;

    /** full constructor */
    public SkillRaceMappingPK(Object raceId, Object skillId) {
        this.raceId = raceId;
        this.skillId = skillId;
    }

    /** default constructor */
    public SkillRaceMappingPK() {
    }

    public Object getRaceId() {
        return this.raceId;
    }

    public void setRaceId(Object raceId) {
        this.raceId = raceId;
    }

    public Object getSkillId() {
        return this.skillId;
    }

    public void setSkillId(Object skillId) {
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
