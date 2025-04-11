package inf112.skeleton.controller;

import com.badlogic.gdx.Input;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GameKeysTest {
    @Test
    void testKeyEnumValues() {
        // Test that all enum values exist
        assertNotNull(GameKeys.PAUSE);
        assertNotNull(GameKeys.BACK);
        assertNotNull(GameKeys.UP);
        assertNotNull(GameKeys.DOWN);
        assertNotNull(GameKeys.LEFT);
        assertNotNull(GameKeys.RIGHT);
        assertNotNull(GameKeys.INTERACT);
    }

    @Test
    void testGetKeyCode() {
        // Test that key codes are correctly set
        assertArrayEquals(new int[]{Input.Keys.ESCAPE}, GameKeys.PAUSE.getKeyCode());
        assertArrayEquals(new int[]{Input.Keys.BACKSPACE}, GameKeys.BACK.getKeyCode());
        assertArrayEquals(new int[]{Input.Keys.W, Input.Keys.UP}, GameKeys.UP.getKeyCode());
        assertArrayEquals(new int[]{Input.Keys.S, Input.Keys.DOWN}, GameKeys.DOWN.getKeyCode());
        assertArrayEquals(new int[]{Input.Keys.A, Input.Keys.LEFT}, GameKeys.LEFT.getKeyCode());
        assertArrayEquals(new int[]{Input.Keys.D, Input.Keys.RIGHT}, GameKeys.RIGHT.getKeyCode());
        assertArrayEquals(new int[]{Input.Keys.E, Input.Keys.ENTER}, GameKeys.INTERACT.getKeyCode());
    }
} 