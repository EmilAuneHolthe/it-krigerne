package inf112.skeleton.app.mapTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;

import inf112.skeleton.model.map.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Iterator;

// Kan ikke teste denne classen seperat, må teste sammen med andre klasser

class MapTest {
    
    private Map map;
    private TiledMap mockTiledMap;
    private MapLayers mockMapLayers;
    private MapLayer mockMapLayer;
    private MapObjects mockMapObjects;
    private Iterator<MapObject> mockIterator;
    
    @BeforeEach
    void setUp() {
        mockTiledMap = mock(TiledMap.class);
        mockMapLayers = mock(MapLayers.class);
        mockMapLayer = mock(MapLayer.class);
        mockMapObjects = mock(MapObjects.class);
        mockIterator = Collections.emptyIterator();
        
        when(mockTiledMap.getLayers()).thenReturn(mockMapLayers);
        when(mockMapLayers.get(anyString())).thenReturn(mockMapLayer);
        when(mockMapLayer.getObjects()).thenReturn(mockMapObjects);
        when(mockMapObjects.iterator()).thenReturn(mockIterator); // Prevent NullPointerException
        
        map = new Map(mockTiledMap);
    }
    
    @Test
    void testMapInitialization() {
        assertNotNull(map, "Map should be initialized");
    }
    
    @Test
    void testPlayerSpawnPoint() {
        Vector2 spawn = map.getPlayerSpawn();
        assertNotNull(spawn, "Player spawn point should not be null");
    }
    
    @Test
    void testCollisionAreasExist() {
        assertNotNull(map.getColissionAreas(), "Collision areas should not be null");
    }
}
