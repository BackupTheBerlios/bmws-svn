package de.mbws.common.data.db;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class CharacterStatus implements Serializable {

    /** identifier field */
    private Long id;

    /** persistent field */
    private String charstatus;

    /** persistent field */
    private String pvp;

    /** persistent field */
    private String gamestatus;

    /** persistent field */
    private int coordinateX;

    /** persistent field */
    private int coordinateY;

    /** persistent field */
    private int coordinateZ;

    /** persistent field */
    private int currentmana;

    /** persistent field */
    private int currenthealth;

    /** persistent field */
    private int currentstamina;

    /** persistent field */
    private int currentstrength;

    /** persistent field */
    private int currentinteligence;

    /** persistent field */
    private int currentdexterity;

    /** persistent field */
    private int currentconstitution;

    /** persistent field */
    private int freexp;

    /** nullable persistent field */
    private de.mbws.common.data.db.Characterdata characterdata;

    /** persistent field */
    private de.mbws.common.data.db.Map map;

    /** full constructor */
    public CharacterStatus(Long id, String charstatus, String pvp, String gamestatus, int coordinateX, int coordinateY, int coordinateZ, int currentmana, int currenthealth, int currentstamina, int currentstrength, int currentinteligence, int currentdexterity, int currentconstitution, int freexp, de.mbws.common.data.db.Characterdata characterdata, de.mbws.common.data.db.Map map) {
        this.id = id;
        this.charstatus = charstatus;
        this.pvp = pvp;
        this.gamestatus = gamestatus;
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
        this.coordinateZ = coordinateZ;
        this.currentmana = currentmana;
        this.currenthealth = currenthealth;
        this.currentstamina = currentstamina;
        this.currentstrength = currentstrength;
        this.currentinteligence = currentinteligence;
        this.currentdexterity = currentdexterity;
        this.currentconstitution = currentconstitution;
        this.freexp = freexp;
        this.characterdata = characterdata;
        this.map = map;
    }

    /** default constructor */
    public CharacterStatus() {
    }

    /** minimal constructor */
    public CharacterStatus(Long id, String charstatus, String pvp, String gamestatus, int coordinateX, int coordinateY, int coordinateZ, int currentmana, int currenthealth, int currentstamina, int currentstrength, int currentinteligence, int currentdexterity, int currentconstitution, int freexp, de.mbws.common.data.db.Map map) {
        this.id = id;
        this.charstatus = charstatus;
        this.pvp = pvp;
        this.gamestatus = gamestatus;
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
        this.coordinateZ = coordinateZ;
        this.currentmana = currentmana;
        this.currenthealth = currenthealth;
        this.currentstamina = currentstamina;
        this.currentstrength = currentstrength;
        this.currentinteligence = currentinteligence;
        this.currentdexterity = currentdexterity;
        this.currentconstitution = currentconstitution;
        this.freexp = freexp;
        this.map = map;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCharstatus() {
        return this.charstatus;
    }

    public void setCharstatus(String charstatus) {
        this.charstatus = charstatus;
    }

    public String getPvp() {
        return this.pvp;
    }

    public void setPvp(String pvp) {
        this.pvp = pvp;
    }

    public String getGamestatus() {
        return this.gamestatus;
    }

    public void setGamestatus(String gamestatus) {
        this.gamestatus = gamestatus;
    }

    public int getCoordinateX() {
        return this.coordinateX;
    }

    public void setCoordinateX(int coordinateX) {
        this.coordinateX = coordinateX;
    }

    public int getCoordinateY() {
        return this.coordinateY;
    }

    public void setCoordinateY(int coordinateY) {
        this.coordinateY = coordinateY;
    }

    public int getCoordinateZ() {
        return this.coordinateZ;
    }

    public void setCoordinateZ(int coordinateZ) {
        this.coordinateZ = coordinateZ;
    }

    public int getCurrentmana() {
        return this.currentmana;
    }

    public void setCurrentmana(int currentmana) {
        this.currentmana = currentmana;
    }

    public int getCurrenthealth() {
        return this.currenthealth;
    }

    public void setCurrenthealth(int currenthealth) {
        this.currenthealth = currenthealth;
    }

    public int getCurrentstamina() {
        return this.currentstamina;
    }

    public void setCurrentstamina(int currentstamina) {
        this.currentstamina = currentstamina;
    }

    public int getCurrentstrength() {
        return this.currentstrength;
    }

    public void setCurrentstrength(int currentstrength) {
        this.currentstrength = currentstrength;
    }

    public int getCurrentinteligence() {
        return this.currentinteligence;
    }

    public void setCurrentinteligence(int currentinteligence) {
        this.currentinteligence = currentinteligence;
    }

    public int getCurrentdexterity() {
        return this.currentdexterity;
    }

    public void setCurrentdexterity(int currentdexterity) {
        this.currentdexterity = currentdexterity;
    }

    public int getCurrentconstitution() {
        return this.currentconstitution;
    }

    public void setCurrentconstitution(int currentconstitution) {
        this.currentconstitution = currentconstitution;
    }

    public int getFreexp() {
        return this.freexp;
    }

    public void setFreexp(int freexp) {
        this.freexp = freexp;
    }

    public de.mbws.common.data.db.Characterdata getCharacterdata() {
        return this.characterdata;
    }

    public void setCharacterdata(de.mbws.common.data.db.Characterdata characterdata) {
        this.characterdata = characterdata;
    }

    public de.mbws.common.data.db.Map getMap() {
        return this.map;
    }

    public void setMap(de.mbws.common.data.db.Map map) {
        this.map = map;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

}
