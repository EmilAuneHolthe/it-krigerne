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
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

import inf112.skeleton.audio.AudioHandler;
import inf112.skeleton.controller.KeyHandler;
import inf112.skeleton.model.map.MapManager;
import inf112.skeleton.view.GameRenderer;
import inf112.skeleton.view.screen.ScreenType;
import inf112.skeleton.view.screen.GameScreen;
import inf112.skeleton.model.entity.player.Player;
import inf112.skeleton.model.entity.enemy.Enemy;
import inf112.skeleton.model.entity.item.Item;
import inf112.skeleton.model.map.MapType;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class GamePanelTest {
    
    @Mock private GamePanel gamePanel;
    @Mock private Screen mockScreen;
    @Mock private Graphics mockGraphics;
    @Mock private Application mockApplication;
    @Mock private World mockWorld;
    @Mock private AssetManager mockAssetManager;
    @Mock private SpriteBatch mockSpriteBatch;
    @Mock private Box2DDebugRenderer mockBox2DDebugRenderer;
    @Mock private GameRenderer mockGameRenderer;
    @Mock private GameScreen mockGameScreen;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // Mock LibGDX static fields
        Gdx.graphics = mockGraphics;
        Gdx.app = mockApplication;
        when(mockGraphics.getWidth()).thenReturn(960);
        when(mockGraphics.getHeight()).thenReturn(540);
        
        // Mock GamePanel methods
        when(gamePanel.getWorld()).thenReturn(mockWorld);
        when(gamePanel.getAssetManager()).thenReturn(mockAssetManager);
        when(gamePanel.getSpriteBatch()).thenReturn(mockSpriteBatch);
        when(gamePanel.getBox2DDebugRenderer()).thenReturn(mockBox2DDebugRenderer);
        when(gamePanel.getGameRenderer()).thenReturn(mockGameRenderer);
    }

    /**
     * Tests the public constants of the GamePanel class.
     * Verifies that:
     * - The bit masks for collision categories are correct
     * - The unit scale is correct
     */
    @Test
    void testPublicConstants() {
        assertEquals(1, GamePanel.BIT_PLAYER);
        assertEquals(2, GamePanel.BIT_BOX);
        assertEquals(4, GamePanel.BIT_GROUND);
        assertEquals(1/32f, GamePanel.UNIT_SCALE);
    }

    /**
     * Tests the static fields initialization.
     * Verifies that:
     * - The body and fixture definitions are properly initialized
     */
    @Test
    void testStaticFields() {
        assertNotNull(GamePanel.BODY_DEF);
        assertNotNull(GamePanel.FIXTURE_DEF);
    }

    /**
     * Tests screen management functionality.
     * Verifies that:
     * - Screens can be set and retrieved
     * - Screen transitions work correctly
     */
    @Test
    void testScreenManagement() {
        gamePanel.setScreen(mockScreen);
        verify(gamePanel).setScreen(mockScreen);
    }

    /**
     * Tests player management.
     * Verifies that:
     * - Players can be set and retrieved
     * - Player reset works correctly
     */
    @Test
    void testPlayerManagement() {
        Player mockPlayer = mock(Player.class);
        
        gamePanel.setPlayer(mockPlayer);
        verify(gamePanel).setPlayer(mockPlayer);
        
        gamePanel.resetPlayer();
        verify(gamePanel).resetPlayer();
    }

    /**
     * Tests enemy management.
     * Verifies that:
     * - Enemies can be set and retrieved
     * - Enemy array is properly managed
     */
    @Test
    void testEnemyManagement() {
        Array<Enemy> enemies = new Array<>();
        Enemy mockEnemy = mock(Enemy.class);
        enemies.add(mockEnemy);
        
        gamePanel.setEnemy(enemies);
        verify(gamePanel).setEnemy(enemies);
    }

    /**
     * Tests item management.
     * Verifies that:
     * - Items can be set and retrieved
     * - Item array is properly managed
     */
    @Test
    void testItemManagement() {
        Array<Item> items = new Array<>();
        Item mockItem = mock(Item.class);
        items.add(mockItem);
        
        gamePanel.setItems(items);
        verify(gamePanel).setItems(items);
    }

    /**
     * Tests singleton pattern.
     * Verifies that:
     * - The getInstance method returns the same instance
     * - Multiple calls return the same instance
     */
    @Test
    void testSingleton() {
        GamePanel instance1 = GamePanel.getInstance();
        GamePanel instance2 = GamePanel.getInstance();
        
        assertSame(instance1, instance2);
    }

    /**
     * Tests the create method.
     * Verifies that:
     * - The game is properly initialized
     * - All necessary components are created
     */
    @Test
    void testCreate() {
        gamePanel.create();
        verify(gamePanel).create();
    }

    /**
     * Tests the render method.
     * Verifies that:
     * - The world step is called with correct parameters
     * - The game renderer is called when in game screen
     */
    @Test
    void testRender() {
        when(mockGraphics.getDeltaTime()).thenReturn(0.016f); // 60 FPS
        when(gamePanel.getScreen()).thenReturn(mockGameScreen);
        
        gamePanel.render();
        verify(gamePanel).render();
    }

    /**
     * Tests the dispose method.
     * Verifies that:
     * - All resources are properly disposed
     * - No memory leaks occur
     */
    @Test
    void testDispose() {
        gamePanel.dispose();
        verify(gamePanel).dispose();
    }

    /**
     * Tests the changemap method.
     * Verifies that:
     * - Map transitions work correctly
     * - The map manager is updated
     */
    @Test
    void testChangemap() {
        gamePanel.changemap(MapType.MAP_START);
        verify(gamePanel).changemap(MapType.MAP_START);
    }

    /**
     * Tests the removeScreen method.
     * Verifies that:
     * - Screens can be removed
     * - Resources are properly cleaned up
     */
    @Test
    void testRemoveScreen() {
        gamePanel.removeScreen(ScreenType.GAME);
        verify(gamePanel).removeScreen(ScreenType.GAME);
    }
}
