package inf112.skeleton.model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

import inf112.skeleton.model.GamePanel;
import inf112.skeleton.audio.AudioHandler;
import inf112.skeleton.controller.KeyHandler;
import inf112.skeleton.model.map.MapManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

class GamePanelTest {
    
    private GamePanel gamePanel;
    private Screen mockScreen;
    private World mockWorld;
    private Graphics mockGraphics;
    private Application mockApplication;
    private FitViewport mockViewport;
    private Box2DDebugRenderer mockBox2DDebugRenderer;
    private KeyHandler mockKeyHandler;
    private AudioHandler mockAudioHandler;
    private MapManager mockMapManager;
    
    @BeforeEach
    void setUp() {
        // Create mocks
        mockScreen = mock(Screen.class);
        mockWorld = mock(World.class);
        mockGraphics = mock(Graphics.class);
        mockApplication = mock(Application.class);
        mockViewport = mock(FitViewport.class);
        mockBox2DDebugRenderer = mock(Box2DDebugRenderer.class);
        mockKeyHandler = mock(KeyHandler.class);
        mockAudioHandler = mock(AudioHandler.class);
        mockMapManager = mock(MapManager.class);
        
        // Mock LibGDX static fields
        Gdx.graphics = mockGraphics;
        Gdx.app = mockApplication;
        when(mockGraphics.getWidth()).thenReturn(960);
        when(mockGraphics.getHeight()).thenReturn(540);
        
        // Create GamePanel instance
        gamePanel = new GamePanel();
    }

    @AfterEach
    void tearDown() {
        // Reset the singleton instance
        try {
            java.lang.reflect.Field instanceField = GamePanel.class.getDeclaredField("instance");
            instanceField.setAccessible(true);
            instanceField.set(null, null);
        } catch (Exception e) {
            fail("Failed to reset GamePanel instance: " + e.getMessage());
        }
    }
    
    @Test
    void testInitialization() {
        assertNotNull(gamePanel, "GamePanel should be initialized");
    }
    
    @Test
    void testResetBodyAndFixtureDefinition() {
        GamePanel.resetBodyAndFixtureDefinition();
        assertEquals(0, GamePanel.BODY_DEF.position.x, "BodyDef position x should be reset to 0");
        assertEquals(0, GamePanel.BODY_DEF.position.y, "BodyDef position y should be reset to 0");
        assertEquals(0, GamePanel.BODY_DEF.gravityScale, "BodyDef gravity scale should be reset to 0");
        assertEquals(BodyDef.BodyType.StaticBody, GamePanel.BODY_DEF.type, "BodyDef type should be StaticBody");
        
        assertFalse(GamePanel.FIXTURE_DEF.isSensor, "FixtureDef isSensor should be false");
        assertEquals(0.75f, GamePanel.FIXTURE_DEF.restitution, "FixtureDef restitution should be 0.75f");
        assertEquals(0.2f, GamePanel.FIXTURE_DEF.friction, "FixtureDef friction should be 0.2f");
        assertEquals(GamePanel.BIT_GROUND, GamePanel.FIXTURE_DEF.filter.categoryBits, "FixtureDef category bits should be BIT_GROUND");
        assertEquals(-1, GamePanel.FIXTURE_DEF.filter.maskBits, "FixtureDef mask bits should be -1");
    }
}
