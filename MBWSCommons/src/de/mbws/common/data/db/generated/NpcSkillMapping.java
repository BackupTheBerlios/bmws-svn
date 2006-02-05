package de.mbws.common.data.db.generated;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class NpcSkillMapping implements Serializable {

    /** identifier field */
    private de.mbws.common.data.db.generated.NpcSkillMappingPK comp_id;

    /** persistent field */
    private int value;

    /** nullable persistent field */
    private de.mbws.common.data.db.generated.Skill skill;

    /** nullable persistent field */
    private de.mbws.common.data.db.generated.Npc npc;

    /** full constructor */
    public NpcSkillMapping(de.mbws.common.data.db.generated.NpcSkillMappingPK comp_id, int value, de.mbws.common.data.db.generated.Skill skill, de.mbws.common.data.db.generated.Npc npc) {
        this.comp_id = comp_id;
        this.value = value;
        this.skill = skill;
        this.npc = npc;
    }

    /** default constructor */
    public NpcSkillMapping() {
    }

    /** minimal constructor */
    public NpcSkillMapping(de.mbws.common.data.db.generated.NpcSkillMappingPK comp_id, int value) {
        this.comp_id = comp_id;
        this.value = value;
    }

    public de.mbws.common.data.db.generated.NpcSkillMappingPK getComp_id() {
        return this.comp_id;
    }

    public void setComp_id(de.mbws.common.data.db.generated.NpcSkillMappingPK comp_id) {
        this.comp_id = comp_id;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public de.mbws.common.data.db.generated.Skill getSkill() {
        return this.skill;
    }

    public void setSkill(de.mbws.common.data.db.generated.Skill skill) {
        this.skill = skill;
    }

    public de.mbws.common.data.db.generated.Npc getNpc() {
        return this.npc;
    }

    public void setNpc(de.mbws.common.data.db.generated.Npc npc) {
        this.npc = npc;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("comp_id", getComp_id())
            .toString();
    }

    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof NpcSkillMapping) ) return false;
        NpcSkillMapping castOther = (NpcSkillMapping) other;
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
