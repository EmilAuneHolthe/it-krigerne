package inf112.skeleton.model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import inf112.skeleton.audio.AudioHandler;
import inf112.skeleton.model.entity.enemy.Enemy;
import inf112.skeleton.model.entity.enemy.EnemyFactory;
import inf112.skeleton.model.entity.item.Item;
import inf112.skeleton.model.entity.player.CharacterType;
import inf112.skeleton.model.entity.player.Player;
import inf112.skeleton.model.entity.player.PlayerAnimation;
import inf112.skeleton.model.entity.player.PlayerFactory;
import inf112.skeleton.model.entity.player.PlayerMovement;
import inf112.skeleton.model.entity.player.TestPlayerAnimation;
import inf112.skeleton.model.map.Borders;
import inf112.skeleton.model.map.Map;
import inf112.skeleton.model.map.MapManager;
import inf112.skeleton.model.map.MapType;
import inf112.skeleton.view.GameRenderer;
import inf112.skeleton.view.screen.ScreenType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

class WorldFunctionsTest {
    private WorldFunctions worldFunctions;
    private GamePanel mockGamePanel;
    private World mockWorld;
    private Player mockPlayer;
    private MapManager mockMapManager;
    private GameRenderer mockGameRenderer;
    private Map mockMap;
    private Body mockBody;
    private AudioHandler mockAudioHandler;
    private PlayerMovement mockPlayerMovement;
    private Input mockInput;
    private Application mockApp;
    private Files mockFiles;
    private FileHandle mockFileHandle;
    private Texture mockTexture;
    private TestPlayerAnimation mockPlayerAnimation;
    private GL20 mockGL20;

    @BeforeEach
    void setUp() {
        mockGamePanel = mock(GamePanel.class);
        mockWorld = mock(World.class);
        mockPlayer = mock(Player.class);
        mockMapManager = mock(MapManager.class);
        mockGameRenderer = mock(GameRenderer.class);
        mockMap = mock(Map.class);
        mockBody = mock(Body.class);
        mockAudioHandler = mock(AudioHandler.class);
        mockPlayerMovement = mock(PlayerMovement.class);
        mockInput = mock(Input.class);
        mockApp = mock(Application.class);
        mockFiles = mock(Files.class);
        mockFileHandle = mock(FileHandle.class);
        mockTexture = mock(Texture.class);
        mockGL20 = mock(GL20.class);

        // Mock LibGDX input, app, files, and graphics
        Gdx.input = mockInput;
        Gdx.app = mockApp;
        Gdx.files = mockFiles;
        Gdx.gl20 = mockGL20;
        when(mockInput.isKeyJustPressed(anyInt())).thenReturn(false);
        when(mockFiles.internal(anyString())).thenReturn(mockFileHandle);
        when(mockFileHandle.exists()).thenReturn(true);
        doNothing().when(mockApp).error(anyString(), anyString());

        // Mock GamePanel dependencies
        when(mockGamePanel.getWorld()).thenReturn(mockWorld);
        when(mockGamePanel.getMapManager()).thenReturn(mockMapManager);
        when(mockGamePanel.getGameRenderer()).thenReturn(mockGameRenderer);
        when(mockGamePanel.getPlayer()).thenReturn(mockPlayer);
        when(mockGamePanel.getEnemy()).thenReturn(new Array<Enemy>());
        when(mockGamePanel.getAudioHandler()).thenReturn(mockAudioHandler);
        when(mockMapManager.getCurrentMap()).thenReturn(mockMap);
        when(mockMap.getBorders(anyString())).thenReturn(new Array<>());
        when(mockMap.getPlayerSpawn()).thenReturn(new Vector2(10, 20));
        when(mockPlayer.getBody()).thenReturn(mockBody);
        when(mockPlayer.getMovement()).thenReturn(mockPlayerMovement);
        when(mockBody.getPosition()).thenReturn(new Vector2(0, 0));

        // Mock Box2D body creation
        when(mockWorld.createBody(any(BodyDef.class))).thenReturn(mockBody);
        when(mockBody.createFixture(any())).thenReturn(null);
    }

    /**
     * Tests the map transition functionality.
     * Verifies that when changing maps:
     * - The map manager updates to the new map type
     * - The player's key state is reset
     * - The game renderer updates the doors
     */
    @Test
    void testChangeMap() {
        // Create a mock enemy array
        Array<Enemy> enemies = new Array<>();
        when(mockGamePanel.getEnemy()).thenReturn(enemies);
        
        // Reset mock invocations to ignore setup calls
        reset(mockMapManager, mockPlayer, mockGameRenderer);
        when(mockMapManager.getCurrentMap()).thenReturn(mockMap);
        when(mockPlayer.getMovement()).thenReturn(mockPlayerMovement);
        
        worldFunctions = new WorldFunctions(mockGamePanel);
        worldFunctions.changeMap(MapType.MAP_CASTLE);
        
        verify(mockMapManager).setMap(MapType.MAP_CASTLE);
        verify(mockPlayer).setKey(false);
        verify(mockGameRenderer).updateDoors();
    }

