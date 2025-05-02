package inf112.skeleton.view.ui;

import org.junit.jupiter.api.Test;

import com.badlogic.gdx.graphics.g2d.Animation;

import inf112.skeleton.view.AnimationTypes;

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
        
        // Test other character types
        assertNotNull(AnimationTypes.KING_DOWN);
        assertNotNull(AnimationTypes.OLD_DOWN);
        assertNotNull(AnimationTypes.ZOMBIE_DOWN);
        assertNotNull(AnimationTypes.PUMPKIN_DOWN);
        assertNotNull(AnimationTypes.SKELETON_DOWN);
        assertNotNull(AnimationTypes.DARK_DOWN);
        assertNotNull(AnimationTypes.GHOST_DOWN);
        assertNotNull(AnimationTypes.BOSS_DOWN);
    }

    @Test
    void testGetAtlasPath() {
        // Test that atlas paths are correctly set
        assertEquals("Entities/Atlas/SoldierWalking3.atlas", AnimationTypes.SOLDIER_DOWN.getAtlasPath());
        assertEquals("Entities/Atlas/king.atlas", AnimationTypes.KING_DOWN.getAtlasPath());
        assertEquals("Entities/Atlas/OldWalking.atlas", AnimationTypes.OLD_DOWN.getAtlasPath());
        assertEquals("Entities/Atlas/zombie.atlas", AnimationTypes.ZOMBIE_DOWN.getAtlasPath());
        assertEquals("Entities/Atlas/Pumpkin.atlas", AnimationTypes.PUMPKIN_DOWN.getAtlasPath());
        assertEquals("Entities/Atlas/skeleton.atlas", AnimationTypes.SKELETON_DOWN.getAtlasPath());
        assertEquals("Entities/Atlas/Dark.atlas", AnimationTypes.DARK_DOWN.getAtlasPath());
        assertEquals("Entities/Atlas/Ghost.atlas", AnimationTypes.GHOST_DOWN.getAtlasPath());
        assertEquals("Entities/Atlas/Boss.atlas", AnimationTypes.BOSS_DOWN.getAtlasPath());
    }

    @Test
    void testGetFrameTime() {
        // Test that frame times are correctly set for all animation types
        for (AnimationTypes type : AnimationTypes.values()) {
            assertEquals(0.5f, type.getFrameTime());
        }
    }

    @Test
    void testGetRowIndex() {
        // Test that row indices are correctly set for soldier animations
        assertEquals(0, AnimationTypes.SOLDIER_DOWN.getRowIndex());
        assertEquals(3, AnimationTypes.SOLDIER_UP.getRowIndex());
        assertEquals(1, AnimationTypes.SOLDIER_LEFT.getRowIndex());
        assertEquals(2, AnimationTypes.SOLDIER_RIGHT.getRowIndex());
        
        // Test row indices for other character types
        assertEquals(0, AnimationTypes.KING_DOWN.getRowIndex());
        assertEquals(3, AnimationTypes.KING_UP.getRowIndex());
        assertEquals(1, AnimationTypes.KING_LEFT.getRowIndex());
        assertEquals(2, AnimationTypes.KING_RIGHT.getRowIndex());
        
        assertEquals(0, AnimationTypes.OLD_DOWN.getRowIndex());
        assertEquals(3, AnimationTypes.OLD_UP.getRowIndex());
        assertEquals(1, AnimationTypes.OLD_LEFT.getRowIndex());
        assertEquals(2, AnimationTypes.OLD_RIGHT.getRowIndex());
    }

    @Test
    void testGetAtlasKey() {
        // Test that atlas keys are correctly set
        assertEquals("Soldier", AnimationTypes.SOLDIER_DOWN.getAtlasKey());
        assertEquals("King", AnimationTypes.KING_DOWN.getAtlasKey());
        assertEquals("Old", AnimationTypes.OLD_DOWN.getAtlasKey());
        assertEquals("Zombie", AnimationTypes.ZOMBIE_DOWN.getAtlasKey());
        assertEquals("Pumpkin", AnimationTypes.PUMPKIN_DOWN.getAtlasKey());
        assertEquals("Skeleton", AnimationTypes.SKELETON_DOWN.getAtlasKey());
        assertEquals("Dark", AnimationTypes.DARK_DOWN.getAtlasKey());
        assertEquals("Ghost", AnimationTypes.GHOST_DOWN.getAtlasKey());
        assertEquals("Boss", AnimationTypes.BOSS_DOWN.getAtlasKey());
    }

    @Test
    void testAttackAnimations() {
        // Test that attack animations have correct properties
        assertEquals("Entities/Atlas/SoldierAttack.atlas", AnimationTypes.SOLDIER_ATTACK.getAtlasPath());
        assertEquals("PlayerAttacking", AnimationTypes.SOLDIER_ATTACK.getAtlasKey());
        assertEquals(0, AnimationTypes.SOLDIER_ATTACK.getRowIndex());
        
        // Test other attack animations
        assertEquals("Entities/Atlas/SoldierAttack.atlas", AnimationTypes.KING_ATTACK.getAtlasPath());
        assertEquals("Entities/Atlas/SoldierAttack.atlas", AnimationTypes.OLD_ATTACK.getAtlasPath());
        assertEquals("Entities/Atlas/SoldierAttack.atlas", AnimationTypes.ZOMBIE_ATTACK.getAtlasPath());
        assertEquals("Entities/Atlas/SoldierAttack.atlas", AnimationTypes.PUMPKIN_ATTACK.getAtlasPath());
        assertEquals("Entities/Atlas/SoldierAttack.atlas", AnimationTypes.SKELETON_ATTACK.getAtlasPath());
        assertEquals("Entities/Atlas/SoldierAttack.atlas", AnimationTypes.DARK_ATTACK.getAtlasPath());
        assertEquals("Entities/Atlas/SoldierAttack.atlas", AnimationTypes.GHOST_ATTACK.getAtlasPath());
        assertEquals("Entities/Atlas/Boss.atlas", AnimationTypes.BOSS_ATTACK.getAtlasPath());
    }
}
