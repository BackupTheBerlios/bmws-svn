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
    private int health;

    /** persistent field */
    private int mana;

    /** persistent field */
    private int stamina;

    /** persistent field */
    private int age;

    /** persistent field */
    private int strength;

    /** persistent field */
    private int intelligence;

    /** persistent field */
    private int dexterity;

    /** persistent field */
    private int constitution;

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
    public Npc(Long id, String name, int health, int mana, int stamina, int age, int strength, int intelligence, int dexterity, int constitution, byte isspecial, de.mbws.common.data.generated.NpcStatus npcStatus, de.mbws.common.data.generated.Race race, Set npcWorldobjectMappings, Set npcSkillMappings) {
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
    public Npc(Long id, String name, int health, int mana, int stamina, int age, int strength, int intelligence, int dexterity, int constitution, byte isspecial, de.mbws.common.data.generated.Race race, Set npcWorldobjectMappings, Set npcSkillMappings) {
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

    public int getHealth() {
        return this.health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getMana() {
        return this.mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getStamina() {
        return this.stamina;
    }

    public void setStamina(int stamina) {
        this.stamina = stamina;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getStrength() {
        return this.strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getIntelligence() {
        return this.intelligence;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public int getDexterity() {
        return this.dexterity;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public int getConstitution() {
        return this.constitution;
    }

    public void setConstitution(int constitution) {
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
