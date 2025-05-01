package inf112.skeleton.model.entity.player;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import inf112.skeleton.controller.KeyHandler;
import inf112.skeleton.controller.Keys;
import inf112.skeleton.model.GamePanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class PlayerMovementTest {
    private PlayerMovement playerMovement;
    
    @Mock private GamePanel gamePanel;
    @Mock private World world;
    @Mock private Body body;
    @Mock private KeyHandler keyHandler;
    
    private Vector2 worldCenter;
    private Vector2 velocity;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // Setup real Vector2 instances
        worldCenter = new Vector2(0f, 0f);
        velocity = new Vector2(0f, 0f);
        
        // Setup mocks
        when(gamePanel.getKeyHandler()).thenReturn(keyHandler);
        when(body.getWorldCenter()).thenReturn(worldCenter);
        when(body.getLinearVelocity()).thenReturn(velocity);
        when(body.getMass()).thenReturn(1f);
        
        playerMovement = new PlayerMovement(gamePanel, world, body);
    }
    
    @Test
    void testHandleInputLeft() {
        playerMovement.handleInput(Keys.LEFT);
        assertEquals("Left", playerMovement.getDirection());
        assertTrue(playerMovement.isMoving());
        
        playerMovement.update();
        verify(body).applyLinearImpulse(
            floatThat(x -> Math.abs(x - (-5.0f)) < 0.0001f),
            floatThat(y -> Math.abs(y - 0f) < 0.0001f),
            eq(0f),
            eq(0f),
            eq(true)
        );
    }
    
    @Test
    void testHandleInputRight() {
        playerMovement.handleInput(Keys.RIGHT);
        assertEquals("Right", playerMovement.getDirection());
        assertTrue(playerMovement.isMoving());
        
        playerMovement.update();
        verify(body).applyLinearImpulse(
            floatThat(x -> Math.abs(x - 5.0f) < 0.0001f),
            floatThat(y -> Math.abs(y - 0f) < 0.0001f),
            eq(0f),
            eq(0f),
            eq(true)
        );
    }
    
    @Test
    void testHandleInputUp() {
        playerMovement.handleInput(Keys.UP);
        assertEquals("Up", playerMovement.getDirection());
        assertTrue(playerMovement.isMoving());
        
        playerMovement.update();
        verify(body).applyLinearImpulse(
            floatThat(x -> Math.abs(x - 0f) < 0.0001f),
            floatThat(y -> Math.abs(y - 5.0f) < 0.0001f),
            eq(0f),
            eq(0f),
            eq(true)
        );
    }
    
    @Test
    void testHandleInputDown() {
        playerMovement.handleInput(Keys.DOWN);
        assertEquals("Down", playerMovement.getDirection());
        assertTrue(playerMovement.isMoving());
        
        playerMovement.update();
        verify(body).applyLinearImpulse(
            floatThat(x -> Math.abs(x - 0f) < 0.0001f),
            floatThat(y -> Math.abs(y - (-5.0f)) < 0.0001f),
            eq(0f),
            eq(0f),
            eq(true)
        );
    }
    
    @Test
    void testSpeedNormalization() {
        // Press UP and then RIGHT
        playerMovement.handleInput(Keys.UP);
        playerMovement.handleInput(Keys.RIGHT);
        
        // Direction should be RIGHT since it was the last key pressed
        assertEquals("Right", playerMovement.getDirection());
        assertTrue(playerMovement.isMoving());
        
        // Verify the impulse is applied with normalized values
        playerMovement.update();
        verify(body).applyLinearImpulse(
            floatThat(x -> Math.abs(x - 3.8411064f) < 0.0001f),
            floatThat(y -> Math.abs(y - 3.200922f) < 0.0001f),
            eq(0f),
            eq(0f),
            eq(true)
        );
    }
    
    @Test
    void testMovementState() {
        assertFalse(playerMovement.isMoving());
        
        playerMovement.handleInput(Keys.UP);
        assertTrue(playerMovement.isMoving());
        
        playerMovement.handleInputRelease(Keys.UP);
        assertFalse(playerMovement.isMoving());
    }
    
    @Test
    void testGetDirection() {
        assertEquals("Down", playerMovement.getDirection());
        
        playerMovement.handleInput(Keys.UP);
        assertEquals("Up", playerMovement.getDirection());
        
        playerMovement.handleInput(Keys.LEFT);
        assertEquals("Left", playerMovement.getDirection());
        
        playerMovement.handleInput(Keys.RIGHT);
        assertEquals("Right", playerMovement.getDirection());
        
        playerMovement.handleInput(Keys.DOWN);
        assertEquals("Down", playerMovement.getDirection());
    }
    
    @Test
    void testMovementAcceleration() {
        playerMovement.handleInput(Keys.RIGHT);
        playerMovement.update();
        
        verify(body).applyLinearImpulse(
            floatThat(x -> Math.abs(x - 5.0f) < 0.0001f),
            floatThat(y -> Math.abs(y - 0f) < 0.0001f),
            eq(0f),
            eq(0f),
            eq(true)
        );
    }
    
    @Test
    void testNoMovementWhenStopped() {
        playerMovement.handleInput(Keys.UP);
        playerMovement.handleInputRelease(Keys.UP);
        
        // When stopped, a zero impulse is applied due to directionChange being true
        playerMovement.update();
        verify(body).applyLinearImpulse(
            floatThat(x -> Math.abs(x - 0f) < 0.0001f),
            floatThat(y -> Math.abs(y - 0f) < 0.0001f),
            eq(0f),
            eq(0f),
            eq(true)
        );
    }
    
    @Test
    void testMultipleDirectionsMovement() {
        playerMovement.handleInput(Keys.UP);
        playerMovement.handleInput(Keys.RIGHT);
        assertTrue(playerMovement.isMoving());
        assertEquals("Right", playerMovement.getDirection());
    }
    
    @Test
    void testReleaseStopsMovement() {
        playerMovement.handleInput(Keys.UP);
        assertTrue(playerMovement.isMoving());
        
        playerMovement.handleInputRelease(Keys.UP);
        assertFalse(playerMovement.isMoving());
    }
} 