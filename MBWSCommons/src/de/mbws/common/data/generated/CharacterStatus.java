package de.mbws.common.data.generated;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class CharacterStatus implements Serializable {

    /** identifier field */
    private String charstatus;

    /** identifier field */
    private String pvp;

    /** identifier field */
    private String gamestatus;

    /** identifier field */
    private int coordinateX;

    /** identifier field */
    private int coordinateY;

    /** identifier field */
    private int coordinateZ;

    /** identifier field */
    private int currentmana;

    /** identifier field */
    private int currenthealth;

    /** identifier field */
    private int currentstamina;

    /** identifier field */
    private int currentstrength;

    /** identifier field */
    private int currentinteligence;

    /** identifier field */
    private int currentdexterity;

    /** identifier field */
    private int currentconstitution;

    /** identifier field */
    private int freexp;

    /** persistent field */
    private de.mbws.common.data.generated.Characterdata characterdata;

    /** persistent field */
    private de.mbws.common.data.generated.Map map;

    /** full constructor */
    public CharacterStatus(String charstatus, String pvp, String gamestatus, int coordinateX, int coordinateY, int coordinateZ, int currentmana, int currenthealth, int currentstamina, int currentstrength, int currentinteligence, int currentdexterity, int currentconstitution, int freexp, de.mbws.common.data.generated.Characterdata characterdata, de.mbws.common.data.generated.Map map) {
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
            .append("charstatus", getCharstatus())
            .append("pvp", getPvp())
            .append("gamestatus", getGamestatus())
            .append("coordinateX", getCoordinateX())
            .append("coordinateY", getCoordinateY())
            .append("coordinateZ", getCoordinateZ())
            .append("currentmana", getCurrentmana())
            .append("currenthealth", getCurrenthealth())
            .append("currentstamina", getCurrentstamina())
            .append("currentstrength", getCurrentstrength())
            .append("currentinteligence", getCurrentinteligence())
            .append("currentdexterity", getCurrentdexterity())
            .append("currentconstitution", getCurrentconstitution())
            .append("freexp", getFreexp())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof CharacterStatus) ) return false;
        CharacterStatus castOther = (CharacterStatus) other;
        return new EqualsBuilder()
            .append(this.getCharstatus(), castOther.getCharstatus())
            .append(this.getPvp(), castOther.getPvp())
            .append(this.getGamestatus(), castOther.getGamestatus())
            .append(this.getCoordinateX(), castOther.getCoordinateX())
            .append(this.getCoordinateY(), castOther.getCoordinateY())
            .append(this.getCoordinateZ(), castOther.getCoordinateZ())
            .append(this.getCurrentmana(), castOther.getCurrentmana())
            .append(this.getCurrenthealth(), castOther.getCurrenthealth())
            .append(this.getCurrentstamina(), castOther.getCurrentstamina())
            .append(this.getCurrentstrength(), castOther.getCurrentstrength())
            .append(this.getCurrentinteligence(), castOther.getCurrentinteligence())
            .append(this.getCurrentdexterity(), castOther.getCurrentdexterity())
            .append(this.getCurrentconstitution(), castOther.getCurrentconstitution())
            .append(this.getFreexp(), castOther.getFreexp())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCharstatus())
            .append(getPvp())
            .append(getGamestatus())
            .append(getCoordinateX())
            .append(getCoordinateY())
            .append(getCoordinateZ())
            .append(getCurrentmana())
            .append(getCurrenthealth())
            .append(getCurrentstamina())
            .append(getCurrentstrength())
            .append(getCurrentinteligence())
            .append(getCurrentdexterity())
            .append(getCurrentconstitution())
            .append(getFreexp())
            .toHashCode();
    }

}
