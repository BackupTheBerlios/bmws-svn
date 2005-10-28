package de.mbws.common.data.generated;

import java.io.Serializable;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class Skill implements Serializable {

    /** identifier field */
    private Object id;

    /** persistent field */
    private String name;

    /** persistent field */
    private byte availableatstartup;

    /** nullable persistent field */
    private Object oppositeskill;

    /** persistent field */
    private Object costhealth;

    /** persistent field */
    private Object coststamina;

    /** persistent field */
    private Object costmana;

    /** persistent field */
    private Object xpcostsbuying;

    /** persistent field */
    private Object goldcostsbuying;

    /** persistent field */
    private Object xpcostEnhancement;

    /** persistent field */
    private Object basevalueFormula;

    /** persistent field */
    private Object useDuration;

    /** persistent field */
    private Object useTime;

    /** persistent field */
    private de.mbws.common.data.generated.SkillGroup skillGroup;

    /** persistent field */
    private Set characterSkillMappings;

    /** persistent field */
    private Set skillRaceMappings;

    /** persistent field */
    private Set npcSkillMappings;

    /** full constructor */
    public Skill(Object id, String name, byte availableatstartup, Object oppositeskill, Object costhealth, Object coststamina, Object costmana, Object xpcostsbuying, Object goldcostsbuying, Object xpcostEnhancement, Object basevalueFormula, Object useDuration, Object useTime, de.mbws.common.data.generated.SkillGroup skillGroup, Set characterSkillMappings, Set skillRaceMappings, Set npcSkillMappings) {
        this.id = id;
        this.name = name;
        this.availableatstartup = availableatstartup;
        this.oppositeskill = oppositeskill;
        this.costhealth = costhealth;
        this.coststamina = coststamina;
        this.costmana = costmana;
        this.xpcostsbuying = xpcostsbuying;
        this.goldcostsbuying = goldcostsbuying;
        this.xpcostEnhancement = xpcostEnhancement;
        this.basevalueFormula = basevalueFormula;
        this.useDuration = useDuration;
        this.useTime = useTime;
        this.skillGroup = skillGroup;
        this.characterSkillMappings = characterSkillMappings;
        this.skillRaceMappings = skillRaceMappings;
        this.npcSkillMappings = npcSkillMappings;
    }

    /** default constructor */
    public Skill() {
    }

    /** minimal constructor */
    public Skill(Object id, String name, byte availableatstartup, Object costhealth, Object coststamina, Object costmana, Object xpcostsbuying, Object goldcostsbuying, Object xpcostEnhancement, Object basevalueFormula, Object useDuration, Object useTime, de.mbws.common.data.generated.SkillGroup skillGroup, Set characterSkillMappings, Set skillRaceMappings, Set npcSkillMappings) {
        this.id = id;
        this.name = name;
        this.availableatstartup = availableatstartup;
        this.costhealth = costhealth;
        this.coststamina = coststamina;
        this.costmana = costmana;
        this.xpcostsbuying = xpcostsbuying;
        this.goldcostsbuying = goldcostsbuying;
        this.xpcostEnhancement = xpcostEnhancement;
        this.basevalueFormula = basevalueFormula;
        this.useDuration = useDuration;
        this.useTime = useTime;
        this.skillGroup = skillGroup;
        this.characterSkillMappings = characterSkillMappings;
        this.skillRaceMappings = skillRaceMappings;
        this.npcSkillMappings = npcSkillMappings;
    }

    /** 
     * 		       auto_increment
     * 		    
     */
    public Object getId() {
        return this.id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte getAvailableatstartup() {
        return this.availableatstartup;
    }

    public void setAvailableatstartup(byte availableatstartup) {
        this.availableatstartup = availableatstartup;
    }

    public Object getOppositeskill() {
        return this.oppositeskill;
    }

    public void setOppositeskill(Object oppositeskill) {
        this.oppositeskill = oppositeskill;
    }

    public Object getCosthealth() {
        return this.costhealth;
    }

    public void setCosthealth(Object costhealth) {
        this.costhealth = costhealth;
    }

    public Object getCoststamina() {
        return this.coststamina;
    }

    public void setCoststamina(Object coststamina) {
        this.coststamina = coststamina;
    }

    public Object getCostmana() {
        return this.costmana;
    }

    public void setCostmana(Object costmana) {
        this.costmana = costmana;
    }

    public Object getXpcostsbuying() {
        return this.xpcostsbuying;
    }

    public void setXpcostsbuying(Object xpcostsbuying) {
        this.xpcostsbuying = xpcostsbuying;
    }

    public Object getGoldcostsbuying() {
        return this.goldcostsbuying;
    }

    public void setGoldcostsbuying(Object goldcostsbuying) {
        this.goldcostsbuying = goldcostsbuying;
    }

    public Object getXpcostEnhancement() {
        return this.xpcostEnhancement;
    }

    public void setXpcostEnhancement(Object xpcostEnhancement) {
        this.xpcostEnhancement = xpcostEnhancement;
    }

    public Object getBasevalueFormula() {
        return this.basevalueFormula;
    }

    public void setBasevalueFormula(Object basevalueFormula) {
        this.basevalueFormula = basevalueFormula;
    }

    public Object getUseDuration() {
        return this.useDuration;
    }

    public void setUseDuration(Object useDuration) {
        this.useDuration = useDuration;
    }

    public Object getUseTime() {
        return this.useTime;
    }

    public void setUseTime(Object useTime) {
        this.useTime = useTime;
    }

    public de.mbws.common.data.generated.SkillGroup getSkillGroup() {
        return this.skillGroup;
    }

    public void setSkillGroup(de.mbws.common.data.generated.SkillGroup skillGroup) {
        this.skillGroup = skillGroup;
    }

    public Set getCharacterSkillMappings() {
        return this.characterSkillMappings;
    }

    public void setCharacterSkillMappings(Set characterSkillMappings) {
        this.characterSkillMappings = characterSkillMappings;
    }

    public Set getSkillRaceMappings() {
        return this.skillRaceMappings;
    }

    public void setSkillRaceMappings(Set skillRaceMappings) {
        this.skillRaceMappings = skillRaceMappings;
    }

    public Set getNpcSkillMappings() {
        return this.npcSkillMappings;
    }

    public void setNpcSkillMappings(Set npcSkillMappings) {
        this.npcSkillMappings = npcSkillMappings;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Skill) ) return false;
        Skill castOther = (Skill) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

}
