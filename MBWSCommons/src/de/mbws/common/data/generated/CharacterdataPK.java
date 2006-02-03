package de.mbws.common.data.generated;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class CharacterdataPK implements Serializable {

    /** identifier field */
    private Long id;

    /** identifier field */
    private Integer raceId;

    /** identifier field */
    private String accountUsername;

    /** full constructor */
    public CharacterdataPK(Long id, Integer raceId, String accountUsername) {
        this.id = id;
        this.raceId = raceId;
        this.accountUsername = accountUsername;
    }

    /** default constructor */
    public CharacterdataPK() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRaceId() {
        return this.raceId;
    }

    public void setRaceId(Integer raceId) {
        this.raceId = raceId;
    }

    public String getAccountUsername() {
        return this.accountUsername;
    }

    public void setAccountUsername(String accountUsername) {
        this.accountUsername = accountUsername;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .append("raceId", getRaceId())
            .append("accountUsername", getAccountUsername())
            .toString();
    }

    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof CharacterdataPK) ) return false;
        CharacterdataPK castOther = (CharacterdataPK) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .append(this.getRaceId(), castOther.getRaceId())
            .append(this.getAccountUsername(), castOther.getAccountUsername())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .append(getRaceId())
            .append(getAccountUsername())
            .toHashCode();
    }

}
