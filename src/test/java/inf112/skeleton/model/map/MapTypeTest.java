package inf112.skeleton.model.map;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class MapTypeTest {
    
    // @Test
    // public void testMapTypeValues() {
    //     assertEquals("map/SampleMap/samplemap.tmx", MapType.MAP_START.getFilePath(), 
    //         "MAP_1 should have the correct file path");
    //     assertEquals("map/testMap/testMap.tmx", MapType.MAP_BOSS.getFilePath(), 
    //         "MAP_2 should have the correct file path");
    // }

    @Test
    public void testGetFilePath() {
        for (MapType mapType : MapType.values()) {
            assertNotNull(mapType.getFilePath(), "File path should not be null");
            assertFalse(mapType.getFilePath().isEmpty(), "File path should not be empty");
        }
    }
} 