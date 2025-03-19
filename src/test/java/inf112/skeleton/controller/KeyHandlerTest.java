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
        assertTrue(keyHandler.isKeyPressed(GameKeys.UP) == false, "UP key should not be pressed initially");
        assertTrue(keyHandler.isKeyPressed(GameKeys.DOWN) == false, "DOWN key should not be pressed initially");
    }

    @Test
    void testKeyDown() {
        keyHandler.keyDown(Input.Keys.W); // Simulate pressing the UP key
        assertTrue(keyHandler.isKeyPressed(GameKeys.UP), "UP key should be pressed after keyDown");
    }

    @Test
    void testKeyUp() {
        keyHandler.keyDown(Input.Keys.W); // Simulate pressing the UP key
        keyHandler.keyUp(Input.Keys.W);  // Simulate releasing the UP key
        assertFalse(keyHandler.isKeyPressed(GameKeys.UP), "UP key should not be pressed after keyUp");
    }
}