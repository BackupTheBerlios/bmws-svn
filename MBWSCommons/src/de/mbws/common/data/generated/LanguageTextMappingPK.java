package de.mbws.common.data.generated;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class LanguageTextMappingPK implements Serializable {

    /** identifier field */
    private Long id;

    /** identifier field */
    private String textKey;

    /** identifier field */
    private Integer languagesId;

    /** full constructor */
    public LanguageTextMappingPK(Long id, String textKey, Integer languagesId) {
        this.id = id;
        this.textKey = textKey;
        this.languagesId = languagesId;
    }

    /** default constructor */
    public LanguageTextMappingPK() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTextKey() {
        return this.textKey;
    }

    public void setTextKey(String textKey) {
        this.textKey = textKey;
    }

    public Integer getLanguagesId() {
        return this.languagesId;
    }

    public void setLanguagesId(Integer languagesId) {
        this.languagesId = languagesId;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .append("textKey", getTextKey())
            .append("languagesId", getLanguagesId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof LanguageTextMappingPK) ) return false;
        LanguageTextMappingPK castOther = (LanguageTextMappingPK) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .append(this.getTextKey(), castOther.getTextKey())
            .append(this.getLanguagesId(), castOther.getLanguagesId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .append(getTextKey())
            .append(getLanguagesId())
            .toHashCode();
    }

}
