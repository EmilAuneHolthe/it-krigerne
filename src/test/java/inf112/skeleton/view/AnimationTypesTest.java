package inf112.skeleton.view;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AnimationTypesTest {
    @Test
    void testAnimationTypeEnumValues() {
        // Test that all enum values exist
        assertNotNull(AnimationTypes.SOLDIER_DOWN);
        assertNotNull(AnimationTypes.SOLDIER_UP);
        assertNotNull(AnimationTypes.SOLDIER_LEFT);
        assertNotNull(AnimationTypes.SOLDIER_RIGHT);
        assertNotNull(AnimationTypes.SOLDIER_ATTACK);
    }

    @Test
    void testGetAtlasPath() {
        // Test that atlas paths are correctly set
        assertEquals("Player/Atlas/SoldierWalking3.atlas", AnimationTypes.SOLDIER_DOWN.getAtlasPath());
        assertEquals("Player/Atlas/HeroWalking.atlas", AnimationTypes.HERO_DOWN.getAtlasPath());
        assertEquals("Player/Atlas/OldWalking.atlas", AnimationTypes.OLD_DOWN.getAtlasPath());
        assertEquals("Player/Atlas/zombie.atlas", AnimationTypes.ZOMBIE_DOWN.getAtlasPath());
    }

    @Test
    void testGetFrameTime() {
        // Test that frame times are correctly set
        assertEquals(0.5f, AnimationTypes.SOLDIER_DOWN.getFrameTime());
        assertEquals(0.5f, AnimationTypes.HERO_DOWN.getFrameTime());
        assertEquals(0.5f, AnimationTypes.OLD_DOWN.getFrameTime());
        assertEquals(0.5f, AnimationTypes.ZOMBIE_DOWN.getFrameTime());
    }

    @Test
    void testGetRowIndex() {
        // Test that row indices are correctly set
        assertEquals(0, AnimationTypes.SOLDIER_DOWN.getRowIndex());
        assertEquals(3, AnimationTypes.SOLDIER_UP.getRowIndex());
        assertEquals(1, AnimationTypes.SOLDIER_LEFT.getRowIndex());
        assertEquals(2, AnimationTypes.SOLDIER_RIGHT.getRowIndex());
    }
} 