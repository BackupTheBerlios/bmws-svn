package de.mbws.server.data.db.generated;
// Generated 14.04.2006 22:07:24 by Hibernate Tools 3.1.0.beta4

import java.util.HashSet;
import java.util.Set;


/**
 * CharacterData generated by hbm2java
 */

public class CharacterData  implements java.io.Serializable {


    // Fields    

     private Long id;
     private String charactername;
     private String gender;
     private int health;
     private int mana;
     private int stamina;
     private short age;
     private Race race;
     private CharacterStatus characterStatus;
     private Set<CharacterSkillMapping> characterSkillMappings = new HashSet<CharacterSkillMapping>(0);
     private CharacterVisualappearance characterVisualappearance;
     private Set<CharacterItemMapping> characterItemMappings = new HashSet<CharacterItemMapping>(0);
     private Set<CharacterWorldobjectMapping> characterWorldobjectMappings = new HashSet<CharacterWorldobjectMapping>(0);
     private Account account;


    // Constructors

    /** default constructor */
    public CharacterData() {
    }

	/** minimal constructor */
    public CharacterData(String charactername, String gender, int health, int mana, int stamina, short age) {
        this.charactername = charactername;
        this.gender = gender;
        this.health = health;
        this.mana = mana;
        this.stamina = stamina;
        this.age = age;
    }
    
    /** full constructor */
    public CharacterData(String charactername, String gender, int health, int mana, int stamina, short age, Race race, CharacterStatus characterStatus, Set<CharacterSkillMapping> characterSkillMappings, CharacterVisualappearance characterVisualappearance, Set<CharacterItemMapping> characterItemMappings, Set<CharacterWorldobjectMapping> characterWorldobjectMappings, Account account) {
        this.charactername = charactername;
        this.gender = gender;
        this.health = health;
        this.mana = mana;
        this.stamina = stamina;
        this.age = age;
        this.race = race;
        this.characterStatus = characterStatus;
        this.characterSkillMappings = characterSkillMappings;
        this.characterVisualappearance = characterVisualappearance;
        this.characterItemMappings = characterItemMappings;
        this.characterWorldobjectMappings = characterWorldobjectMappings;
        this.account = account;
    }
    

   
    // Property accessors

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

    public Race getRace() {
        return this.race;
    }
    
    public void setRace(Race race) {
        this.race = race;
    }

    public CharacterStatus getCharacterStatus() {
        return this.characterStatus;
    }
    
    public void setCharacterStatus(CharacterStatus characterStatus) {
        this.characterStatus = characterStatus;
    }

    public Set<CharacterSkillMapping> getCharacterSkillMappings() {
        return this.characterSkillMappings;
    }
    
    public void setCharacterSkillMappings(Set<CharacterSkillMapping> characterSkillMappings) {
        this.characterSkillMappings = characterSkillMappings;
    }

    public CharacterVisualappearance getCharacterVisualappearance() {
        return this.characterVisualappearance;
    }
    
    public void setCharacterVisualappearance(CharacterVisualappearance characterVisualappearance) {
        this.characterVisualappearance = characterVisualappearance;
    }

    public Set<CharacterItemMapping> getCharacterItemMappings() {
        return this.characterItemMappings;
    }
    
    public void setCharacterItemMappings(Set<CharacterItemMapping> characterItemMappings) {
        this.characterItemMappings = characterItemMappings;
    }

    public Set<CharacterWorldobjectMapping> getCharacterWorldobjectMappings() {
        return this.characterWorldobjectMappings;
    }
    
    public void setCharacterWorldobjectMappings(Set<CharacterWorldobjectMapping> characterWorldobjectMappings) {
        this.characterWorldobjectMappings = characterWorldobjectMappings;
    }

    public Account getAccount() {
        return this.account;
    }
    
    public void setAccount(Account account) {
        this.account = account;
    }
   








}
