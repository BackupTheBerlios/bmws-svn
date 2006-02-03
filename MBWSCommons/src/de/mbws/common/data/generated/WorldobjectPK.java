package de.mbws.common.data.generated;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class WorldobjectPK implements Serializable {

    /** identifier field */
    private Long id;

    /** identifier field */
    private Integer mapId;

    /** identifier field */
    private Integer worldobjectTypeId;

    /** full constructor */
    public WorldobjectPK(Long id, Integer mapId, Integer worldobjectTypeId) {
        this.id = id;
        this.mapId = mapId;
        this.worldobjectTypeId = worldobjectTypeId;
    }

    /** default constructor */
    public WorldobjectPK() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMapId() {
        return this.mapId;
    }

    public void setMapId(Integer mapId) {
        this.mapId = mapId;
    }

    public Integer getWorldobjectTypeId() {
        return this.worldobjectTypeId;
    }

    public void setWorldobjectTypeId(Integer worldobjectTypeId) {
        this.worldobjectTypeId = worldobjectTypeId;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .append("mapId", getMapId())
            .append("worldobjectTypeId", getWorldobjectTypeId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof WorldobjectPK) ) return false;
        WorldobjectPK castOther = (WorldobjectPK) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .append(this.getMapId(), castOther.getMapId())
            .append(this.getWorldobjectTypeId(), castOther.getWorldobjectTypeId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .append(getMapId())
            .append(getWorldobjectTypeId())
            .toHashCode();
    }

}
