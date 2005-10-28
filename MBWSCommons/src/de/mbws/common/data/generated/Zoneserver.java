package de.mbws.common.data.generated;

import java.io.Serializable;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class Zoneserver implements Serializable {

    /** identifier field */
    private Object id;

    /** persistent field */
    private String name;

    /** persistent field */
    private String ip;

    /** persistent field */
    private Object port;

    /** persistent field */
    private Set zoneserverMapMappings;

    /** full constructor */
    public Zoneserver(Object id, String name, String ip, Object port, Set zoneserverMapMappings) {
        this.id = id;
        this.name = name;
        this.ip = ip;
        this.port = port;
        this.zoneserverMapMappings = zoneserverMapMappings;
    }

    /** default constructor */
    public Zoneserver() {
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

    public String getIp() {
        return this.ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Object getPort() {
        return this.port;
    }

    public void setPort(Object port) {
        this.port = port;
    }

    public Set getZoneserverMapMappings() {
        return this.zoneserverMapMappings;
    }

    public void setZoneserverMapMappings(Set zoneserverMapMappings) {
        this.zoneserverMapMappings = zoneserverMapMappings;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Zoneserver) ) return false;
        Zoneserver castOther = (Zoneserver) other;
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
