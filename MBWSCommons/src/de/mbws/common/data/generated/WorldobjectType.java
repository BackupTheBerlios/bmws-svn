package de.mbws.common.data.generated;

import java.io.Serializable;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class WorldobjectType implements Serializable {

    /** identifier field */
    private Object id;

    /** persistent field */
    private String name;

    /** persistent field */
    private byte moveable;

    /** nullable persistent field */
    private Object maxspeed;

    /** nullable persistent field */
    private Object stamina;

    /** persistent field */
    private Set worldobjects;

    /** full constructor */
    public WorldobjectType(Object id, String name, byte moveable, Object maxspeed, Object stamina, Set worldobjects) {
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
    public WorldobjectType(Object id, String name, byte moveable, Set worldobjects) {
        this.id = id;
        this.name = name;
        this.moveable = moveable;
        this.worldobjects = worldobjects;
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

    public Object getMaxspeed() {
        return this.maxspeed;
    }

    public void setMaxspeed(Object maxspeed) {
        this.maxspeed = maxspeed;
    }

    public Object getStamina() {
        return this.stamina;
    }

    public void setStamina(Object stamina) {
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

    public boolean equals(Object other) {
        if ( !(other instanceof WorldobjectType) ) return false;
        WorldobjectType castOther = (WorldobjectType) other;
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
