package de.mwbs.common.data.generated;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class SkillRaceMapping implements Serializable {

    /** identifier field */
    private de.mwbs.common.data.generated.SkillRaceMappingPK comp_id;

    /** persistent field */
    private byte hasonstartup;

    /** nullable persistent field */
    private de.mwbs.common.data.generated.Skill skill;

    /** nullable persistent field */
    private de.mwbs.common.data.generated.Race race;

    /** full constructor */
    public SkillRaceMapping(de.mwbs.common.data.generated.SkillRaceMappingPK comp_id, byte hasonstartup, de.mwbs.common.data.generated.Skill skill, de.mwbs.common.data.generated.Race race) {
        this.comp_id = comp_id;
        this.hasonstartup = hasonstartup;
        this.skill = skill;
        this.race = race;
    }

    /** default constructor */
    public SkillRaceMapping() {
    }

    /** minimal constructor */
    public SkillRaceMapping(de.mwbs.common.data.generated.SkillRaceMappingPK comp_id, byte hasonstartup) {
        this.comp_id = comp_id;
        this.hasonstartup = hasonstartup;
    }

    public de.mwbs.common.data.generated.SkillRaceMappingPK getComp_id() {
        return this.comp_id;
    }

    public void setComp_id(de.mwbs.common.data.generated.SkillRaceMappingPK comp_id) {
        this.comp_id = comp_id;
    }

    public byte getHasonstartup() {
        return this.hasonstartup;
    }

    public void setHasonstartup(byte hasonstartup) {
        this.hasonstartup = hasonstartup;
    }

    public de.mwbs.common.data.generated.Skill getSkill() {
        return this.skill;
    }

    public void setSkill(de.mwbs.common.data.generated.Skill skill) {
        this.skill = skill;
    }

    public de.mwbs.common.data.generated.Race getRace() {
        return this.race;
    }

    public void setRace(de.mwbs.common.data.generated.Race race) {
        this.race = race;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("comp_id", getComp_id())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof SkillRaceMapping) ) return false;
        SkillRaceMapping castOther = (SkillRaceMapping) other;
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
