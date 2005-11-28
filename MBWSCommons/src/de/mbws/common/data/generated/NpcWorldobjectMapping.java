package de.mbws.common.data.generated;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class NpcWorldobjectMapping implements Serializable {

    /** identifier field */
    private de.mbws.common.data.generated.NpcWorldobjectMappingPK comp_id;

    /** nullable persistent field */
    private de.mbws.common.data.generated.Worldobject worldobject;

    /** nullable persistent field */
    private de.mbws.common.data.generated.Npc npc;

    /** full constructor */
    public NpcWorldobjectMapping(de.mbws.common.data.generated.NpcWorldobjectMappingPK comp_id, de.mbws.common.data.generated.Worldobject worldobject, de.mbws.common.data.generated.Npc npc) {
        this.comp_id = comp_id;
        this.worldobject = worldobject;
        this.npc = npc;
    }

    /** default constructor */
    public NpcWorldobjectMapping() {
    }

    /** minimal constructor */
    public NpcWorldobjectMapping(de.mbws.common.data.generated.NpcWorldobjectMappingPK comp_id) {
        this.comp_id = comp_id;
    }

    public de.mbws.common.data.generated.NpcWorldobjectMappingPK getComp_id() {
        return this.comp_id;
    }

    public void setComp_id(de.mbws.common.data.generated.NpcWorldobjectMappingPK comp_id) {
        this.comp_id = comp_id;
    }

    public de.mbws.common.data.generated.Worldobject getWorldobject() {
        return this.worldobject;
    }

    public void setWorldobject(de.mbws.common.data.generated.Worldobject worldobject) {
        this.worldobject = worldobject;
    }

    public de.mbws.common.data.generated.Npc getNpc() {
        return this.npc;
    }

    public void setNpc(de.mbws.common.data.generated.Npc npc) {
        this.npc = npc;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("comp_id", getComp_id())
            .toString();
    }

    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof NpcWorldobjectMapping) ) return false;
        NpcWorldobjectMapping castOther = (NpcWorldobjectMapping) other;
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
