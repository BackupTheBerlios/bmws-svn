package de.mwbs.common.data.generated;

import java.io.Serializable;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class Map implements Serializable {

    /** identifier field */
    private Integer id;

    /** persistent field */
    private String name;

    /** persistent field */
    private String filename;

    /** persistent field */
    private Set characterStatuses;

    /** persistent field */
    private Set zoneserverMapMappings;

    /** persistent field */
    private Set npcStatuses;

    /** persistent field */
    private Set worldobjects;

    /** full constructor */
    public Map(Integer id, String name, String filename, Set characterStatuses, Set zoneserverMapMappings, Set npcStatuses, Set worldobjects) {
        this.id = id;
        this.name = name;
        this.filename = filename;
        this.characterStatuses = characterStatuses;
        this.zoneserverMapMappings = zoneserverMapMappings;
        this.npcStatuses = npcStatuses;
        this.worldobjects = worldobjects;
    }

    /** default constructor */
    public Map() {
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

    public String getFilename() {
        return this.filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Set getCharacterStatuses() {
        return this.characterStatuses;
    }

    public void setCharacterStatuses(Set characterStatuses) {
        this.characterStatuses = characterStatuses;
    }

    public Set getZoneserverMapMappings() {
        return this.zoneserverMapMappings;
    }

    public void setZoneserverMapMappings(Set zoneserverMapMappings) {
        this.zoneserverMapMappings = zoneserverMapMappings;
    }

    public Set getNpcStatuses() {
        return this.npcStatuses;
    }

    public void setNpcStatuses(Set npcStatuses) {
        this.npcStatuses = npcStatuses;
    }

    public Set getWorldobjects() {
        return this.worldobjects;
    }

    public void setWorldobjects(Set worldobjects) {
        this.worldobjects = worldobjects;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Map) ) return false;
        Map castOther = (Map) other;
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
