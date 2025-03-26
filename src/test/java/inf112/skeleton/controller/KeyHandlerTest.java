package inf112.skeleton.controller;

import com.badlogic.gdx.Input;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class KeyHandlerTest {

    private KeyHandler keyHandler;

    @BeforeEach
    void setUp() {
        keyHandler = new KeyHandler();
    }

    @Test
    void testKeyMappingInitialization() {
        assertTrue(keyHandler.isKeyPressed(Keys.UP) == false, "UP key should not be pressed initially");
        assertTrue(keyHandler.isKeyPressed(Keys.DOWN) == false, "DOWN key should not be pressed initially");
    }

    @Test
    void testKeyDown() {
        keyHandler.keyDown(Input.Keys.W); // Simulate pressing the UP key
        assertTrue(keyHandler.isKeyPressed(Keys.UP), "UP key should be pressed after keyDown");
    }

    @Test
    void testKeyUp() {
        keyHandler.keyDown(Input.Keys.W); // Simulate pressing the UP key
        keyHandler.keyUp(Input.Keys.W);  // Simulate releasing the UP key
        assertFalse(keyHandler.isKeyPressed(Keys.UP), "UP key should not be pressed after keyUp");
    }

    @Test
    void testMultipleKeysDown() {
        keyHandler.keyDown(Input.Keys.W); // UP
        keyHandler.keyDown(Input.Keys.S); // DOWN
        
        assertTrue(keyHandler.isKeyPressed(Keys.UP), "UP key should be pressed");
        assertTrue(keyHandler.isKeyPressed(Keys.DOWN), "DOWN key should be pressed");
    }

    @Test
    void testMultipleKeysUp() {
        keyHandler.keyDown(Input.Keys.W);
        keyHandler.keyDown(Input.Keys.S);
        keyHandler.keyUp(Input.Keys.W);
        
        assertFalse(keyHandler.isKeyPressed(Keys.UP), "UP key should not be pressed");
        assertTrue(keyHandler.isKeyPressed(Keys.DOWN), "DOWN key should still be pressed");
    }

    @Test
    void testUnknownKeyIgnored() {
        keyHandler.keyDown(Input.Keys.F1); // Simulate pressing an unknown key
        assertFalse(keyHandler.isKeyPressed(Keys.UP), "UP key should not be affected by F1");
        assertFalse(keyHandler.isKeyPressed(Keys.DOWN), "DOWN key should not be affected by F1");
    }
}