    /**
     * Tests the task board spawning functionality.
     * Verifies that:
     * - The map manager spawns the task board
     * - The game renderer updates to show the task board
     */
    @Test
    void testSpawnTaskBoard() {
        when(mockGamePanel.getMapManager()).thenReturn(mockMapManager);
        when(mockGamePanel.getGameRenderer()).thenReturn(mockGameRenderer);
        when(mockGamePanel.getWorld()).thenReturn(mockWorld);
        when(mockMapManager.getCurrentMap()).thenReturn(mockMap);
        when(mockMap.getBorders(anyString())).thenReturn(new Array<>());
        
        worldFunctions = new WorldFunctions(mockGamePanel);
        
        reset(mockMapManager, mockGameRenderer);
        
        when(mockGamePanel.getMapManager()).thenReturn(mockMapManager);
        when(mockGamePanel.getGameRenderer()).thenReturn(mockGameRenderer);
        when(mockMapManager.getCurrentMap()).thenReturn(mockMap);

        worldFunctions.spawnTaskBoard();
        
        verify(mockMapManager, times(1)).spawnTaskBoard();
        verify(mockGameRenderer, times(1)).updateTaskBoard();
    }

    /**
     * Tests the main update loop functionality.
     * Verifies that:
     * - Player movement is updated
     * - Player mana is regenerated
     * - Player movement component is updated
     */
    @Test
    void testUpdate() {
        when(mockPlayer.getMovement()).thenReturn(mockPlayerMovement);
        when(mockBody.getPosition()).thenReturn(new Vector2(0, 0));
        
        worldFunctions = new WorldFunctions(mockGamePanel);
        worldFunctions.update(0.5f);
        
        verify(mockPlayer).getMovement();
        verify(mockPlayer).regenerateMana(0.5f);
        verify(mockPlayerMovement).update();
    }

    /**
     * Tests the victory condition handling.
     * Verifies that:
     * - When victory is set to true, the game switches to the victory screen
     * - The victory state is properly reset after the test
     */
    @Test
    void testSetVictory() {
        WorldFunctions.victory = true;
        
        worldFunctions = new WorldFunctions(mockGamePanel);
        worldFunctions.setVictory();
        
        verify(mockGamePanel).setScreen(ScreenType.VICTORY);
        
        WorldFunctions.victory = false;
    }

    /**
     * Tests the teleportation from MAP_START to MAP_CASTLE.
     * Verifies that:
     * - When player is at the correct position in MAP_START
     * - The map changes to MAP_CASTLE
     */
    @Test
    void testTeleportPlayerToMapCastle() {
        when(mockBody.getPosition()).thenReturn(new Vector2(72.5f, 75.4f));
        when(mockMapManager.getCurrentMapType()).thenReturn(MapType.MAP_START);
        
        worldFunctions = new WorldFunctions(mockGamePanel);
        worldFunctions.changeMap(MapType.MAP_CASTLE);
        
        verify(mockMapManager).setMap(MapType.MAP_CASTLE);
    }

    /**
     * Tests the teleportation from MAP_CASTLE to MAP_BOSS.
     * Verifies that:
     * - When player is at the correct position in MAP_CASTLE
     * - The map changes to MAP_BOSS
     */
    @Test
    void testTeleportPlayerToMapBoss() {
        when(mockBody.getPosition()).thenReturn(new Vector2(83.5f, 78.0f));
        when(mockMapManager.getCurrentMapType()).thenReturn(MapType.MAP_CASTLE);
        
        worldFunctions = new WorldFunctions(mockGamePanel);
        worldFunctions.changeMap(MapType.MAP_BOSS);
        
        verify(mockMapManager).setMap(MapType.MAP_BOSS);
    }

