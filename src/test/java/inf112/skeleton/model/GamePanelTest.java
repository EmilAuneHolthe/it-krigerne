package inf112.skeleton.model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.graphics.GL20;

import inf112.skeleton.audio.AudioHandler;
import inf112.skeleton.controller.KeyHandler;
import inf112.skeleton.model.map.MapManager;
import inf112.skeleton.model.map.MapChanger;
import inf112.skeleton.model.map.Map;
import inf112.skeleton.view.GameRenderer;
import inf112.skeleton.view.screen.ScreenType;
import inf112.skeleton.view.ui.PlayerHUD;
import inf112.skeleton.view.screen.GameScreen;
import inf112.skeleton.model.entity.player.Player;
import inf112.skeleton.model.entity.taskBoard.TaskBoard;
import inf112.skeleton.model.entity.enemy.Enemy;
import inf112.skeleton.model.entity.item.Item;
import inf112.skeleton.model.map.MapType;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;
import java.util.EnumMap;

class GamePanelTest {
    
    private GamePanel gamePanel;
    @Mock private Screen mockScreen;
    @Mock private Graphics mockGraphics;
    @Mock private Application mockApplication;
    @Mock private World mockWorld;
    @Mock private AssetManager mockAssetManager;
    @Mock private SpriteBatch mockSpriteBatch;
    @Mock private Box2DDebugRenderer mockBox2DDebugRenderer;
    @Mock private GameRenderer mockGameRenderer;
    @Mock private GameScreen mockGameScreen;
    @Mock private FitViewport mockViewPort;
    @Mock private AudioHandler mockAudioHandler;
    @Mock private MapManager mockMapManager;
    @Mock private Player mockPlayer;
    @Mock private PlayerHUD mockPlayerHUD;
    @Mock private TaskBoard mockTaskBoard;
    @Mock private WorldFunctions mockWorldFunctions;
    @Mock private GL20 mockGL20;
    @Mock private OrthographicCamera mockCamera;
    @Mock private MapChanger mockMapChanger;
    @Mock private Map mockMap;
    
    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        
        // Mock LibGDX static fields
        Gdx.graphics = mockGraphics;
        Gdx.app = mockApplication;
        Gdx.gl20 = mockGL20;
        
        when(mockGraphics.getWidth()).thenReturn(960);
        when(mockGraphics.getHeight()).thenReturn(540);
        
        // Create real GamePanel instance
        gamePanel = new GamePanel();
        
        // Initialize screen cache with the mock game screen
        EnumMap<ScreenType, Screen> screenCache = new EnumMap<>(ScreenType.class);
        screenCache.put(ScreenType.GAME, mockGameScreen);
        setPrivateField(gamePanel, "screenCache", screenCache);
        
        // Initialize enemies array
        Array<Enemy> enemies = new Array<>();
        setPrivateField(gamePanel, "enemies", enemies);
        
        // Set private fields using reflection
        setPrivateField(gamePanel, "world", mockWorld);
        setPrivateField(gamePanel, "assetManager", mockAssetManager);
        setPrivateField(gamePanel, "spriteBatch", mockSpriteBatch);
        setPrivateField(gamePanel, "box2DDebugRenderer", mockBox2DDebugRenderer);
        setPrivateField(gamePanel, "gameRenderer", mockGameRenderer);
        setPrivateField(gamePanel, "audioHandler", mockAudioHandler);
        setPrivateField(gamePanel, "mapManager", mockMapManager);
        setPrivateField(gamePanel, "playerHUD", mockPlayerHUD);
        setPrivateField(gamePanel, "worldFunctions", mockWorldFunctions);
        setPrivateField(gamePanel, "screenViewport", mockViewPort);
        setPrivateField(gamePanel, "camera", mockCamera);
        setPrivateField(gamePanel, "player", mockPlayer);
        setPrivateField(gamePanel, "mapChanger", mockMapChanger);
        
        // Mock the viewport
        doNothing().when(mockViewPort).update(anyInt(), anyInt(), anyBoolean());
        
        // Mock the world
        doNothing().when(mockWorld).dispose();
        
        // Mock the box2d debug renderer
        doNothing().when(mockBox2DDebugRenderer).dispose();
        
        // Mock the asset manager
        doNothing().when(mockAssetManager).dispose();
        
        // Mock the sprite batch
        doNothing().when(mockSpriteBatch).dispose();
        
        // Mock the map manager
        doNothing().when(mockMapManager).setMap(any(MapType.class));
        when(mockMapManager.getCurrentMap()).thenReturn(mockMap);
        
        // Mock the game screen
        doNothing().when(mockGameScreen).dispose();
        
