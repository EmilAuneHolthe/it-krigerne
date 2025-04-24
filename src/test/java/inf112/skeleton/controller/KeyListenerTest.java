package inf112.skeleton.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.*;

public class KeyListenerTest {
    @Mock
    private KeyHandler keyHandler;
    
    @Test
    void testKeyListenerImplementation() {
        // Create a mock implementation of KeyListener
        KeyListener listener = mock(KeyListener.class);
        
        // Test keyPressed
        listener.keyPressed(keyHandler, Keys.UP);
        verify(listener).keyPressed(keyHandler, Keys.UP);
        
        // Test keyReleased
        listener.keyReleased(keyHandler, Keys.UP);
        verify(listener).keyReleased(keyHandler, Keys.UP);
    }
} 