package inf112.skeleton.model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

import inf112.skeleton.audio.AudioHandler;
import inf112.skeleton.controller.KeyHandler;
import inf112.skeleton.model.map.MapManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GamePanelTest {
    
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
    }

    @Test
    void testPublicConstants() {
        // Test public constants
        assertEquals(1, GamePanel.BIT_Player);
        assertEquals(2, GamePanel.BIT_Box);
        assertEquals(4, GamePanel.BIT_GROUND);
        assertEquals(1/32f, GamePanel.UNIT_SCALE);
    }

    @Test
    void testStaticFields() {
        // Test that static fields are initialized
        assertNotNull(GamePanel.BODY_DEF);
        assertNotNull(GamePanel.FIXTURE_DEF);
    }
}
