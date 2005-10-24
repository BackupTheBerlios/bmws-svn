package de.mwbs.common.data.generated;

import java.io.Serializable;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class Worldobject implements Serializable {

    /** identifier field */
    private Long id;

    /** persistent field */
    private String name;

    /** persistent field */
    private String description;

    /** persistent field */
    private int coordinateX;

    /** persistent field */
    private int coordinateY;

    /** persistent field */
    private int coordinateZ;

    /** persistent field */
    private de.mwbs.common.data.generated.WorldobjectType worldobjectType;

    /** persistent field */
    private de.mwbs.common.data.generated.Map map;

    /** persistent field */
    private Set npcWorldobjectMappings;

    /** persistent field */
    private Set characterWorldobjectMappings;

    /** full constructor */
    public Worldobject(Long id, String name, String description, int coordinateX, int coordinateY, int coordinateZ, de.mwbs.common.data.generated.WorldobjectType worldobjectType, de.mwbs.common.data.generated.Map map, Set npcWorldobjectMappings, Set characterWorldobjectMappings) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
        this.coordinateZ = coordinateZ;
        this.worldobjectType = worldobjectType;
        this.map = map;
        this.npcWorldobjectMappings = npcWorldobjectMappings;
        this.characterWorldobjectMappings = characterWorldobjectMappings;
    }

    /** default constructor */
    public Worldobject() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCoordinateX() {
        return this.coordinateX;
    }

    public void setCoordinateX(int coordinateX) {
        this.coordinateX = coordinateX;
    }

    public int getCoordinateY() {
        return this.coordinateY;
    }

    public void setCoordinateY(int coordinateY) {
        this.coordinateY = coordinateY;
    }

    public int getCoordinateZ() {
        return this.coordinateZ;
    }

    public void setCoordinateZ(int coordinateZ) {
        this.coordinateZ = coordinateZ;
    }

    public de.mwbs.common.data.generated.WorldobjectType getWorldobjectType() {
        return this.worldobjectType;
    }

    public void setWorldobjectType(de.mwbs.common.data.generated.WorldobjectType worldobjectType) {
        this.worldobjectType = worldobjectType;
    }

    public de.mwbs.common.data.generated.Map getMap() {
        return this.map;
    }

    public void setMap(de.mwbs.common.data.generated.Map map) {
        this.map = map;
    }

    public Set getNpcWorldobjectMappings() {
        return this.npcWorldobjectMappings;
    }

    public void setNpcWorldobjectMappings(Set npcWorldobjectMappings) {
        this.npcWorldobjectMappings = npcWorldobjectMappings;
    }

    public Set getCharacterWorldobjectMappings() {
        return this.characterWorldobjectMappings;
    }

    public void setCharacterWorldobjectMappings(Set characterWorldobjectMappings) {
        this.characterWorldobjectMappings = characterWorldobjectMappings;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Worldobject) ) return false;
        Worldobject castOther = (Worldobject) other;
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
