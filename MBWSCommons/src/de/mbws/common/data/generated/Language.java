package de.mbws.common.data.generated;

import java.io.Serializable;
import java.util.Set;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class Language implements Serializable {

    /** identifier field */
    private Integer id;

    /** persistent field */
    private String shortname;

    /** persistent field */
    private String description;

    /** persistent field */
    private Set languageTextMappings;

    /** full constructor */
    public Language(Integer id, String shortname, String description, Set languageTextMappings) {
        this.id = id;
        this.shortname = shortname;
        this.description = description;
        this.languageTextMappings = languageTextMappings;
    }

    /** default constructor */
    public Language() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getShortname() {
        return this.shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set getLanguageTextMappings() {
        return this.languageTextMappings;
    }

    public void setLanguageTextMappings(Set languageTextMappings) {
        this.languageTextMappings = languageTextMappings;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

}
