package de.mbws.common.data.generated;

import java.io.Serializable;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class Account implements Serializable {

    /** identifier field */
    private String username;

    /** persistent field */
    private String password;

    /** persistent field */
    private String emailaddress;

    /** persistent field */
    private Set characterdatas;

    /** full constructor */
    public Account(String username, String password, String emailaddress, Set characterdatas) {
        this.username = username;
        this.password = password;
        this.emailaddress = emailaddress;
        this.characterdatas = characterdatas;
    }

    /** default constructor */
    public Account() {
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmailaddress() {
        return this.emailaddress;
    }

    public void setEmailaddress(String emailaddress) {
        this.emailaddress = emailaddress;
    }

    public Set getCharacterdatas() {
        return this.characterdatas;
    }

    public void setCharacterdatas(Set characterdatas) {
        this.characterdatas = characterdatas;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("username", getUsername())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Account) ) return false;
        Account castOther = (Account) other;
        return new EqualsBuilder()
            .append(this.getUsername(), castOther.getUsername())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getUsername())
            .toHashCode();
    }

}