        // Mock the player HUD
        doNothing().when(mockPlayerHUD).updateEquippedSword(anyString());
        
        // Mock the map changer
        doNothing().when(mockMapChanger).removeObjects(any(World.class), any(Map.class), any(Array.class));
        doNothing().when(mockMapChanger).movePlayer(any(World.class), any(Map.class), any(Player.class));
    }

    private void setPrivateField(Object target, String fieldName, Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }

    @Test
    void testPublicConstants() {
        assertEquals(1, GamePanel.BIT_PLAYER);
        assertEquals(2, GamePanel.BIT_BOX);
        assertEquals(4, GamePanel.BIT_GROUND);
        assertEquals(1/32f, GamePanel.UNIT_SCALE);
    }

    @Test
    void testStaticFields() {
        assertNotNull(GamePanel.BODY_DEF);
        assertNotNull(GamePanel.FIXTURE_DEF);
    }

    @Test
    void testScreenManagement() {
        gamePanel.setScreen(mockScreen);
        assertEquals(mockScreen, gamePanel.getScreen());
    }

    @Test
    void testPlayerManagement() {
        gamePanel.setPlayer(mockPlayer);
        assertEquals(mockPlayer, gamePanel.getPlayer());
        
        gamePanel.resetPlayer();
        assertNull(gamePanel.getPlayer());
    }

    @Test
    void testEnemyManagement() {
        Array<Enemy> enemies = new Array<>();
        Enemy mockEnemy = mock(Enemy.class);
        enemies.add(mockEnemy);
        
        gamePanel.setEnemy(enemies);
        assertEquals(enemies, gamePanel.getEnemy());
    }

    @Test
    void testItemManagement() {
        Array<Item> items = new Array<>();
        Item mockItem = mock(Item.class);
        items.add(mockItem);
        
        gamePanel.setItems(items);
        assertEquals(items, gamePanel.getItems());
    }

    @Test
    void testSingleton() {
        GamePanel instance1 = GamePanel.getInstance();
        GamePanel instance2 = GamePanel.getInstance();
        assertSame(instance1, instance2);
    }

    @Test
    void testGetViewPort() {
        assertEquals(mockViewPort, gamePanel.getViewport());
    }

    @Test 
    void testGetWorld() {
        assertEquals(mockWorld, gamePanel.getWorld());
    }

    @Test
    void testDispose() {
        gamePanel.dispose();
        verify(mockWorld).dispose();
        verify(mockBox2DDebugRenderer).dispose();
        verify(mockAssetManager).dispose();
        verify(mockSpriteBatch).dispose();
    }

    @Test
    void testChangemap() {
        // Set up required dependencies
        when(mockMapManager.getCurrentMap()).thenReturn(mockMap);
        
        gamePanel.changemap(MapType.MAP_START);
        
        // Verify interactions
        verify(mockMapManager).setMap(MapType.MAP_START);
        verify(mockMapChanger).removeObjects(eq(mockWorld), eq(mockMap), any(Array.class));
        verify(mockMapChanger).movePlayer(mockWorld, mockMap, mockPlayer);
    }

    @Test
    void testRemoveScreen() {
        gamePanel.removeScreen(ScreenType.GAME);
        verify(mockGameScreen).dispose();
    }

    @Test
    void testAudioHandler() {
        assertEquals(mockAudioHandler, gamePanel.getAudioHandler());
    }


    @Test
    void testMapManager() {
        assertEquals(mockMapManager, gamePanel.getMapManager());
    }

    @Test
    void testPlayerHUD() {
        gamePanel.setPlayerHUD(mockPlayerHUD);
        gamePanel.updateEquippedSwordHUD("test_sword.png");
        verify(mockPlayerHUD).updateEquippedSword("test_sword.png");
    }

    @Test
    void testBodyAndFixtureReset() {
        GamePanel.resetBodyAndFixtureDefinition();
        
        assertEquals(0, GamePanel.BODY_DEF.position.x);
        assertEquals(0, GamePanel.BODY_DEF.position.y);
        assertEquals(0, GamePanel.BODY_DEF.gravityScale);
        assertEquals(BodyDef.BodyType.StaticBody, GamePanel.BODY_DEF.type);
        
        assertFalse(GamePanel.FIXTURE_DEF.isSensor);
        assertEquals(0.75f, GamePanel.FIXTURE_DEF.restitution);
        assertEquals(0.2f, GamePanel.FIXTURE_DEF.friction);
        assertEquals(GamePanel.BIT_GROUND, GamePanel.FIXTURE_DEF.filter.categoryBits);
        assertEquals(-1, GamePanel.FIXTURE_DEF.filter.maskBits);
    }
}
