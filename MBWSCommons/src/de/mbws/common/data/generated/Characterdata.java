package de.mbws.common.data.generated;

import java.io.Serializable;
import java.util.Set;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class Characterdata implements Serializable {

    /** identifier field */
    private Long id;

    /** persistent field */
    private String charactername;

    /** persistent field */
    private String gender;

    /** persistent field */
    private int health;

    /** persistent field */
    private int mana;

    /** persistent field */
    private int stamina;

    /** persistent field */
    private short age;

    /** persistent field */
    private int strength;

    /** persistent field */
    private int intelligence;

    /** persistent field */
    private int dexterity;

    /** persistent field */
    private int constitution;

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
    public Characterdata(Long id, String charactername, String gender, int health, int mana, int stamina, short age, int strength, int intelligence, int dexterity, int constitution, de.mbws.common.data.generated.CharacterStatus characterStatus, de.mbws.common.data.generated.CharacterVisualappearance characterVisualappearance, de.mbws.common.data.generated.Account account, de.mbws.common.data.generated.Race race, Set characterSkillMappings, Set characterItemMappings, Set characterWorldobjectMappings) {
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
    public Characterdata(Long id, String charactername, String gender, int health, int mana, int stamina, short age, int strength, int intelligence, int dexterity, int constitution, de.mbws.common.data.generated.Account account, de.mbws.common.data.generated.Race race, Set characterSkillMappings, Set characterItemMappings, Set characterWorldobjectMappings) {
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

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
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

    public short getAge() {
        return this.age;
    }

    public void setAge(short age) {
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

}
