package de.mbws.server.data.db.generated;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class Configuration implements Serializable {

    /** identifier field */
    private Integer id;

    /** persistent field */
    private String value;

    /** full constructor */
    public Configuration(Integer id, String value) {
        this.id = id;
        this.value = value;
    }

    /** default constructor */
    public Configuration() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

}
