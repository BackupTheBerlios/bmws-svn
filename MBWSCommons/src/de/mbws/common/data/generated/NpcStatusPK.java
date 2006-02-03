package de.mbws.common.data.generated;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class NpcStatusPK implements Serializable {

    /** identifier field */
    private Long npcId;

    /** identifier field */
    private Integer mapId;

    /** full constructor */
    public NpcStatusPK(Long npcId, Integer mapId) {
        this.npcId = npcId;
        this.mapId = mapId;
    }

    /** default constructor */
    public NpcStatusPK() {
    }

    public Long getNpcId() {
        return this.npcId;
    }

    public void setNpcId(Long npcId) {
        this.npcId = npcId;
    }

    public Integer getMapId() {
        return this.mapId;
    }

    public void setMapId(Integer mapId) {
        this.mapId = mapId;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("npcId", getNpcId())
            .append("mapId", getMapId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof NpcStatusPK) ) return false;
        NpcStatusPK castOther = (NpcStatusPK) other;
        return new EqualsBuilder()
            .append(this.getNpcId(), castOther.getNpcId())
            .append(this.getMapId(), castOther.getMapId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getNpcId())
            .append(getMapId())
            .toHashCode();
    }

}
