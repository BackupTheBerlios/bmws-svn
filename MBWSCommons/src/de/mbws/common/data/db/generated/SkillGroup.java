package de.mbws.common.data.db.generated;

import java.io.Serializable;
import java.util.Set;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class SkillGroup implements Serializable {

    /** identifier field */
    private Integer id;

    /** persistent field */
    private String name;

    /** persistent field */
    private Set skills;

    /** full constructor */
    public SkillGroup(Integer id, String name, Set skills) {
        this.id = id;
        this.name = name;
        this.skills = skills;
    }

    /** default constructor */
    public SkillGroup() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set getSkills() {
        return this.skills;
    }

    public void setSkills(Set skills) {
        this.skills = skills;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

}
