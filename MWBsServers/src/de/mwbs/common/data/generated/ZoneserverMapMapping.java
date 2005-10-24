package de.mwbs.common.data.generated;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class ZoneserverMapMapping implements Serializable {

    /** identifier field */
    private byte active;

    /** persistent field */
    private de.mwbs.common.data.generated.Zoneserver zoneserver;

    /** persistent field */
    private de.mwbs.common.data.generated.Map map;

    /** full constructor */
    public ZoneserverMapMapping(byte active, de.mwbs.common.data.generated.Zoneserver zoneserver, de.mwbs.common.data.generated.Map map) {
        this.active = active;
        this.zoneserver = zoneserver;
        this.map = map;
    }

    /** default constructor */
    public ZoneserverMapMapping() {
    }

    public byte getActive() {
        return this.active;
    }

    public void setActive(byte active) {
        this.active = active;
    }

    public de.mwbs.common.data.generated.Zoneserver getZoneserver() {
        return this.zoneserver;
    }

    public void setZoneserver(de.mwbs.common.data.generated.Zoneserver zoneserver) {
        this.zoneserver = zoneserver;
    }

    public de.mwbs.common.data.generated.Map getMap() {
        return this.map;
    }

    public void setMap(de.mwbs.common.data.generated.Map map) {
        this.map = map;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("active", getActive())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof ZoneserverMapMapping) ) return false;
        ZoneserverMapMapping castOther = (ZoneserverMapMapping) other;
        return new EqualsBuilder()
            .append(this.getActive(), castOther.getActive())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getActive())
            .toHashCode();
    }

}