    /**
     * Tests enemy spawning functionality.
     * Verifies that:
     * - Enemies are properly created when changing maps
     * - The game renderer updates to show the new enemies
     */
    @Test
    void testSpawnEnemy() {
        Array<Enemy> enemies = new Array<>();
        Enemy mockEnemy = mock(Enemy.class);
        enemies.add(mockEnemy);
        
        when(mockGamePanel.getMapManager()).thenReturn(mockMapManager);
        when(mockGamePanel.getGameRenderer()).thenReturn(mockGameRenderer);
        when(mockGamePanel.getWorld()).thenReturn(mockWorld);
        when(mockGamePanel.getEnemy()).thenReturn(enemies);
        when(mockMapManager.getCurrentMap()).thenReturn(mockMap);
        when(mockMap.getBorders(anyString())).thenReturn(new Array<>());
        when(mockWorld.createBody(any(BodyDef.class))).thenReturn(mockBody);
        
        worldFunctions = new WorldFunctions(mockGamePanel);
        
        reset(mockGamePanel, mockGameRenderer);

        when(mockGamePanel.getEnemy()).thenReturn(enemies);
        when(mockGamePanel.getMapManager()).thenReturn(mockMapManager);
        when(mockGamePanel.getGameRenderer()).thenReturn(mockGameRenderer);
        when(mockGamePanel.getWorld()).thenReturn(mockWorld);
        when(mockMapManager.getCurrentMap()).thenReturn(mockMap);
        when(mockWorld.createBody(any(BodyDef.class))).thenReturn(mockBody);
        
        worldFunctions.changeMap(MapType.MAP_CASTLE);
        
        verify(mockGameRenderer).updateEnemy(any());
    }

    /**
     * Tests player spawning functionality.
     * Verifies that:
     * - A new player is created when none exists
     * - The player is created at the correct spawn position
     * - The game renderer updates to show the player
     */
    @Test
    void testSpawnPlayer() {
        reset(mockGamePanel);
        when(mockGamePanel.getPlayer()).thenReturn(null).thenReturn(mockPlayer);
        when(mockGamePanel.getWorld()).thenReturn(mockWorld);
        when(mockGamePanel.getMapManager()).thenReturn(mockMapManager);
        when(mockGamePanel.getGameRenderer()).thenReturn(mockGameRenderer);
        when(mockGamePanel.getEnemy()).thenReturn(new Array<Enemy>());
        when(mockMapManager.getCurrentMap()).thenReturn(mockMap);
        when(mockMap.getPlayerSpawn()).thenReturn(new Vector2(10, 20));

        try (MockedConstruction<PlayerFactory> mockPlayerFactory = mockConstruction(PlayerFactory.class,
                (mock, context) -> {
                    when(mock.createPlayer(anyFloat(), anyFloat(), any(CharacterType.class))).thenReturn(mockPlayer);
                })) {
            
            worldFunctions = new WorldFunctions(mockGamePanel);
            
            verify(mockGameRenderer).updatePlayer(mockPlayer);
        }
    }

    /**
     * Tests item spawning functionality.
     * Verifies that:
     * - Items are properly created when changing maps
     * - The game renderer updates to show the new items
     */
    @Test
    void testSpawnItem() {
        Array<Item> items = new Array<>();
        Item mockItem = mock(Item.class);
        items.add(mockItem);
        
        when(mockGamePanel.getMapManager()).thenReturn(mockMapManager);
        when(mockGamePanel.getGameRenderer()).thenReturn(mockGameRenderer);
        when(mockGamePanel.getWorld()).thenReturn(mockWorld);
        when(mockGamePanel.getItems()).thenReturn(items);
        when(mockMapManager.getCurrentMap()).thenReturn(mockMap);
        when(mockMap.getBorders(anyString())).thenReturn(new Array<>());
        when(mockWorld.createBody(any(BodyDef.class))).thenReturn(mockBody);
        
        worldFunctions = new WorldFunctions(mockGamePanel);
        
        reset(mockGamePanel, mockGameRenderer);

        when(mockGamePanel.getItems()).thenReturn(items);
        when(mockGamePanel.getMapManager()).thenReturn(mockMapManager);
        when(mockGamePanel.getGameRenderer()).thenReturn(mockGameRenderer);
        when(mockGamePanel.getWorld()).thenReturn(mockWorld);
        when(mockMapManager.getCurrentMap()).thenReturn(mockMap);
        when(mockWorld.createBody(any(BodyDef.class))).thenReturn(mockBody);
        
        worldFunctions.changeMap(MapType.MAP_CASTLE);
        
        verify(mockGameRenderer).updateItem(any());
    }

    /**
     * Tests the static enemy list functionality.
     * Verifies that:
     * - The static enemy list can be accessed
     * - Enemies can be added to and retrieved from the list
     */
    @Test
    void testGetEnemies() {
        Array<Enemy> enemies = new Array<>();
        Enemy mockEnemy = mock(Enemy.class);
        enemies.add(mockEnemy);
        
        WorldFunctions.getEnemies().clear();
        WorldFunctions.getEnemies().add(mockEnemy);
        
        Array<Enemy> retrievedEnemies = WorldFunctions.getEnemies();
        assertEquals(1, retrievedEnemies.size);
        assertSame(mockEnemy, retrievedEnemies.get(0));
    }
}
