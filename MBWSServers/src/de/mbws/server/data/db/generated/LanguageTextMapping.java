package de.mbws.server.data.db.generated;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class LanguageTextMapping implements Serializable {

    /** identifier field */
    private String id;

    /** persistent field */
    private String textKey;

    /** persistent field */
    private String text;

    /** persistent field */
    private de.mbws.server.data.db.generated.Language language;

    /** full constructor */
    public LanguageTextMapping(String id, String textKey, String text, de.mbws.server.data.db.generated.Language language) {
        this.id = id;
        this.textKey = textKey;
        this.text = text;
        this.language = language;
    }

    /** default constructor */
    public LanguageTextMapping() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTextKey() {
        return this.textKey;
    }

    public void setTextKey(String textKey) {
        this.textKey = textKey;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public de.mbws.server.data.db.generated.Language getLanguage() {
        return this.language;
    }

    public void setLanguage(de.mbws.server.data.db.generated.Language language) {
        this.language = language;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

}
