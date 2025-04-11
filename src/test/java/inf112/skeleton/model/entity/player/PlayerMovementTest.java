package inf112.skeleton.model.entity.player;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import inf112.skeleton.BaseTest;
import inf112.skeleton.controller.KeyHandler;
import inf112.skeleton.controller.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class PlayerMovementTest extends BaseTest {
    private PlayerMovement playerMovement;
    
    @Mock
    private World mockWorld;
    @Mock
    private Body mockBody;
    @Mock
    private KeyHandler mockKeyHandler;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // Setup mock body
        when(mockBody.getWorldCenter()).thenReturn(new Vector2(10, 10));
        when(mockBody.getMass()).thenReturn(1.0f);
        when(mockBody.getLinearVelocity()).thenReturn(new Vector2(0, 0));
        
        playerMovement = new PlayerMovement(mockWorld, mockBody, mockKeyHandler);
    }
    
    @Test
    void testInitialState() {
        assertEquals("Down", playerMovement.getDirection());
        assertFalse(playerMovement.isMoving());
    }
    
    @Test
    void testHandleInputLeft() {
        playerMovement.handleInput(Keys.LEFT);
        playerMovement.update();
        
        assertEquals("Left", playerMovement.getDirection());
        assertTrue(playerMovement.isMoving());
        
        // Verify body impulse was applied
        verify(mockBody).applyLinearImpulse(
            anyFloat(), // x impulse
            anyFloat(), // y impulse
            eq(10f),   // world center x
            eq(10f),   // world center y
            eq(true)   // wake
        );
    }
    
    @Test
    void testHandleInputRight() {
        playerMovement.handleInput(Keys.RIGHT);
        playerMovement.update();
        
        assertEquals("Right", playerMovement.getDirection());
        assertTrue(playerMovement.isMoving());
        
        verify(mockBody).applyLinearImpulse(
            anyFloat(),
            anyFloat(),
            eq(10f),
            eq(10f),
            eq(true)
        );
    }
    
    @Test
    void testHandleInputUp() {
        playerMovement.handleInput(Keys.UP);
        playerMovement.update();
        
        assertEquals("Up", playerMovement.getDirection());
        assertTrue(playerMovement.isMoving());
        
        verify(mockBody).applyLinearImpulse(
            anyFloat(),
            anyFloat(),
            eq(10f),
            eq(10f),
            eq(true)
        );
    }
    
    @Test
    void testHandleInputDown() {
        playerMovement.handleInput(Keys.DOWN);
        playerMovement.update();
        
        assertEquals("Down", playerMovement.getDirection());
        assertTrue(playerMovement.isMoving());
        
        verify(mockBody).applyLinearImpulse(
            anyFloat(),
            anyFloat(),
            eq(10f),
            eq(10f),
            eq(true)
        );
    }
    
    @Test
    void testHandleInputReleaseWithNoOtherKeysPressed() {
        // First move right
        playerMovement.handleInput(Keys.RIGHT);
        playerMovement.update();
        
        // Then release right key with no other keys pressed
        when(mockKeyHandler.isKeyPressed(any(Keys.class))).thenReturn(false);
        playerMovement.handleInputRelease(Keys.RIGHT);
        
        assertFalse(playerMovement.isMoving());
    }
    
    @Test
    void testHandleInputReleaseWithOtherKeyPressed() {
        // First move right
        playerMovement.handleInput(Keys.RIGHT);
        playerMovement.update();
        
        // When releasing right key, simulate left key being pressed
        when(mockKeyHandler.isKeyPressed(Keys.LEFT)).thenReturn(true);
        playerMovement.handleInputRelease(Keys.RIGHT);
        playerMovement.update();
        
        assertTrue(playerMovement.isMoving());
        assertEquals("Left", playerMovement.getDirection());
        
        // Verify impulse was applied twice (once for initial right movement, once for left movement)
        verify(mockBody, times(2)).applyLinearImpulse(
            anyFloat(),
            anyFloat(),
            eq(10f),
            eq(10f),
            eq(true)
        );
    }
    
    @Test
    void testSetDirection() {
        playerMovement.setDirection("Up");
        assertEquals("Up", playerMovement.getDirection());
        
        playerMovement.setDirection("Down");
        assertEquals("Down", playerMovement.getDirection());
    }
    
    @Test
    void testUpdate() {
        // Move right
        playerMovement.handleInput(Keys.RIGHT);
        
        // Update should apply impulse
        playerMovement.update();
        
        // Verify impulse was applied once
        verify(mockBody).applyLinearImpulse(
            anyFloat(),
            anyFloat(),
            eq(10f),
            eq(10f),
            eq(true)
        );
    }
    
    @Test
    void testUpdateWithNoMovement() {
        // No movement input
        playerMovement.update();
        
        // Verify no impulse was applied
        verify(mockBody, never()).applyLinearImpulse(
            anyFloat(),
            anyFloat(),
            anyFloat(),
            anyFloat(),
            anyBoolean()
        );
    }
} 