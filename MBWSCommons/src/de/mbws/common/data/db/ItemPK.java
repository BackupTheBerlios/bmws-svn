package de.mbws.common.data.db;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class ItemPK implements Serializable {

    /** identifier field */
    private Long id;

    /** identifier field */
    private Integer itemTypeId;

    /** full constructor */
    public ItemPK(Long id, Integer itemTypeId) {
        this.id = id;
        this.itemTypeId = itemTypeId;
    }

    /** default constructor */
    public ItemPK() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getItemTypeId() {
        return this.itemTypeId;
    }

    public void setItemTypeId(Integer itemTypeId) {
        this.itemTypeId = itemTypeId;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .append("itemTypeId", getItemTypeId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof ItemPK) ) return false;
        ItemPK castOther = (ItemPK) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .append(this.getItemTypeId(), castOther.getItemTypeId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .append(getItemTypeId())
            .toHashCode();
    }

}
