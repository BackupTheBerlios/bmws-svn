package de.mbws.common.data.generated;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class CharacterItemMappingPK implements Serializable {

    /** identifier field */
    private Long id;

    /** identifier field */
    private Long characterdataId;

    /** identifier field */
    private Long itemId;

    /** full constructor */
    public CharacterItemMappingPK(Long id, Long characterdataId, Long itemId) {
        this.id = id;
        this.characterdataId = characterdataId;
        this.itemId = itemId;
    }

    /** default constructor */
    public CharacterItemMappingPK() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCharacterdataId() {
        return this.characterdataId;
    }

    public void setCharacterdataId(Long characterdataId) {
        this.characterdataId = characterdataId;
    }

    public Long getItemId() {
        return this.itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .append("characterdataId", getCharacterdataId())
            .append("itemId", getItemId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof CharacterItemMappingPK) ) return false;
        CharacterItemMappingPK castOther = (CharacterItemMappingPK) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .append(this.getCharacterdataId(), castOther.getCharacterdataId())
            .append(this.getItemId(), castOther.getItemId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .append(getCharacterdataId())
            .append(getItemId())
            .toHashCode();
    }

}
