package de.mbws.client.data;

/**
 * Description:
 * 
 * @author Azarai
 */
public abstract class AbstractDataObject {

    private int id;

    private String name;

    private String description;

    /**
     * 
     */
    public AbstractDataObject(int id) {
        super();
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
