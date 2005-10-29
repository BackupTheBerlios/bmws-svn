package de.mbws.common.data.generated;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class ZoneserverMapMappingPK implements Serializable {

    /** identifier field */
    private Integer mapId;

    /** identifier field */
    private Integer zoneserverId;

    /** full constructor */
    public ZoneserverMapMappingPK(Integer mapId, Integer zoneserverId) {
        this.mapId = mapId;
        this.zoneserverId = zoneserverId;
    }

    /** default constructor */
    public ZoneserverMapMappingPK() {
    }

    public Integer getMapId() {
        return this.mapId;
    }

    public void setMapId(Integer mapId) {
        this.mapId = mapId;
    }

    public Integer getZoneserverId() {
        return this.zoneserverId;
    }

    public void setZoneserverId(Integer zoneserverId) {
        this.zoneserverId = zoneserverId;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("mapId", getMapId())
            .append("zoneserverId", getZoneserverId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof ZoneserverMapMappingPK) ) return false;
        ZoneserverMapMappingPK castOther = (ZoneserverMapMappingPK) other;
        return new EqualsBuilder()
            .append(this.getMapId(), castOther.getMapId())
            .append(this.getZoneserverId(), castOther.getZoneserverId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getMapId())
            .append(getZoneserverId())
            .toHashCode();
    }

}
