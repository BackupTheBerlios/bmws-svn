package de.mbws.common.data.generated;

import java.io.Serializable;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class Characterdata implements Serializable {

    /** identifier field */
    private Object id;

    /** persistent field */
    private String charactername;

    /** persistent field */
    private String gender;

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

    /** nullable persistent field */
    private de.mbws.common.data.generated.CharacterStatus characterStatus;

    /** nullable persistent field */
    private de.mbws.common.data.generated.CharacterVisualappearance characterVisualappearance;

    /** persistent field */
    private de.mbws.common.data.generated.Account account;

    /** persistent field */
    private de.mbws.common.data.generated.Race race;

    /** persistent field */
    private Set characterSkillMappings;

    /** persistent field */
    private Set characterItemMappings;

    /** persistent field */
    private Set characterWorldobjectMappings;

    /** full constructor */
    public Characterdata(Object id, String charactername, String gender, Object health, Object mana, Object stamina, Object age, Object strength, Object intelligence, Object dexterity, Object constitution, de.mbws.common.data.generated.CharacterStatus characterStatus, de.mbws.common.data.generated.CharacterVisualappearance characterVisualappearance, de.mbws.common.data.generated.Account account, de.mbws.common.data.generated.Race race, Set characterSkillMappings, Set characterItemMappings, Set characterWorldobjectMappings) {
        this.id = id;
        this.charactername = charactername;
        this.gender = gender;
        this.health = health;
        this.mana = mana;
        this.stamina = stamina;
        this.age = age;
        this.strength = strength;
        this.intelligence = intelligence;
        this.dexterity = dexterity;
        this.constitution = constitution;
        this.characterStatus = characterStatus;
        this.characterVisualappearance = characterVisualappearance;
        this.account = account;
        this.race = race;
        this.characterSkillMappings = characterSkillMappings;
        this.characterItemMappings = characterItemMappings;
        this.characterWorldobjectMappings = characterWorldobjectMappings;
    }

    /** default constructor */
    public Characterdata() {
    }

    /** minimal constructor */
    public Characterdata(Object id, String charactername, String gender, Object health, Object mana, Object stamina, Object age, Object strength, Object intelligence, Object dexterity, Object constitution, de.mbws.common.data.generated.Account account, de.mbws.common.data.generated.Race race, Set characterSkillMappings, Set characterItemMappings, Set characterWorldobjectMappings) {
        this.id = id;
        this.charactername = charactername;
        this.gender = gender;
        this.health = health;
        this.mana = mana;
        this.stamina = stamina;
        this.age = age;
        this.strength = strength;
        this.intelligence = intelligence;
        this.dexterity = dexterity;
        this.constitution = constitution;
        this.account = account;
        this.race = race;
        this.characterSkillMappings = characterSkillMappings;
        this.characterItemMappings = characterItemMappings;
        this.characterWorldobjectMappings = characterWorldobjectMappings;
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

    public String getCharactername() {
        return this.charactername;
    }

    public void setCharactername(String charactername) {
        this.charactername = charactername;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public de.mbws.common.data.generated.CharacterStatus getCharacterStatus() {
        return this.characterStatus;
    }

    public void setCharacterStatus(de.mbws.common.data.generated.CharacterStatus characterStatus) {
        this.characterStatus = characterStatus;
    }

    public de.mbws.common.data.generated.CharacterVisualappearance getCharacterVisualappearance() {
        return this.characterVisualappearance;
    }

    public void setCharacterVisualappearance(de.mbws.common.data.generated.CharacterVisualappearance characterVisualappearance) {
        this.characterVisualappearance = characterVisualappearance;
    }

    public de.mbws.common.data.generated.Account getAccount() {
        return this.account;
    }

    public void setAccount(de.mbws.common.data.generated.Account account) {
        this.account = account;
    }

    public de.mbws.common.data.generated.Race getRace() {
        return this.race;
    }

    public void setRace(de.mbws.common.data.generated.Race race) {
        this.race = race;
    }

    public Set getCharacterSkillMappings() {
        return this.characterSkillMappings;
    }

    public void setCharacterSkillMappings(Set characterSkillMappings) {
        this.characterSkillMappings = characterSkillMappings;
    }

    public Set getCharacterItemMappings() {
        return this.characterItemMappings;
    }

    public void setCharacterItemMappings(Set characterItemMappings) {
        this.characterItemMappings = characterItemMappings;
    }

    public Set getCharacterWorldobjectMappings() {
        return this.characterWorldobjectMappings;
    }

    public void setCharacterWorldobjectMappings(Set characterWorldobjectMappings) {
        this.characterWorldobjectMappings = characterWorldobjectMappings;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Characterdata) ) return false;
        Characterdata castOther = (Characterdata) other;
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
