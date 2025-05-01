package inf112.skeleton.controller;

import com.badlogic.gdx.Input;

/**
 * Enum representing custom key mappings for the game.
 * Each key can have one or more associated key codes from the {@link Input.Keys} class.
 */
public enum Keys {
    // Menu keys
    BACK (Input.Keys.BACKSPACE),

    // Movement keys
    UP (Input.Keys.W, Input.Keys.UP),
    DOWN (Input.Keys.S, Input.Keys.DOWN),
    LEFT (Input.Keys.A, Input.Keys.LEFT), 
    RIGHT (Input.Keys.D, Input.Keys.RIGHT), 
    QUIT (Input.Keys.ESCAPE),

    // Player interaction keys
    INTERACT (Input.Keys.E, Input.Keys.ENTER),
    ATTACK (Input.Keys.F),
    
    // Item keys
    NUM_1 (Input.Keys.NUM_1),
    NUM_2 (Input.Keys.NUM_2),
    NUM_3 (Input.Keys.NUM_3),
    NUM_4 (Input.Keys.NUM_4),
    USE_ITEM (Input.Keys.Q);

    /**
     * Array of key codes associated with this key.
     */
    final int[] keyCode;

    /**
     * Constructs a Keys enum value with one or more associated key codes.
     *
     * @param keyCode The key codes associated with this key.
     */
    Keys(int... keyCode) {
        this.keyCode = keyCode;
    }

    /**
     * Gets the key codes associated with this key.
     *
     * @return An array of key codes.
     */
    public int[] getKeyCode() {
        return keyCode;
    }
}
