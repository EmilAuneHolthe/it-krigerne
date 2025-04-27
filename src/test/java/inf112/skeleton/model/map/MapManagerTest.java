package inf112.skeleton.model.map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.physics.box2d.World;


import inf112.skeleton.model.GamePanel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MapManagerTest {
    private MapManager mapManager;
    private GamePanel mockGamePanel;
    private World mockWorld;
    private AssetManager mockAssetManager;
    private TiledMap mockTiledMap;
    private Map mockMap;
    private Application mockApplication;
    private MapLayers mockMapLayers;
    private TiledMapTileLayer mockTileLayer;

    @BeforeEach
    void setUp() {
        // Create mocks
        mockGamePanel = mock(GamePanel.class);
        mockWorld = mock(World.class);
        mockAssetManager = mock(AssetManager.class);
        mockTiledMap = mock(TiledMap.class);
        mockMap = mock(Map.class);
        mockApplication = mock(Application.class);
        mockMapLayers = mock(MapLayers.class);
        mockTileLayer = mock(TiledMapTileLayer.class);

        // Mock Gdx.app
        Gdx.app = mockApplication;

        // Mock GamePanel
        when(mockGamePanel.getWorld()).thenReturn(mockWorld);
        when(mockGamePanel.getAssetManager()).thenReturn(mockAssetManager);
        when(mockAssetManager.get(anyString(), eq(TiledMap.class))).thenReturn(mockTiledMap);
        
        // Mock TiledMap layers
        when(mockTiledMap.getLayers()).thenReturn(mockMapLayers);
        when(mockMapLayers.get("PlayerSpawn")).thenReturn(mockTileLayer);

        // Create MapManager instance
        mapManager = new MapManager(mockGamePanel);
    }

    @Test
    void testConstructor() {
        assertNotNull(mapManager, "MapManager should be initialized");
        assertNull(mapManager.getCurrentMap(), "Current map should be null initially");
    }

    
} 