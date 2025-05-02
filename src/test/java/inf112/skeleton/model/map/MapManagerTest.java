package inf112.skeleton.model.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import inf112.skeleton.BaseTest;
import inf112.skeleton.model.GamePanel;
import inf112.skeleton.model.entity.door.Door;
import inf112.skeleton.model.entity.taskBoard.TaskBoard;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

import java.util.Collections;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class MapManagerTest extends BaseTest {
    private MapManager mapManager;
    private GamePanel gamePanel;
    private static AssetManager assetManager;
    private static World world;
    private static TiledMap mockTiledMap;
    private static MapLayers mockLayers;
    private static MapLayer mockLayer;
    private static MapObjects mockObjects;
    private static Body mockBody;
    private MockedConstruction<Map> mockedMapConstruction;

    @BeforeAll
    static void initMocks() {
        // Setup mocks
        assetManager = spy(new AssetManager());
        mockTiledMap = mock(TiledMap.class);
        mockLayers = mock(MapLayers.class);
        mockLayer = mock(MapLayer.class);
        mockObjects = mock(MapObjects.class);
        world = mock(World.class);
        mockBody = mock(Body.class);
        
        // Setup TiledMap mock
        when(mockTiledMap.getLayers()).thenReturn(mockLayers);
        when(mockLayers.get(any(String.class))).thenReturn(mockLayer);
        when(mockLayer.getObjects()).thenReturn(mockObjects);
        when(mockObjects.iterator()).thenReturn(Collections.<MapObject>emptyIterator());
        
        // Setup World mock
        when(world.createBody(any())).thenReturn(mockBody);
        doAnswer(invocation -> {
            Array<Body> bodies = invocation.getArgument(0);
            bodies.add(mockBody);
            return null;
        }).when(world).getBodies(any(Array.class));
        
        // Setup AssetManager mock
        doReturn(mockTiledMap).when(assetManager).get(any(String.class), eq(TiledMap.class));
        doReturn(true).when(assetManager).isLoaded(any(String.class));
        doReturn(mock(Texture.class)).when(assetManager).get(eq("map/miniMap.png"), eq(Texture.class));
        doReturn(mock(Texture.class)).when(assetManager).get(eq("map/Door1.png"), eq(Texture.class));
    }

    @BeforeEach
    void setUp() {
        gamePanel = mock(GamePanel.class);
        when(gamePanel.getAssetManager()).thenReturn(assetManager);
        when(gamePanel.getWorld()).thenReturn(world);
        mapManager = new MapManager(gamePanel);
    }

    @AfterEach
    void tearDown() {
        if (mockedMapConstruction != null) {
            mockedMapConstruction.close();
            mockedMapConstruction = null;
        }
    }

    private void setupMockedMap(Array<CollisionArea> collisionAreas, Array<Borders> doorAreas, Array<Borders> taskBoardBorders) {
        if (mockedMapConstruction != null) {
            mockedMapConstruction.close();
        }
        mockedMapConstruction = mockConstruction(Map.class, (mock, context) -> {
            when(mock.getColissionAreas()).thenReturn(collisionAreas);
            when(mock.getDoorsAreas()).thenReturn(doorAreas);
            when(mock.getBorders("TaskBoard")).thenReturn(taskBoardBorders);
        });
    }

    @Test
    void testMapLoadingAndCaching() {
        // First load should get from AssetManager
        mapManager.setMap(MapType.MAP_START);
        verify(assetManager, atLeastOnce()).get(eq(MapType.MAP_START.getFilePath()), eq(TiledMap.class));

        // Reset interaction count
        clearInvocations(assetManager);

        // Second load of same map should use cache
        mapManager.setMap(MapType.MAP_START);
        verify(assetManager, never()).get(eq(MapType.MAP_START.getFilePath()), eq(TiledMap.class));
    }

    @Test
    void testCollisionAreaCreation() {
        Array<CollisionArea> collisionAreas = new Array<>();
        float[] vertices = {0, 0, 32, 0, 32, 32, 0, 32};
        collisionAreas.add(new CollisionArea(0, 0, vertices));
        setupMockedMap(collisionAreas, new Array<>(), new Array<>());
        
        mapManager.setMap(MapType.MAP_START);
        assertFalse(mapManager.getBodies().isEmpty());
        verify(world, atLeastOnce()).createBody(any());
    }

    @Test
    void testDoorManagement() {
        Array<Borders> doorAreas = new Array<>();
        doorAreas.add(new Borders(0, 0, 32, 32, "TestDoor"));
        setupMockedMap(new Array<>(), doorAreas, new Array<>());
        
        mapManager.setMap(MapType.MAP_START);
        Array<Door> doors = mapManager.getDoors();
        
        assertFalse(doors.isEmpty());
        assertEquals("TestDoor", doors.get(0).getName());
        assertTrue(mapManager.openDoor("TestDoor"));
        assertTrue(mapManager.getDoors().isEmpty());
    }

    @Test
    void testTaskBoardManagement() {
        Array<Borders> taskBoardBorders = new Array<>();
        taskBoardBorders.add(new Borders(0, 0, 32, 32, "TaskBoard"));
        setupMockedMap(new Array<>(), new Array<>(), taskBoardBorders);
        
        mapManager.setMap(MapType.MAP_START);
        mapManager.spawnTaskBoard();
        
        TaskBoard taskBoard = mapManager.getTaskBoard();
        assertNotNull(taskBoard);
        
        // TaskBoard should only exist in start map
        mapManager.setMap(MapType.MAP_CASTLE);
        assertNull(mapManager.getTaskBoard());
    }

    @Test
    void testMapChangeCleanup() {
        Array<CollisionArea> collisionAreas = new Array<>();
        float[] vertices = {0, 0, 32, 0, 32, 32, 0, 32};
        collisionAreas.add(new CollisionArea(0, 0, vertices));
        setupMockedMap(collisionAreas, new Array<>(), new Array<>());
        
        mapManager.setMap(MapType.MAP_START);
        Array<Body> initialBodies = new Array<>(mapManager.getBodies());
        
        // Setup the mock body to be returned as "GROUND"
        when(mockBody.getUserData()).thenReturn("GROUND");
        
        mapManager.setMap(MapType.MAP_CASTLE);
        verify(world, atLeastOnce()).destroyBody(any());
        assertNotSame(initialBodies, mapManager.getBodies());
    }

    @Test
    void testMapListenerNotification() {
        MapListener listener = mock(MapListener.class);
        mapManager.addListener(listener);
        mapManager.setMap(MapType.MAP_START);
        verify(listener).mapChanged(any(Map.class));
    }
} 