package de.mbws.common.data.generated;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class SkillPK implements Serializable {

    /** identifier field */
    private Integer id;

    /** identifier field */
    private Integer skillGroupId;

    /** full constructor */
    public SkillPK(Integer id, Integer skillGroupId) {
        this.id = id;
        this.skillGroupId = skillGroupId;
    }

    /** default constructor */
    public SkillPK() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSkillGroupId() {
        return this.skillGroupId;
    }

    public void setSkillGroupId(Integer skillGroupId) {
        this.skillGroupId = skillGroupId;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .append("skillGroupId", getSkillGroupId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof SkillPK) ) return false;
        SkillPK castOther = (SkillPK) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .append(this.getSkillGroupId(), castOther.getSkillGroupId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .append(getSkillGroupId())
            .toHashCode();
    }

}
