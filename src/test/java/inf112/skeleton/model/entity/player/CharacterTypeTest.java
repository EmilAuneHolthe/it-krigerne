package inf112.skeleton.model.entity.player;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import inf112.skeleton.view.AnimationTypes;

public class CharacterTypeTest {
    @Test
    void testGetDefaultAnimation() {
        // Test each character type's default animation
        assertEquals(AnimationTypes.SOLDIER_DOWN, CharacterType.SOLDIER.getDefaultAnimation());
        assertEquals(AnimationTypes.OLD_DOWN, CharacterType.OLD.getDefaultAnimation());
        assertEquals(AnimationTypes.KING_DOWN, CharacterType.KING.getDefaultAnimation());
        assertEquals(AnimationTypes.ZOMBIE_DOWN, CharacterType.ZOMBIE.getDefaultAnimation());
        assertEquals(AnimationTypes.BOSS_DOWN, CharacterType.BOSS.getDefaultAnimation());
        assertEquals(AnimationTypes.SKELETON_DOWN, CharacterType.SKELETON.getDefaultAnimation());
        assertEquals(AnimationTypes.DARK_DOWN, CharacterType.DARK.getDefaultAnimation());
        assertEquals(AnimationTypes.PUMPKIN_DOWN, CharacterType.PUMPKIN.getDefaultAnimation());
        assertEquals(AnimationTypes.GHOST_DOWN, CharacterType.GHOST.getDefaultAnimation());
    }

    @Test
    void testValues() {
        // Test that all expected character types exist
        CharacterType[] types = CharacterType.values();
        assertEquals(9, types.length);
        
        // Verify each type is present
        assertTrue(containsType(types, CharacterType.SOLDIER));
        assertTrue(containsType(types, CharacterType.OLD));
        assertTrue(containsType(types, CharacterType.KING));
        assertTrue(containsType(types, CharacterType.ZOMBIE));
        assertTrue(containsType(types, CharacterType.BOSS));
        assertTrue(containsType(types, CharacterType.SKELETON));
        assertTrue(containsType(types, CharacterType.DARK));
        assertTrue(containsType(types, CharacterType.PUMPKIN));
        assertTrue(containsType(types, CharacterType.GHOST));
    }

    @Test
    void testValueOf() {
        // Test that each character type can be retrieved by name
        assertEquals(CharacterType.SOLDIER, CharacterType.valueOf("SOLDIER"));
        assertEquals(CharacterType.OLD, CharacterType.valueOf("OLD"));
        assertEquals(CharacterType.KING, CharacterType.valueOf("KING"));
        assertEquals(CharacterType.ZOMBIE, CharacterType.valueOf("ZOMBIE"));
        assertEquals(CharacterType.BOSS, CharacterType.valueOf("BOSS"));
        assertEquals(CharacterType.SKELETON, CharacterType.valueOf("SKELETON"));
        assertEquals(CharacterType.DARK, CharacterType.valueOf("DARK"));
        assertEquals(CharacterType.PUMPKIN, CharacterType.valueOf("PUMPKIN"));
        assertEquals(CharacterType.GHOST, CharacterType.valueOf("GHOST"));
    }

    private boolean containsType(CharacterType[] types, CharacterType type) {
        for (CharacterType t : types) {
            if (t == type) {
                return true;
            }
        }
        return false;
    }
}
