package de.mwbs.common.data.generated;

import java.io.Serializable;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class Skill implements Serializable {

    /** identifier field */
    private Integer id;

    /** persistent field */
    private int skillGroupId;

    /** persistent field */
    private String name;

    /** persistent field */
    private byte availableatstartup;

    /** nullable persistent field */
    private Integer oppositeskill;

    /** persistent field */
    private int costhealth;

    /** persistent field */
    private int coststamina;

    /** persistent field */
    private int costmana;

    /** persistent field */
    private int xpcostsbuying;

    /** persistent field */
    private int goldcostsbuying;

    /** persistent field */
    private int xpcostEnhancement;

    /** nullable persistent field */
    private Integer basevalueFormula;

    /** persistent field */
    private int useDuration;

    /** persistent field */
    private int useTime;

    /** persistent field */
    private Set characterSkillMappings;

    /** persistent field */
    private Set skillRaceMappings;

    /** persistent field */
    private Set npcSkillMappings;

    /** full constructor */
    public Skill(Integer id, int skillGroupId, String name, byte availableatstartup, Integer oppositeskill, int costhealth, int coststamina, int costmana, int xpcostsbuying, int goldcostsbuying, int xpcostEnhancement, Integer basevalueFormula, int useDuration, int useTime, Set characterSkillMappings, Set skillRaceMappings, Set npcSkillMappings) {
        this.id = id;
        this.skillGroupId = skillGroupId;
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
        this.characterSkillMappings = characterSkillMappings;
        this.skillRaceMappings = skillRaceMappings;
        this.npcSkillMappings = npcSkillMappings;
    }

    /** default constructor */
    public Skill() {
    }

    /** minimal constructor */
    public Skill(Integer id, int skillGroupId, String name, byte availableatstartup, int costhealth, int coststamina, int costmana, int xpcostsbuying, int goldcostsbuying, int xpcostEnhancement, int useDuration, int useTime, Set characterSkillMappings, Set skillRaceMappings, Set npcSkillMappings) {
        this.id = id;
        this.skillGroupId = skillGroupId;
        this.name = name;
        this.availableatstartup = availableatstartup;
        this.costhealth = costhealth;
        this.coststamina = coststamina;
        this.costmana = costmana;
        this.xpcostsbuying = xpcostsbuying;
        this.goldcostsbuying = goldcostsbuying;
        this.xpcostEnhancement = xpcostEnhancement;
        this.useDuration = useDuration;
        this.useTime = useTime;
        this.characterSkillMappings = characterSkillMappings;
        this.skillRaceMappings = skillRaceMappings;
        this.npcSkillMappings = npcSkillMappings;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getSkillGroupId() {
        return this.skillGroupId;
    }

    public void setSkillGroupId(int skillGroupId) {
        this.skillGroupId = skillGroupId;
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

    public Integer getOppositeskill() {
        return this.oppositeskill;
    }

    public void setOppositeskill(Integer oppositeskill) {
        this.oppositeskill = oppositeskill;
    }

    public int getCosthealth() {
        return this.costhealth;
    }

    public void setCosthealth(int costhealth) {
        this.costhealth = costhealth;
    }

    public int getCoststamina() {
        return this.coststamina;
    }

    public void setCoststamina(int coststamina) {
        this.coststamina = coststamina;
    }

    public int getCostmana() {
        return this.costmana;
    }

    public void setCostmana(int costmana) {
        this.costmana = costmana;
    }

    public int getXpcostsbuying() {
        return this.xpcostsbuying;
    }

    public void setXpcostsbuying(int xpcostsbuying) {
        this.xpcostsbuying = xpcostsbuying;
    }

    public int getGoldcostsbuying() {
        return this.goldcostsbuying;
    }

    public void setGoldcostsbuying(int goldcostsbuying) {
        this.goldcostsbuying = goldcostsbuying;
    }

    public int getXpcostEnhancement() {
        return this.xpcostEnhancement;
    }

    public void setXpcostEnhancement(int xpcostEnhancement) {
        this.xpcostEnhancement = xpcostEnhancement;
    }

    public Integer getBasevalueFormula() {
        return this.basevalueFormula;
    }

    public void setBasevalueFormula(Integer basevalueFormula) {
        this.basevalueFormula = basevalueFormula;
    }

    public int getUseDuration() {
        return this.useDuration;
    }

    public void setUseDuration(int useDuration) {
        this.useDuration = useDuration;
    }

    public int getUseTime() {
        return this.useTime;
    }

    public void setUseTime(int useTime) {
        this.useTime = useTime;
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
