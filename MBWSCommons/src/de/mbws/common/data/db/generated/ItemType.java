package de.mbws.common.data.db.generated;

import java.io.Serializable;
import java.util.Set;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class ItemType implements Serializable {

    /** identifier field */
    private Integer id;

    /** persistent field */
    private String name;

    /** persistent field */
    private int containersize;

    /** persistent field */
    private byte wearable;

    /** persistent field */
    private Set items;

    /** full constructor */
    public ItemType(Integer id, String name, int containersize, byte wearable, Set items) {
        this.id = id;
        this.name = name;
        this.containersize = containersize;
        this.wearable = wearable;
        this.items = items;
    }

    /** default constructor */
    public ItemType() {
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

    public int getContainersize() {
        return this.containersize;
    }

    public void setContainersize(int containersize) {
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

}
