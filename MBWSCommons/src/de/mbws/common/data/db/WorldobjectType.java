package de.mbws.common.data.db;

import java.io.Serializable;
import java.util.Set;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class WorldobjectType implements Serializable {

    /** identifier field */
    private Integer id;

    /** persistent field */
    private String name;

    /** persistent field */
    private byte moveable;

    /** nullable persistent field */
    private Integer maxspeed;

    /** nullable persistent field */
    private Integer stamina;

    /** persistent field */
    private Set worldobjects;

    /** full constructor */
    public WorldobjectType(Integer id, String name, byte moveable, Integer maxspeed, Integer stamina, Set worldobjects) {
        this.id = id;
        this.name = name;
        this.moveable = moveable;
        this.maxspeed = maxspeed;
        this.stamina = stamina;
        this.worldobjects = worldobjects;
    }

    /** default constructor */
    public WorldobjectType() {
    }

    /** minimal constructor */
    public WorldobjectType(Integer id, String name, byte moveable, Set worldobjects) {
        this.id = id;
        this.name = name;
        this.moveable = moveable;
        this.worldobjects = worldobjects;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte getMoveable() {
        return this.moveable;
    }

    public void setMoveable(byte moveable) {
        this.moveable = moveable;
    }

    public Integer getMaxspeed() {
        return this.maxspeed;
    }

    public void setMaxspeed(Integer maxspeed) {
        this.maxspeed = maxspeed;
    }

    public Integer getStamina() {
        return this.stamina;
    }

    public void setStamina(Integer stamina) {
        this.stamina = stamina;
    }

    public Set getWorldobjects() {
        return this.worldobjects;
    }

    public void setWorldobjects(Set worldobjects) {
        this.worldobjects = worldobjects;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

}
