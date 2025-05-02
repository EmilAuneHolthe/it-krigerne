package inf112.skeleton.model.map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.utils.Array;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import inf112.skeleton.BaseTest;
import inf112.skeleton.model.entity.enemy.EnemySpawn;
import inf112.skeleton.model.entity.item.ItemSpawn;
import inf112.skeleton.model.entity.item.ItemType;
import inf112.skeleton.model.entity.player.CharacterType;

// Kan ikke teste denne classen seperat, må teste sammen med andre klasser

class MapTest extends BaseTest {
    
    private Map map;
    private TiledMap mockTiledMap;
    private MapLayers mockMapLayers;
    private MapLayer mockMapLayer;
    private MapObjects mockMapObjects;
    private Iterator<MapObject> mockIterator;
    private MapLayer mockEnemyLayer;
    private MapLayer mockItemLayer;
    private MapLayer mockBossLayer;
    private MapObjects mockEnemyObjects;
    private MapObjects mockItemObjects;
    private MapObjects mockBossObjects;
    
    @BeforeEach
    void setUp() {
        mockTiledMap = mock(TiledMap.class);
        mockMapLayers = mock(MapLayers.class);
        mockMapLayer = mock(MapLayer.class);
        mockMapObjects = mock(MapObjects.class);
        mockIterator = Collections.emptyIterator();
        
        // Setup enemy layer
        mockEnemyLayer = mock(MapLayer.class);
        mockEnemyObjects = mock(MapObjects.class);
        when(mockEnemyLayer.getObjects()).thenReturn(mockEnemyObjects);
        when(mockEnemyObjects.getCount()).thenReturn(0); // Start with empty layer
        when(mockEnemyObjects.iterator()).thenReturn(Collections.emptyIterator());
        
        // Setup item layer
        mockItemLayer = mock(MapLayer.class);
        mockItemObjects = mock(MapObjects.class);
        when(mockItemLayer.getObjects()).thenReturn(mockItemObjects);
        when(mockItemObjects.getCount()).thenReturn(0); // Start with empty layer
        when(mockItemObjects.iterator()).thenReturn(Collections.emptyIterator());
        
        // Setup boss layer
        mockBossLayer = mock(MapLayer.class);
        mockBossObjects = mock(MapObjects.class);
        when(mockBossLayer.getObjects()).thenReturn(mockBossObjects);
        when(mockBossObjects.getCount()).thenReturn(0); // Start with empty layer
        when(mockBossObjects.iterator()).thenReturn(Collections.emptyIterator());
        
        // Setup main map layers
        when(mockTiledMap.getLayers()).thenReturn(mockMapLayers);
        when(mockMapLayers.get("Collision")).thenReturn(mockMapLayer);
        when(mockMapLayers.get("Enemy")).thenReturn(mockEnemyLayer);
        when(mockMapLayers.get("Items")).thenReturn(mockItemLayer);
        when(mockMapLayers.get("Boss")).thenReturn(mockBossLayer);
        when(mockMapLayer.getObjects()).thenReturn(mockMapObjects);
        when(mockMapObjects.iterator()).thenReturn(mockIterator);
        when(mockMapObjects.getCount()).thenReturn(0); // No collision objects by default
        
        map = new Map(mockTiledMap);
    }
    
    @Test
    void testMapInitialization() {
        assertNotNull(map, "Map should be initialized");
    }
    
    @Test
    void testPlayerSpawnPoint() {
        // Setup player spawn object
        RectangleMapObject mockPlayerObject = mock(RectangleMapObject.class);
        when(mockPlayerObject.getRectangle()).thenReturn(new com.badlogic.gdx.math.Rectangle(0, 0, 1, 1));
        
        MapLayer mockPlayerLayer = mock(MapLayer.class);
        MapObjects mockPlayerObjects = mock(MapObjects.class);
        when(mockMapLayers.get("Player")).thenReturn(mockPlayerLayer);
        when(mockPlayerLayer.getObjects()).thenReturn(mockPlayerObjects);
        when(mockPlayerObjects.getCount()).thenReturn(1);
        when(mockPlayerObjects.get(0)).thenReturn(mockPlayerObject);
        
        Vector2 spawn = map.getPlayerSpawn();
        assertNotNull(spawn, "Player spawn point should not be null");
        assertEquals(new Vector2(0, 0), spawn, "Player spawn position should match");
    }
    
