package de.mbws.common.data.db;

import java.io.Serializable;
import java.util.Set;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class Item implements Serializable {

    /** identifier field */
    private Long id;

    /** persistent field */
    private String name;

    /** persistent field */
    private int maxCondition;

    /** persistent field */
    private long weight;

    /** persistent field */
    private long size;

    /** persistent field */
    private String description;

    /** nullable persistent field */
    private String modelName;

    /** nullable persistent field */
    private String iconName;

    /** persistent field */
    private de.mbws.common.data.db.ItemType itemType;

    /** persistent field */
    private Set characterItemMappings;

    /** full constructor */
    public Item(Long id, String name, int maxCondition, long weight, long size, String description, String modelName, String iconName, de.mbws.common.data.db.ItemType itemType, Set characterItemMappings) {
        this.id = id;
        this.name = name;
        this.maxCondition = maxCondition;
        this.weight = weight;
        this.size = size;
        this.description = description;
        this.modelName = modelName;
        this.iconName = iconName;
        this.itemType = itemType;
        this.characterItemMappings = characterItemMappings;
    }

    /** default constructor */
    public Item() {
    }

    /** minimal constructor */
    public Item(Long id, String name, int maxCondition, long weight, long size, String description, de.mbws.common.data.db.ItemType itemType, Set characterItemMappings) {
        this.id = id;
        this.name = name;
        this.maxCondition = maxCondition;
        this.weight = weight;
        this.size = size;
        this.description = description;
        this.itemType = itemType;
        this.characterItemMappings = characterItemMappings;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxCondition() {
        return this.maxCondition;
    }

    public void setMaxCondition(int maxCondition) {
        this.maxCondition = maxCondition;
    }

    public long getWeight() {
        return this.weight;
    }

    public void setWeight(long weight) {
        this.weight = weight;
    }

    public long getSize() {
        return this.size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getModelName() {
        return this.modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getIconName() {
        return this.iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }

    public de.mbws.common.data.db.ItemType getItemType() {
        return this.itemType;
    }

    public void setItemType(de.mbws.common.data.db.ItemType itemType) {
        this.itemType = itemType;
    }

    public Set getCharacterItemMappings() {
        return this.characterItemMappings;
    }

    public void setCharacterItemMappings(Set characterItemMappings) {
        this.characterItemMappings = characterItemMappings;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

}
