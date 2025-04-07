package inf112.skeleton.model.entity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;

import inf112.skeleton.audio.AudioHandler;
import inf112.skeleton.audio.AudioTypes;
import inf112.skeleton.controller.KeyHandler;
import inf112.skeleton.controller.Keys;
import inf112.skeleton.model.GamePanel;

public class PlayerTest {
    private Player player;
    private World mockWorld;
    private Body mockBody;
    private GamePanel mockGamePanel;
    private KeyHandler mockKeyHandler;
    private AudioHandler mockAudioHandler;
    private Application mockApplication;
    private SpriteBatch mockSpriteBatch;
    private Texture mockTexture;

    @BeforeEach
    public void setUp() {
        // Create mocks
        mockWorld = mock(World.class);
        mockBody = mock(Body.class);
        mockGamePanel = mock(GamePanel.class);
        mockKeyHandler = mock(KeyHandler.class);
        mockAudioHandler = mock(AudioHandler.class);
        mockApplication = mock(Application.class);
        mockSpriteBatch = mock(SpriteBatch.class);
        mockTexture = mock(Texture.class);

        // Mock Gdx.app
        Gdx.app = mockApplication;

        // Mock GamePanel
        when(mockGamePanel.getAudioHandler()).thenReturn(mockAudioHandler);

        // Mock Body
        when(mockBody.getMass()).thenReturn(1.0f);
        when(mockBody.getLinearVelocity()).thenReturn(new com.badlogic.gdx.math.Vector2(0, 0));
        when(mockBody.getWorldCenter()).thenReturn(new com.badlogic.gdx.math.Vector2(0, 0));

        // Create Player instance
        player = new Player(mockGamePanel, mockWorld, mockBody, 100, 10, 0, 0);
    }

    @Test
    public void testTakeDamage() {
        assertTrue(player.takeDamage(50));
        assertEquals(50, player.getHealth());

        assertFalse(player.takeDamage(50));
        assertEquals(0, player.getHealth());
    }

    @Test
    public void testSetHealth() {
        player.setHealth(200);
        assertEquals(200, player.getHealth());
    }

    @Test
    public void testSetSpawn() {
        player.setSpawn(5, 10);
        assertEquals(5, player.getX());
        assertEquals(10, player.getY());
    }

    @Test
    public void testAttack() {
        int damage = player.attack();
        assertEquals(10, damage, "Attack damage should match the initialized value");
    }

    @Test
    public void testDie() {
        assertThrows(UnsupportedOperationException.class, () -> {
            player.die();
        }, "Die method should throw UnsupportedOperationException");
    }

    @Test
    public void testCreate() {
        player.create(150, 20, 10, 15);
        assertEquals(150, player.getHealth(), "Health should be set correctly");
        assertEquals(10, player.getX(), "X position should be set correctly");
        assertEquals(15, player.getY(), "Y position should be set correctly");
    }

    @Test
    public void testGetBody() {
        Body playerBody = player.getBody();
        assertSame(mockBody, playerBody, "Body should be the same as the mock");
    }

    @Test
    public void testPlayerInput() {
        // Test LEFT key
        player.playerInput(mockKeyHandler, Keys.LEFT);
        player.movePlayer();
        verify(mockBody, times(1)).applyLinearImpulse(anyFloat(), anyFloat(), anyFloat(), anyFloat(), eq(true));

        // Test RIGHT key
        player.playerInput(mockKeyHandler, Keys.RIGHT);
        player.movePlayer();
        verify(mockBody, times(2)).applyLinearImpulse(anyFloat(), anyFloat(), anyFloat(), anyFloat(), eq(true));

        // Test UP key
        player.playerInput(mockKeyHandler, Keys.UP);
        player.movePlayer();
        verify(mockBody, times(3)).applyLinearImpulse(anyFloat(), anyFloat(), anyFloat(), anyFloat(), eq(true));

        // Test DOWN key
        player.playerInput(mockKeyHandler, Keys.DOWN);
        player.movePlayer();
        verify(mockBody, times(4)).applyLinearImpulse(anyFloat(), anyFloat(), anyFloat(), anyFloat(), eq(true));
    }

    @Test
    public void testPlayerTakeDamage() {
        when(mockKeyHandler.isKeyPressed(Keys.INTERACT)).thenReturn(true);
        player.playerTakeDamage(mockKeyHandler, Keys.INTERACT);
        assertEquals(90, player.getHealth(), "Health should be reduced by 10");
        verify(mockAudioHandler).playAudio(AudioTypes.HURT);
    }

    @Test
    public void testMovePlayerReleased() {
        // Test releasing LEFT key while RIGHT is still pressed
        when(mockKeyHandler.isKeyPressed(Keys.RIGHT)).thenReturn(true);
        player.playerInput(mockKeyHandler, Keys.RIGHT);
        player.movePlayerReleased(mockKeyHandler, Keys.LEFT);
        player.movePlayer();
        verify(mockBody, times(1)).applyLinearImpulse(anyFloat(), anyFloat(), anyFloat(), anyFloat(), eq(true));

        // Test releasing RIGHT key while LEFT is still pressed
        when(mockKeyHandler.isKeyPressed(Keys.LEFT)).thenReturn(true);
        player.playerInput(mockKeyHandler, Keys.LEFT);
        player.movePlayerReleased(mockKeyHandler, Keys.RIGHT);
        player.movePlayer();
        verify(mockBody, times(2)).applyLinearImpulse(anyFloat(), anyFloat(), anyFloat(), anyFloat(), eq(true));

        // Test releasing UP key while DOWN is still pressed
        when(mockKeyHandler.isKeyPressed(Keys.DOWN)).thenReturn(true);
        player.playerInput(mockKeyHandler, Keys.DOWN);
        player.movePlayerReleased(mockKeyHandler, Keys.UP);
        player.movePlayer();
        verify(mockBody, times(3)).applyLinearImpulse(anyFloat(), anyFloat(), anyFloat(), anyFloat(), eq(true));

        // Test releasing DOWN key while UP is still pressed
        when(mockKeyHandler.isKeyPressed(Keys.UP)).thenReturn(true);
        player.playerInput(mockKeyHandler, Keys.UP);
        player.movePlayerReleased(mockKeyHandler, Keys.DOWN);
        player.movePlayer();
        verify(mockBody, times(4)).applyLinearImpulse(anyFloat(), anyFloat(), anyFloat(), anyFloat(), eq(true));
    }
} 