    @Test
    void testCollisionAreasExist() {
        assertNotNull(map.getColissionAreas(), "Collision areas should not be null");
    }

    @Test
    void testGetTiledMap() {
        TiledMap result = map.getTiledMap();
        assertNotNull(result, "TiledMap should not be null");
        assertEquals(mockTiledMap, result, "Should return the same TiledMap instance");
    }

    @Test
    void testGetEnemySpawn() {
        // Create a mock enemy spawn object
        RectangleMapObject mockEnemyObject = mock(RectangleMapObject.class);
        MapProperties mockProperties = mock(MapProperties.class);
        when(mockEnemyObject.getName()).thenReturn("TestEnemy");
        when(mockEnemyObject.getProperties()).thenReturn(mockProperties);
        when(mockProperties.get("Type")).thenReturn("SKELETON");
        when(mockEnemyObject.getRectangle()).thenReturn(new com.badlogic.gdx.math.Rectangle(10, 10, 1, 1));
        
        // Setup enemy objects
        Array<MapObject> enemyObjects = new Array<>();
        enemyObjects.add(mockEnemyObject);
        when(mockEnemyObjects.getCount()).thenReturn(1);
        when(mockEnemyObjects.iterator()).thenReturn(enemyObjects.iterator());
        when(mockEnemyObjects.get(0)).thenReturn(mockEnemyObject);
        
        // Create a new map instance with the updated mocks
        map = new Map(mockTiledMap);
        
        ArrayList<EnemySpawn> spawns = map.getEnemySpawn();
        assertNotNull(spawns, "Enemy spawns should not be null");
        assertFalse(spawns.isEmpty(), "Should have at least one enemy spawn");
        
        EnemySpawn spawn = spawns.get(0);
        assertEquals("TestEnemy", spawn.name(), "Enemy name should match");
        assertEquals(CharacterType.SKELETON, spawn.characterType(), "Enemy type should match");
        assertEquals(new Vector2(10, 10), spawn.position(), "Enemy position should match");
    }

    @Test
    void testGetBossSpawn() {
        // Create a mock boss spawn object
        RectangleMapObject mockBossObject = mock(RectangleMapObject.class);
        when(mockBossObject.getRectangle()).thenReturn(new com.badlogic.gdx.math.Rectangle(20, 20, 1, 1));
        
        // Setup boss objects
        when(mockBossObjects.getCount()).thenReturn(1);
        when(mockBossObjects.get(0)).thenReturn(mockBossObject);
        
        // Create a new map instance with the updated mocks
        map = new Map(mockTiledMap);
        
        Vector2 bossSpawn = map.getBossSpawn();
        assertNotNull(bossSpawn, "Boss spawn should not be null");
        assertEquals(new Vector2(20, 20), bossSpawn, "Boss spawn position should match");
    }

    @Test
    void testGetItemSpawn() {
        // Create a mock item spawn object
        RectangleMapObject mockItemObject = mock(RectangleMapObject.class);
        MapProperties mockProperties = mock(MapProperties.class);
        when(mockItemObject.getProperties()).thenReturn(mockProperties);
        when(mockProperties.get("Type")).thenReturn("HEALTH");
        when(mockItemObject.getRectangle()).thenReturn(new com.badlogic.gdx.math.Rectangle(30, 30, 1, 1));
        
        // Setup item objects
        Array<MapObject> itemObjects = new Array<>();
        itemObjects.add(mockItemObject);
        when(mockItemObjects.getCount()).thenReturn(1);
        when(mockItemObjects.iterator()).thenReturn(itemObjects.iterator());
        when(mockItemObjects.get(0)).thenReturn(mockItemObject);
        
        // Create a new map instance with the updated mocks
        map = new Map(mockTiledMap);
        
        ArrayList<ItemSpawn> spawns = map.getItemSpawn();
        assertNotNull(spawns, "Item spawns should not be null");
        assertFalse(spawns.isEmpty(), "Should have at least one item spawn");
        
        ItemSpawn spawn = spawns.get(0);
        assertEquals(ItemType.HEALTH, spawn.itemType(), "Item type should match");
        assertEquals(new Vector2(30, 30), spawn.position(), "Item position should match");
    }
}
