package de.mbws.common.data.db.generated;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class CharacterItemMapping implements Serializable {

    /** identifier field */
    private Long id;

    /** persistent field */
    private String position;

    /** persistent field */
    private int currentcondition;

    /** persistent field */
    private int depositItem;

    /** persistent field */
    private de.mbws.common.data.db.generated.Item item;

    /** persistent field */
    private de.mbws.common.data.db.generated.Characterdata characterdata;

    /** full constructor */
    public CharacterItemMapping(Long id, String position, int currentcondition, int depositItem, de.mbws.common.data.db.generated.Item item, de.mbws.common.data.db.generated.Characterdata characterdata) {
        this.id = id;
        this.position = position;
        this.currentcondition = currentcondition;
        this.depositItem = depositItem;
        this.item = item;
        this.characterdata = characterdata;
    }

    /** default constructor */
    public CharacterItemMapping() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPosition() {
        return this.position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getCurrentcondition() {
        return this.currentcondition;
    }

    public void setCurrentcondition(int currentcondition) {
        this.currentcondition = currentcondition;
    }

    public int getDepositItem() {
        return this.depositItem;
    }

    public void setDepositItem(int depositItem) {
        this.depositItem = depositItem;
    }

    public de.mbws.common.data.db.generated.Item getItem() {
        return this.item;
    }

    public void setItem(de.mbws.common.data.db.generated.Item item) {
        this.item = item;
    }

    public de.mbws.common.data.db.generated.Characterdata getCharacterdata() {
        return this.characterdata;
    }

    public void setCharacterdata(de.mbws.common.data.db.generated.Characterdata characterdata) {
        this.characterdata = characterdata;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

}
