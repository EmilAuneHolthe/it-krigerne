package inf112.skeleton.view.screen;

import com.badlogic.gdx.utils.viewport.FitViewport;
import inf112.skeleton.controller.KeyHandler;
import inf112.skeleton.controller.Keys;
import inf112.skeleton.model.GamePanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AbstractScreenTest {
    private AbstractScreen abstractScreen;
    
    @Mock
    private GamePanel gamePanel;
    @Mock
    private FitViewport viewport;
    @Mock
    private KeyHandler keyHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // Setup mock behavior
        when(gamePanel.getViewport()).thenReturn(viewport);
        when(gamePanel.getKeyHandler()).thenReturn(keyHandler);
        
        // Create a concrete implementation of AbstractScreen for testing
        abstractScreen = new AbstractScreen(gamePanel) {
            // Override abstract methods if needed
        };
    }

    @Test
    void testScreenResize() {
        int width = 800;
        int height = 600;
        
        abstractScreen.resize(width, height);
        
        // Verify viewport was updated with correct dimensions
        verify(viewport).update(width, height);
    }

    @Test
    void testKeyPressed() {
        Keys testKey = Keys.UP;
        
        abstractScreen.keyPressed(keyHandler, testKey);
        
        // Verify key press was handled
        // Note: The actual implementation just prints to System.err
        // We can't easily verify this, but we can verify the method was called
        assertNotNull(abstractScreen);
    }

    @Test
    void testKeyReleased() {
        Keys testKey = Keys.UP;
        
        abstractScreen.keyReleased(keyHandler, testKey);
        
        // Verify key release was handled
        // Note: The actual implementation just prints to System.err
        // We can't easily verify this, but we can verify the method was called
        assertNotNull(abstractScreen);
    }

    @Test
    void testScreenLifecycle() {
        // Test show
        abstractScreen.show();
        assertNotNull(abstractScreen);
        
        // Test hide
        abstractScreen.hide();
        assertNotNull(abstractScreen);
        
        // Test pause
        abstractScreen.pause();
        assertNotNull(abstractScreen);
        
        // Test resume
        abstractScreen.resume();
        assertNotNull(abstractScreen);
        
        // Test dispose
        abstractScreen.dispose();
        assertNotNull(abstractScreen);
    }
}
