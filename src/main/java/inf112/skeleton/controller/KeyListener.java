package inf112.skeleton.controller;

/**
 * Interface for listening to key events.
 * Classes implementing this interface can respond to key press and release events.
 */
public interface KeyListener {

    /**
     * Called when a key is pressed.
     *
     * @param keyHandler The KeyHandler managing the key events.
     * @param key        The key that was pressed.
     */
    void keyPressed(KeyHandler keyHandler, Keys key);

    /**
     * Called when a key is released.
     *
     * @param keyHandler The KeyHandler managing the key events.
     * @param key        The key that was released.
     */
    void keyReleased(KeyHandler keyHandler, Keys key);

}
