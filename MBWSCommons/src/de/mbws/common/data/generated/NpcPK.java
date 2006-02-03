package de.mbws.common.data.generated;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class NpcPK implements Serializable {

    /** identifier field */
    private Long id;

    /** identifier field */
    private Integer raceId;

    /** full constructor */
    public NpcPK(Long id, Integer raceId) {
        this.id = id;
        this.raceId = raceId;
    }

    /** default constructor */
    public NpcPK() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRaceId() {
        return this.raceId;
    }

    public void setRaceId(Integer raceId) {
        this.raceId = raceId;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .append("raceId", getRaceId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof NpcPK) ) return false;
        NpcPK castOther = (NpcPK) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .append(this.getRaceId(), castOther.getRaceId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .append(getRaceId())
            .toHashCode();
    }

}
