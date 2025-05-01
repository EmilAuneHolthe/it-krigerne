package inf112.skeleton.view;

import org.junit.jupiter.api.Test;

import com.badlogic.gdx.graphics.g2d.Animation;

import static org.junit.jupiter.api.Assertions.*;

class AnimationTypesTest {
    @Test
    void testAnimationTypeEnumValues() {
        // Test that all enum values exist
        assertNotNull(AnimationTypes.SOLDIER_DOWN);
        assertNotNull(AnimationTypes.SOLDIER_UP);
        assertNotNull(AnimationTypes.SOLDIER_LEFT);
        assertNotNull(AnimationTypes.SOLDIER_RIGHT);
        assertNotNull(AnimationTypes.SOLDIER_ATTACK);
    }

    // @Test
    // void testGetAtlasPath() {
    //     // Test that atlas paths are correctly set
    //     assertEquals("Player/Atlas/SoldierWalking3.atlas", AnimationTypes.SOLDIER_DOWN.getAtlasPath());
    //     assertEquals("Player/Atlas/king.atlas", AnimationTypes.KING_DOWN.getAtlasPath());
    //     assertEquals("Player/Atlas/OldWalking.atlas", AnimationTypes.OLD_DOWN.getAtlasPath());
    //     assertEquals("Player/Atlas/zombie.atlas", AnimationTypes.ZOMBIE_DOWN.getAtlasPath());
    //     assertEquals("Player/Atlas/Pumkin.atlas", AnimationTypes.PUMKIN_DOWN.getAtlasPath());
    //     assertEquals("Player/Atlas/skeleton.atlas", AnimationTypes.SKELETON_DOWN.getAtlasPath());
    //     assertEquals("Player/Atlas/Dark.atlas", AnimationTypes.DARK_DOWN.getAtlasPath());
    //     assertEquals("Player/Atlas/Ghost.atlas", AnimationTypes.GHOST_DOWN.getAtlasPath());
    //     assertEquals("Player/Atlas/Boss.atlas", AnimationTypes.BOSS_DOWN.getAtlasPath());
    // }

    @Test
    void testGetFrameTime() {
        // Test that frame times are correctly set
        assertEquals(0.5f, AnimationTypes.SOLDIER_DOWN.getFrameTime());
        assertEquals(0.5f, AnimationTypes.KING_DOWN.getFrameTime());
        assertEquals(0.5f, AnimationTypes.OLD_DOWN.getFrameTime());
        assertEquals(0.5f, AnimationTypes.ZOMBIE_DOWN.getFrameTime());
        assertEquals(0.5f, AnimationTypes.PUMKIN_DOWN.getFrameTime());
        assertEquals(0.5f, AnimationTypes.SKELETON_DOWN.getFrameTime());
        assertEquals(0.5f, AnimationTypes.DARK_DOWN.getFrameTime());
        assertEquals(0.5f, AnimationTypes.GHOST_DOWN.getFrameTime());
        assertEquals(0.5f, AnimationTypes.BOSS_DOWN.getFrameTime());
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
