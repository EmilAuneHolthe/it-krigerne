package inf112.skeleton.controller;

import com.badlogic.gdx.Input;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class KeysTest {
    @Test
    void testKeyEnumValues() {
        // Test that all enum values exist
        assertNotNull(Keys.BACK);
        assertNotNull(Keys.UP);
        assertNotNull(Keys.DOWN);
        assertNotNull(Keys.LEFT);
        assertNotNull(Keys.RIGHT);
        assertNotNull(Keys.INTERACT);
        assertNotNull(Keys.ATTACK);
    }

    @Test
    void testGetKeyCode() {
        // Test that key codes are correctly set
        assertArrayEquals(new int[]{Input.Keys.BACKSPACE}, Keys.BACK.getKeyCode());
        assertArrayEquals(new int[]{Input.Keys.W, Input.Keys.UP}, Keys.UP.getKeyCode());
        assertArrayEquals(new int[]{Input.Keys.S, Input.Keys.DOWN}, Keys.DOWN.getKeyCode());
        assertArrayEquals(new int[]{Input.Keys.A, Input.Keys.LEFT}, Keys.LEFT.getKeyCode());
        assertArrayEquals(new int[]{Input.Keys.D, Input.Keys.RIGHT}, Keys.RIGHT.getKeyCode());
        assertArrayEquals(new int[]{Input.Keys.E, Input.Keys.ENTER}, Keys.INTERACT.getKeyCode());
        assertArrayEquals(new int[]{Input.Keys.F}, Keys.ATTACK.getKeyCode());
    }
} 