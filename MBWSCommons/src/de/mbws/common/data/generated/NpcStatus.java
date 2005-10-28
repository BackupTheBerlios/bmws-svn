package de.mbws.common.data.generated;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class NpcStatus implements Serializable {

    /** identifier field */
    private Long npcId;

    /** persistent field */
    private String npcstatus;

    /** nullable persistent field */
    private String gamestatus;

    /** nullable persistent field */
    private Object coordinateX;

    /** nullable persistent field */
    private Object coordinateZ;

    /** nullable persistent field */
    private Object currentmana;

    /** nullable persistent field */
    private Object currenthelth;

    /** nullable persistent field */
    private Object currentstamina;

    /** nullable persistent field */
    private Object currentstrength;

    /** nullable persistent field */
    private Object currentintelligence;

    /** nullable persistent field */
    private Object currentdexterity;

    /** nullable persistent field */
    private Object currentconstitution;

    /** nullable persistent field */
    private de.mbws.common.data.generated.Npc npc;

    /** persistent field */
    private de.mbws.common.data.generated.Map map;

    /** full constructor */
    public NpcStatus(Long npcId, String npcstatus, String gamestatus, Object coordinateX, Object coordinateZ, Object currentmana, Object currenthelth, Object currentstamina, Object currentstrength, Object currentintelligence, Object currentdexterity, Object currentconstitution, de.mbws.common.data.generated.Npc npc, de.mbws.common.data.generated.Map map) {
        this.npcId = npcId;
        this.npcstatus = npcstatus;
        this.gamestatus = gamestatus;
        this.coordinateX = coordinateX;
        this.coordinateZ = coordinateZ;
        this.currentmana = currentmana;
        this.currenthelth = currenthelth;
        this.currentstamina = currentstamina;
        this.currentstrength = currentstrength;
        this.currentintelligence = currentintelligence;
        this.currentdexterity = currentdexterity;
        this.currentconstitution = currentconstitution;
        this.npc = npc;
        this.map = map;
    }

    /** default constructor */
    public NpcStatus() {
    }

    /** minimal constructor */
    public NpcStatus(Long npcId, String npcstatus, de.mbws.common.data.generated.Map map) {
        this.npcId = npcId;
        this.npcstatus = npcstatus;
        this.map = map;
    }

    public Long getNpcId() {
        return this.npcId;
    }

    public void setNpcId(Long npcId) {
        this.npcId = npcId;
    }

    public String getNpcstatus() {
        return this.npcstatus;
    }

    public void setNpcstatus(String npcstatus) {
        this.npcstatus = npcstatus;
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

    public Object getCurrenthelth() {
        return this.currenthelth;
    }

    public void setCurrenthelth(Object currenthelth) {
        this.currenthelth = currenthelth;
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

    public Object getCurrentintelligence() {
        return this.currentintelligence;
    }

    public void setCurrentintelligence(Object currentintelligence) {
        this.currentintelligence = currentintelligence;
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

    public de.mbws.common.data.generated.Npc getNpc() {
        return this.npc;
    }

    public void setNpc(de.mbws.common.data.generated.Npc npc) {
        this.npc = npc;
    }

    public de.mbws.common.data.generated.Map getMap() {
        return this.map;
    }

    public void setMap(de.mbws.common.data.generated.Map map) {
        this.map = map;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("npcId", getNpcId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof NpcStatus) ) return false;
        NpcStatus castOther = (NpcStatus) other;
        return new EqualsBuilder()
            .append(this.getNpcId(), castOther.getNpcId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getNpcId())
            .toHashCode();
    }

}
