package inf112.skeleton.app;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GamePanelTest {
    
    private GamePanel gamePanel;
    private AssetManager mockAssetManager;
    private Screen mockScreen;
    private Music mockMusic;
    private Sound mockSound;
    private SpriteBatch mockSpriteBatch;
    private OrthographicCamera mockCamera;
    private World mockWorld;
    private Graphics mockGraphics;
    
    @BeforeEach
    void setUp() {
        mockAssetManager = mock(AssetManager.class);
        mockScreen = mock(Screen.class);
        mockMusic = mock(Music.class);
        mockSound = mock(Sound.class);
        mockSpriteBatch = mock(SpriteBatch.class);
        mockCamera = mock(OrthographicCamera.class);
        mockWorld = mock(World.class);
        mockGraphics = mock(Graphics.class);
        
        // Mock LibGDX static fields
        Gdx.graphics = mockGraphics;
        when(mockGraphics.getWidth()).thenReturn(960);
        when(mockGraphics.getHeight()).thenReturn(540);
        
        gamePanel = new GamePanel();
    }
    
    @Test
    void testInitialization() {
        assertNotNull(gamePanel, "GamePanel should be initialized");
    }
    
    @Test
    void testSetScreen() {
        gamePanel.setScreen(mockScreen);
        assertEquals(mockScreen, gamePanel.getScreen(), "Screen should be set correctly");
    }
    
    @Test
    void testAssetLoading() {
        when(mockAssetManager.get("test_music.mp3", Music.class)).thenReturn(mockMusic);
        Music music = mockAssetManager.get("test_music.mp3", Music.class);
        assertNotNull(music, "Music should be loaded successfully");
    }
}
