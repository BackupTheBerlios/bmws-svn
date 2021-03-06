package de.mbws.server.data.db.generated;
// Generated 14.04.2006 22:07:23 by Hibernate Tools 3.1.0.beta4

import java.util.HashSet;
import java.util.Set;


/**
 * ItemType generated by hbm2java
 */

public class ItemType  implements java.io.Serializable {


    // Fields    

     private Integer id;
     private String name;
     private boolean wearable;
     private Set<Item> items = new HashSet<Item>(0);


    // Constructors

    /** default constructor */
    public ItemType() {
    }

	/** minimal constructor */
    public ItemType(String name, boolean wearable) {
        this.name = name;
        this.wearable = wearable;
    }
    
    /** full constructor */
    public ItemType(String name, boolean wearable, Set<Item> items) {
        this.name = name;
        this.wearable = wearable;
        this.items = items;
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

    public boolean isWearable() {
        return this.wearable;
    }
    
    public void setWearable(boolean wearable) {
        this.wearable = wearable;
    }

    public Set<Item> getItems() {
        return this.items;
    }
    
    public void setItems(Set<Item> items) {
        this.items = items;
    }
   








}
