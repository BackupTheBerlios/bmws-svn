package de.mbws.server.data.db.generated;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class CharacterSkillMapping implements Serializable {

    /** identifier field */
    private de.mbws.server.data.db.generated.CharacterSkillMappingPK comp_id;

    /** persistent field */
    private int bonusvalue;

    /** persistent field */
    private int basevalue;

    /** nullable persistent field */
    private de.mbws.server.data.db.generated.Skill skill;

    /** nullable persistent field */
    private de.mbws.server.data.db.generated.Characterdata characterdata;

    /** full constructor */
    public CharacterSkillMapping(de.mbws.server.data.db.generated.CharacterSkillMappingPK comp_id, int bonusvalue, int basevalue, de.mbws.server.data.db.generated.Skill skill, de.mbws.server.data.db.generated.Characterdata characterdata) {
        this.comp_id = comp_id;
        this.bonusvalue = bonusvalue;
        this.basevalue = basevalue;
        this.skill = skill;
        this.characterdata = characterdata;
    }

    /** default constructor */
    public CharacterSkillMapping() {
    }

    /** minimal constructor */
    public CharacterSkillMapping(de.mbws.server.data.db.generated.CharacterSkillMappingPK comp_id, int bonusvalue, int basevalue) {
        this.comp_id = comp_id;
        this.bonusvalue = bonusvalue;
        this.basevalue = basevalue;
    }

    public de.mbws.server.data.db.generated.CharacterSkillMappingPK getComp_id() {
        return this.comp_id;
    }

    public void setComp_id(de.mbws.server.data.db.generated.CharacterSkillMappingPK comp_id) {
        this.comp_id = comp_id;
    }

    public int getBonusvalue() {
        return this.bonusvalue;
    }

    public void setBonusvalue(int bonusvalue) {
        this.bonusvalue = bonusvalue;
    }

    public int getBasevalue() {
        return this.basevalue;
    }

    public void setBasevalue(int basevalue) {
        this.basevalue = basevalue;
    }

    public de.mbws.server.data.db.generated.Skill getSkill() {
        return this.skill;
    }

    public void setSkill(de.mbws.server.data.db.generated.Skill skill) {
        this.skill = skill;
    }

    public de.mbws.server.data.db.generated.Characterdata getCharacterdata() {
        return this.characterdata;
    }

    public void setCharacterdata(de.mbws.server.data.db.generated.Characterdata characterdata) {
        this.characterdata = characterdata;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("comp_id", getComp_id())
            .toString();
    }

    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof CharacterSkillMapping) ) return false;
        CharacterSkillMapping castOther = (CharacterSkillMapping) other;
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
