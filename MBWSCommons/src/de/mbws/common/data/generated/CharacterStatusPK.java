package de.mbws.common.data.generated;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class CharacterStatusPK implements Serializable {

    /** identifier field */
    private Long id;

    /** identifier field */
    private Integer mapId;

    /** full constructor */
    public CharacterStatusPK(Long id, Integer mapId) {
        this.id = id;
        this.mapId = mapId;
    }

    /** default constructor */
    public CharacterStatusPK() {
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

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .append("mapId", getMapId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof CharacterStatusPK) ) return false;
        CharacterStatusPK castOther = (CharacterStatusPK) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .append(this.getMapId(), castOther.getMapId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .append(getMapId())
            .toHashCode();
    }

}
