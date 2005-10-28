package de.mbws.common.data.generated;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class ZoneserverMapMappingPK implements Serializable {

    /** identifier field */
    private Object mapId;

    /** identifier field */
    private Object zoneserverId;

    /** full constructor */
    public ZoneserverMapMappingPK(Object mapId, Object zoneserverId) {
        this.mapId = mapId;
        this.zoneserverId = zoneserverId;
    }

    /** default constructor */
    public ZoneserverMapMappingPK() {
    }

    public Object getMapId() {
        return this.mapId;
    }

    public void setMapId(Object mapId) {
        this.mapId = mapId;
    }

    public Object getZoneserverId() {
        return this.zoneserverId;
    }

    public void setZoneserverId(Object zoneserverId) {
        this.zoneserverId = zoneserverId;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("mapId", getMapId())
            .append("zoneserverId", getZoneserverId())
            .toString();
    }

    public boolean equals(Object other) {
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
