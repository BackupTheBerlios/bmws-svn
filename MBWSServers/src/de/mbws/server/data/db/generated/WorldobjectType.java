package de.mbws.server.data.db.generated;
// Generated 14.04.2006 22:07:24 by Hibernate Tools 3.1.0.beta4

import java.util.HashSet;
import java.util.Set;


/**
 * WorldobjectType generated by hbm2java
 */

public class WorldobjectType  implements java.io.Serializable {


    // Fields    

     private Integer id;
     private String name;
     private byte moveable;
     private Integer maxspeed;
     private Integer stamina;
     private Set<Worldobject> worldobjects = new HashSet<Worldobject>(0);


    // Constructors

    /** default constructor */
    public WorldobjectType() {
    }

	/** minimal constructor */
    public WorldobjectType(Integer id, String name, byte moveable) {
        this.id = id;
        this.name = name;
        this.moveable = moveable;
    }
    
    /** full constructor */
    public WorldobjectType(Integer id, String name, byte moveable, Integer maxspeed, Integer stamina, Set<Worldobject> worldobjects) {
        this.id = id;
        this.name = name;
        this.moveable = moveable;
        this.maxspeed = maxspeed;
        this.stamina = stamina;
        this.worldobjects = worldobjects;
    }
    

   
    // Property accessors

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

    public Set<Worldobject> getWorldobjects() {
        return this.worldobjects;
    }
    
    public void setWorldobjects(Set<Worldobject> worldobjects) {
        this.worldobjects = worldobjects;
    }
   








}
