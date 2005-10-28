package de.mbws.common.data.generated;

import java.io.Serializable;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class Race implements Serializable {

    /** identifier field */
    private Object id;

    /** persistent field */
    private String name;

    /** persistent field */
    private Object basehealth;

    /** persistent field */
    private Object basemana;

    /** persistent field */
    private Object basestamina;

    /** persistent field */
    private int modifierStrength;

    /** persistent field */
    private int modifierIntelligence;

    /** persistent field */
    private int modifierDexterity;

    /** persistent field */
    private int modifierConstitution;

    /** persistent field */
    private byte isplayable;

    /** persistent field */
    private String description;

    /** persistent field */
    private Set skillRaceMappings;

    /** persistent field */
    private Set characterdatas;

    /** persistent field */
    private Set npcs;

    /** full constructor */
    public Race(Object id, String name, Object basehealth, Object basemana, Object basestamina, int modifierStrength, int modifierIntelligence, int modifierDexterity, int modifierConstitution, byte isplayable, String description, Set skillRaceMappings, Set characterdatas, Set npcs) {
        this.id = id;
        this.name = name;
        this.basehealth = basehealth;
        this.basemana = basemana;
        this.basestamina = basestamina;
        this.modifierStrength = modifierStrength;
        this.modifierIntelligence = modifierIntelligence;
        this.modifierDexterity = modifierDexterity;
        this.modifierConstitution = modifierConstitution;
        this.isplayable = isplayable;
        this.description = description;
        this.skillRaceMappings = skillRaceMappings;
        this.characterdatas = characterdatas;
        this.npcs = npcs;
    }

    /** default constructor */
    public Race() {
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

    public Object getBasehealth() {
        return this.basehealth;
    }

    public void setBasehealth(Object basehealth) {
        this.basehealth = basehealth;
    }

    public Object getBasemana() {
        return this.basemana;
    }

    public void setBasemana(Object basemana) {
        this.basemana = basemana;
    }

    public Object getBasestamina() {
        return this.basestamina;
    }

    public void setBasestamina(Object basestamina) {
        this.basestamina = basestamina;
    }

    public int getModifierStrength() {
        return this.modifierStrength;
    }

    public void setModifierStrength(int modifierStrength) {
        this.modifierStrength = modifierStrength;
    }

    public int getModifierIntelligence() {
        return this.modifierIntelligence;
    }

    public void setModifierIntelligence(int modifierIntelligence) {
        this.modifierIntelligence = modifierIntelligence;
    }

    public int getModifierDexterity() {
        return this.modifierDexterity;
    }

    public void setModifierDexterity(int modifierDexterity) {
        this.modifierDexterity = modifierDexterity;
    }

    public int getModifierConstitution() {
        return this.modifierConstitution;
    }

    public void setModifierConstitution(int modifierConstitution) {
        this.modifierConstitution = modifierConstitution;
    }

    public byte getIsplayable() {
        return this.isplayable;
    }

    public void setIsplayable(byte isplayable) {
        this.isplayable = isplayable;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set getSkillRaceMappings() {
        return this.skillRaceMappings;
    }

    public void setSkillRaceMappings(Set skillRaceMappings) {
        this.skillRaceMappings = skillRaceMappings;
    }

    public Set getCharacterdatas() {
        return this.characterdatas;
    }

    public void setCharacterdatas(Set characterdatas) {
        this.characterdatas = characterdatas;
    }

    public Set getNpcs() {
        return this.npcs;
    }

    public void setNpcs(Set npcs) {
        this.npcs = npcs;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Race) ) return false;
        Race castOther = (Race) other;
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
