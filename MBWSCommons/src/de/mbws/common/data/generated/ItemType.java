package de.mbws.common.data.generated;

import java.io.Serializable;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class ItemType implements Serializable {

    /** identifier field */
    private Object id;

    /** persistent field */
    private String name;

    /** persistent field */
    private Object containersize;

    /** persistent field */
    private byte wearable;

    /** persistent field */
    private Set items;

    /** full constructor */
    public ItemType(Object id, String name, Object containersize, byte wearable, Set items) {
        this.id = id;
        this.name = name;
        this.containersize = containersize;
        this.wearable = wearable;
        this.items = items;
    }

    /** default constructor */
    public ItemType() {
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

    public Object getContainersize() {
        return this.containersize;
    }

    public void setContainersize(Object containersize) {
        this.containersize = containersize;
    }

    public byte getWearable() {
        return this.wearable;
    }

    public void setWearable(byte wearable) {
        this.wearable = wearable;
    }

    public Set getItems() {
        return this.items;
    }

    public void setItems(Set items) {
        this.items = items;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof ItemType) ) return false;
        ItemType castOther = (ItemType) other;
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
