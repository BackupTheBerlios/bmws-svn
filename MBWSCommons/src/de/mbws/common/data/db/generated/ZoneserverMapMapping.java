package de.mbws.common.data.db.generated;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class ZoneserverMapMapping implements Serializable {

    /** identifier field */
    private de.mbws.common.data.db.generated.ZoneserverMapMappingPK comp_id;

    /** persistent field */
    private byte active;

    /** nullable persistent field */
    private de.mbws.common.data.db.generated.Zoneserver zoneserver;

    /** nullable persistent field */
    private de.mbws.common.data.db.generated.Map map;

    /** full constructor */
    public ZoneserverMapMapping(de.mbws.common.data.db.generated.ZoneserverMapMappingPK comp_id, byte active, de.mbws.common.data.db.generated.Zoneserver zoneserver, de.mbws.common.data.db.generated.Map map) {
        this.comp_id = comp_id;
        this.active = active;
        this.zoneserver = zoneserver;
        this.map = map;
    }

    /** default constructor */
    public ZoneserverMapMapping() {
    }

    /** minimal constructor */
    public ZoneserverMapMapping(de.mbws.common.data.db.generated.ZoneserverMapMappingPK comp_id, byte active) {
        this.comp_id = comp_id;
        this.active = active;
    }

    public de.mbws.common.data.db.generated.ZoneserverMapMappingPK getComp_id() {
        return this.comp_id;
    }

    public void setComp_id(de.mbws.common.data.db.generated.ZoneserverMapMappingPK comp_id) {
        this.comp_id = comp_id;
    }

    public byte getActive() {
        return this.active;
    }

    public void setActive(byte active) {
        this.active = active;
    }

    public de.mbws.common.data.db.generated.Zoneserver getZoneserver() {
        return this.zoneserver;
    }

    public void setZoneserver(de.mbws.common.data.db.generated.Zoneserver zoneserver) {
        this.zoneserver = zoneserver;
    }

    public de.mbws.common.data.db.generated.Map getMap() {
        return this.map;
    }

    public void setMap(de.mbws.common.data.db.generated.Map map) {
        this.map = map;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("comp_id", getComp_id())
            .toString();
    }

    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof ZoneserverMapMapping) ) return false;
        ZoneserverMapMapping castOther = (ZoneserverMapMapping) other;
        return new EqualsBuilder()
            .append(this.getComp_id(), castOther.getComp_id())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getComp_id())
            .toHashCode();
    }

}
