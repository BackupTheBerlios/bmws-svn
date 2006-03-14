package de.mbws.server.data.db.generated;

import java.io.Serializable;

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
    private Integer coordinateX;

    /** nullable persistent field */
    private Integer coordinateZ;

    /** nullable persistent field */
    private Integer currentmana;

    /** nullable persistent field */
    private Integer currenthelth;

    /** nullable persistent field */
    private Integer currentstamina;

    /** nullable persistent field */
    private Integer currentstrength;

    /** nullable persistent field */
    private Integer currentintelligence;

    /** nullable persistent field */
    private Integer currentdexterity;

    /** nullable persistent field */
    private Integer currentconstitution;

    /** nullable persistent field */
    private de.mbws.server.data.db.generated.Npc npc;

    /** persistent field */
    private de.mbws.server.data.db.generated.Map map;

    /** full constructor */
    public NpcStatus(Long npcId, String npcstatus, String gamestatus, Integer coordinateX, Integer coordinateZ, Integer currentmana, Integer currenthelth, Integer currentstamina, Integer currentstrength, Integer currentintelligence, Integer currentdexterity, Integer currentconstitution, de.mbws.server.data.db.generated.Npc npc, de.mbws.server.data.db.generated.Map map) {
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
    public NpcStatus(Long npcId, String npcstatus, de.mbws.server.data.db.generated.Map map) {
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

    public Integer getCoordinateX() {
        return this.coordinateX;
    }

    public void setCoordinateX(Integer coordinateX) {
        this.coordinateX = coordinateX;
    }

    public Integer getCoordinateZ() {
        return this.coordinateZ;
    }

    public void setCoordinateZ(Integer coordinateZ) {
        this.coordinateZ = coordinateZ;
    }

    public Integer getCurrentmana() {
        return this.currentmana;
    }

    public void setCurrentmana(Integer currentmana) {
        this.currentmana = currentmana;
    }

    public Integer getCurrenthelth() {
        return this.currenthelth;
    }

    public void setCurrenthelth(Integer currenthelth) {
        this.currenthelth = currenthelth;
    }

    public Integer getCurrentstamina() {
        return this.currentstamina;
    }

    public void setCurrentstamina(Integer currentstamina) {
        this.currentstamina = currentstamina;
    }

    public Integer getCurrentstrength() {
        return this.currentstrength;
    }

    public void setCurrentstrength(Integer currentstrength) {
        this.currentstrength = currentstrength;
    }

    public Integer getCurrentintelligence() {
        return this.currentintelligence;
    }

    public void setCurrentintelligence(Integer currentintelligence) {
        this.currentintelligence = currentintelligence;
    }

    public Integer getCurrentdexterity() {
        return this.currentdexterity;
    }

    public void setCurrentdexterity(Integer currentdexterity) {
        this.currentdexterity = currentdexterity;
    }

    public Integer getCurrentconstitution() {
        return this.currentconstitution;
    }

    public void setCurrentconstitution(Integer currentconstitution) {
        this.currentconstitution = currentconstitution;
    }

    public de.mbws.server.data.db.generated.Npc getNpc() {
        return this.npc;
    }

    public void setNpc(de.mbws.server.data.db.generated.Npc npc) {
        this.npc = npc;
    }

    public de.mbws.server.data.db.generated.Map getMap() {
        return this.map;
    }

    public void setMap(de.mbws.server.data.db.generated.Map map) {
        this.map = map;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("npcId", getNpcId())
            .toString();
    }

}
