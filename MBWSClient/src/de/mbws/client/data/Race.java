package de.mbws.client.data;

/**
 * Description: 
 * @author Azarai
 *
 */
public class Race extends AbstractDataObject {

    private boolean playable = false;
    /**
     * @param id
     */
    public Race(int id) {
        super(id);

    }
    public boolean isPlayable() {
        return playable;
    }
    public void setPlayable(boolean playable) {
        this.playable = playable;
    }

}