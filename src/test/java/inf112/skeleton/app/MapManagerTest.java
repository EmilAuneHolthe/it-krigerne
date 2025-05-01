package inf112.skeleton.app;

import com.badlogic.gdx.maps.tiled.TiledMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MapManagerTest {
    private MapManager mapManager;

    @BeforeEach
    void setUp() {
        mapManager = new MapManager();
    }

    @Test
    void testLoadMap() {
        // Test loading a new map
        mapManager.loadMap("testMap");
        assertNotNull(mapManager.getCurrentMap());
        assertEquals("testMap", mapManager.getCurrentMapName());
    }

    @Test
    void testMapCaching() {
        // Test that maps are cached and reused
        mapManager.loadMap("testMap");
        TiledMap firstMap = mapManager.getCurrentMap();
        
        mapManager.loadMap("otherMap");
        mapManager.loadMap("testMap");
        
        // Should be the same map instance
        assertSame(firstMap, mapManager.getCurrentMap());
    }

    @Test
    void testTileSolidity() {
        // Test the tile solidity check
        assertTrue(mapManager.isTileSolid(0, 0));  // Both divisible by 2
        assertTrue(mapManager.isTileSolid(2, 1));  // x divisible by 2
        assertTrue(mapManager.isTileSolid(1, 2));  // y divisible by 2
        assertFalse(mapManager.isTileSolid(1, 1)); // Neither divisible by 2
    }

    @Test
    void testDispose() {
        // Test that resources are properly disposed
        mapManager.loadMap("testMap");
        mapManager.loadMap("otherMap");
        
        mapManager.dispose();
        // In a real test, we would verify that the resources are actually disposed
        // For now, we'll just verify the map name is cleared
        assertEquals("", mapManager.getCurrentMapName());
    }
} 