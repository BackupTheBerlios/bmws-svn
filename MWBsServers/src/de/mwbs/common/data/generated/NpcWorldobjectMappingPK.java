package de.mwbs.common.data.generated;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class NpcWorldobjectMappingPK implements Serializable {

    /** identifier field */
    private Long worldobjectId;

    /** identifier field */
    private Long npcId;

    /** full constructor */
    public NpcWorldobjectMappingPK(Long worldobjectId, Long npcId) {
        this.worldobjectId = worldobjectId;
        this.npcId = npcId;
    }

    /** default constructor */
    public NpcWorldobjectMappingPK() {
    }

    public Long getWorldobjectId() {
        return this.worldobjectId;
    }

    public void setWorldobjectId(Long worldobjectId) {
        this.worldobjectId = worldobjectId;
    }

    public Long getNpcId() {
        return this.npcId;
    }

    public void setNpcId(Long npcId) {
        this.npcId = npcId;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("worldobjectId", getWorldobjectId())
            .append("npcId", getNpcId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof NpcWorldobjectMappingPK) ) return false;
        NpcWorldobjectMappingPK castOther = (NpcWorldobjectMappingPK) other;
        return new EqualsBuilder()
            .append(this.getWorldobjectId(), castOther.getWorldobjectId())
            .append(this.getNpcId(), castOther.getNpcId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getWorldobjectId())
            .append(getNpcId())
            .toHashCode();
    }

}
