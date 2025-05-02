package inf112.skeleton.model.map;

/**
 * Interface for objects that need to be notified when the game map changes.
 * Implement this interface to receive notifications about map transitions.
 */
public interface MapListener {
    /**
     * Called when the game map changes to a new map.
     * Implement this method to handle map transition events.
     *
     * @param map The new map that has been loaded
     */
    void mapChanged(final Map map);
}
