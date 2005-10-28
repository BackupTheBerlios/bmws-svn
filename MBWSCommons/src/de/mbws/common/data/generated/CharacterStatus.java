package de.mbws.common.data.generated;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class CharacterStatus implements Serializable {

    /** identifier field */
    private Object id;

    /** persistent field */
    private String charstatus;

    /** persistent field */
    private String pvp;

    /** persistent field */
    private String gamestatus;

    /** persistent field */
    private Object coordinateX;

    /** persistent field */
    private Object coordinateY;

    /** persistent field */
    private Object coordinateZ;

    /** persistent field */
    private Object currentmana;

    /** persistent field */
    private Object currenthealth;

    /** persistent field */
    private Object currentstamina;

    /** persistent field */
    private Object currentstrength;

    /** persistent field */
    private Object currentinteligence;

    /** persistent field */
    private Object currentdexterity;

    /** persistent field */
    private Object currentconstitution;

    /** persistent field */
    private Object freexp;

    /** nullable persistent field */
    private de.mbws.common.data.generated.Characterdata characterdata;

    /** persistent field */
    private de.mbws.common.data.generated.Map map;

    /** full constructor */
    public CharacterStatus(Object id, String charstatus, String pvp, String gamestatus, Object coordinateX, Object coordinateY, Object coordinateZ, Object currentmana, Object currenthealth, Object currentstamina, Object currentstrength, Object currentinteligence, Object currentdexterity, Object currentconstitution, Object freexp, de.mbws.common.data.generated.Characterdata characterdata, de.mbws.common.data.generated.Map map) {
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
    public CharacterStatus(Object id, String charstatus, String pvp, String gamestatus, Object coordinateX, Object coordinateY, Object coordinateZ, Object currentmana, Object currenthealth, Object currentstamina, Object currentstrength, Object currentinteligence, Object currentdexterity, Object currentconstitution, Object freexp, de.mbws.common.data.generated.Map map) {
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

    public Object getId() {
        return this.id;
    }

    public void setId(Object id) {
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

    public Object getCoordinateX() {
        return this.coordinateX;
    }

    public void setCoordinateX(Object coordinateX) {
        this.coordinateX = coordinateX;
    }

    public Object getCoordinateY() {
        return this.coordinateY;
    }

    public void setCoordinateY(Object coordinateY) {
        this.coordinateY = coordinateY;
    }

    public Object getCoordinateZ() {
        return this.coordinateZ;
    }

    public void setCoordinateZ(Object coordinateZ) {
        this.coordinateZ = coordinateZ;
    }

    public Object getCurrentmana() {
        return this.currentmana;
    }

    public void setCurrentmana(Object currentmana) {
        this.currentmana = currentmana;
    }

    public Object getCurrenthealth() {
        return this.currenthealth;
    }

    public void setCurrenthealth(Object currenthealth) {
        this.currenthealth = currenthealth;
    }

    public Object getCurrentstamina() {
        return this.currentstamina;
    }

    public void setCurrentstamina(Object currentstamina) {
        this.currentstamina = currentstamina;
    }

    public Object getCurrentstrength() {
        return this.currentstrength;
    }

    public void setCurrentstrength(Object currentstrength) {
        this.currentstrength = currentstrength;
    }

    public Object getCurrentinteligence() {
        return this.currentinteligence;
    }

    public void setCurrentinteligence(Object currentinteligence) {
        this.currentinteligence = currentinteligence;
    }

    public Object getCurrentdexterity() {
        return this.currentdexterity;
    }

    public void setCurrentdexterity(Object currentdexterity) {
        this.currentdexterity = currentdexterity;
    }

    public Object getCurrentconstitution() {
        return this.currentconstitution;
    }

    public void setCurrentconstitution(Object currentconstitution) {
        this.currentconstitution = currentconstitution;
    }

    public Object getFreexp() {
        return this.freexp;
    }

    public void setFreexp(Object freexp) {
        this.freexp = freexp;
    }

    public de.mbws.common.data.generated.Characterdata getCharacterdata() {
        return this.characterdata;
    }

    public void setCharacterdata(de.mbws.common.data.generated.Characterdata characterdata) {
        this.characterdata = characterdata;
    }

    public de.mbws.common.data.generated.Map getMap() {
        return this.map;
    }

    public void setMap(de.mbws.common.data.generated.Map map) {
        this.map = map;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof CharacterStatus) ) return false;
        CharacterStatus castOther = (CharacterStatus) other;
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
