package de.mbws.common.data.generated;

import java.io.Serializable;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class Npc implements Serializable {

    /** identifier field */
    private Long id;

    /** persistent field */
    private String name;

    /** persistent field */
    private Object health;

    /** persistent field */
    private Object mana;

    /** persistent field */
    private Object stamina;

    /** persistent field */
    private Object age;

    /** persistent field */
    private Object strength;

    /** persistent field */
    private Object intelligence;

    /** persistent field */
    private Object dexterity;

    /** persistent field */
    private Object constitution;

    /** persistent field */
    private byte isspecial;

    /** nullable persistent field */
    private de.mbws.common.data.generated.NpcStatus npcStatus;

    /** persistent field */
    private de.mbws.common.data.generated.Race race;

    /** persistent field */
    private Set npcWorldobjectMappings;

    /** persistent field */
    private Set npcSkillMappings;

    /** full constructor */
    public Npc(Long id, String name, Object health, Object mana, Object stamina, Object age, Object strength, Object intelligence, Object dexterity, Object constitution, byte isspecial, de.mbws.common.data.generated.NpcStatus npcStatus, de.mbws.common.data.generated.Race race, Set npcWorldobjectMappings, Set npcSkillMappings) {
        this.id = id;
        this.name = name;
        this.health = health;
        this.mana = mana;
        this.stamina = stamina;
        this.age = age;
        this.strength = strength;
        this.intelligence = intelligence;
        this.dexterity = dexterity;
        this.constitution = constitution;
        this.isspecial = isspecial;
        this.npcStatus = npcStatus;
        this.race = race;
        this.npcWorldobjectMappings = npcWorldobjectMappings;
        this.npcSkillMappings = npcSkillMappings;
    }

    /** default constructor */
    public Npc() {
    }

    /** minimal constructor */
    public Npc(Long id, String name, Object health, Object mana, Object stamina, Object age, Object strength, Object intelligence, Object dexterity, Object constitution, byte isspecial, de.mbws.common.data.generated.Race race, Set npcWorldobjectMappings, Set npcSkillMappings) {
        this.id = id;
        this.name = name;
        this.health = health;
        this.mana = mana;
        this.stamina = stamina;
        this.age = age;
        this.strength = strength;
        this.intelligence = intelligence;
        this.dexterity = dexterity;
        this.constitution = constitution;
        this.isspecial = isspecial;
        this.race = race;
        this.npcWorldobjectMappings = npcWorldobjectMappings;
        this.npcSkillMappings = npcSkillMappings;
    }

    /** 
     * 		       auto_increment
     * 		    
     */
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getHealth() {
        return this.health;
    }

    public void setHealth(Object health) {
        this.health = health;
    }

    public Object getMana() {
        return this.mana;
    }

    public void setMana(Object mana) {
        this.mana = mana;
    }

    public Object getStamina() {
        return this.stamina;
    }

    public void setStamina(Object stamina) {
        this.stamina = stamina;
    }

    public Object getAge() {
        return this.age;
    }

    public void setAge(Object age) {
        this.age = age;
    }

    public Object getStrength() {
        return this.strength;
    }

    public void setStrength(Object strength) {
        this.strength = strength;
    }

    public Object getIntelligence() {
        return this.intelligence;
    }

    public void setIntelligence(Object intelligence) {
        this.intelligence = intelligence;
    }

    public Object getDexterity() {
        return this.dexterity;
    }

    public void setDexterity(Object dexterity) {
        this.dexterity = dexterity;
    }

    public Object getConstitution() {
        return this.constitution;
    }

    public void setConstitution(Object constitution) {
        this.constitution = constitution;
    }

    public byte getIsspecial() {
        return this.isspecial;
    }

    public void setIsspecial(byte isspecial) {
        this.isspecial = isspecial;
    }

    public de.mbws.common.data.generated.NpcStatus getNpcStatus() {
        return this.npcStatus;
    }

    public void setNpcStatus(de.mbws.common.data.generated.NpcStatus npcStatus) {
        this.npcStatus = npcStatus;
    }

    public de.mbws.common.data.generated.Race getRace() {
        return this.race;
    }

    public void setRace(de.mbws.common.data.generated.Race race) {
        this.race = race;
    }

    public Set getNpcWorldobjectMappings() {
        return this.npcWorldobjectMappings;
    }

    public void setNpcWorldobjectMappings(Set npcWorldobjectMappings) {
        this.npcWorldobjectMappings = npcWorldobjectMappings;
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
        if ( !(other instanceof Npc) ) return false;
        Npc castOther = (Npc) other;
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
