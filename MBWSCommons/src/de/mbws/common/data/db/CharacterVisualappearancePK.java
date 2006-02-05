package de.mbws.common.data.db;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class CharacterVisualappearancePK implements Serializable {

    /** identifier field */
    private Integer height;

    /** identifier field */
    private String characterdataCharactername;

    /** full constructor */
    public CharacterVisualappearancePK(Integer height, String characterdataCharactername) {
        this.height = height;
        this.characterdataCharactername = characterdataCharactername;
    }

    /** default constructor */
    public CharacterVisualappearancePK() {
    }

    public Integer getHeight() {
        return this.height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getCharacterdataCharactername() {
        return this.characterdataCharactername;
    }

    public void setCharacterdataCharactername(String characterdataCharactername) {
        this.characterdataCharactername = characterdataCharactername;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("height", getHeight())
            .append("characterdataCharactername", getCharacterdataCharactername())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof CharacterVisualappearancePK) ) return false;
        CharacterVisualappearancePK castOther = (CharacterVisualappearancePK) other;
        return new EqualsBuilder()
            .append(this.getHeight(), castOther.getHeight())
            .append(this.getCharacterdataCharactername(), castOther.getCharacterdataCharactername())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getHeight())
            .append(getCharacterdataCharactername())
            .toHashCode();
    }

}
