package inf112.skeleton.model.entity.enemy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import inf112.skeleton.model.entity.player.CharacterType;

class EnemyTypesTest {
    private EnemyTypes enemyTypes;

    @BeforeEach
    void setUp() {
        // Initialize test objects here
    }

    @Test
    void testEnemyTypeEnumValues() {
        // Test enemy type enum values
    }

    @Test
    void testGetHealth() {
        // Test health getter
    }

    @Test
    void testGetSpeed() {
        // Test speed getter
    }

    @Test
    void testGetDamage() {
        // Test damage getter
    }

    @Test
    void testGetName() {
        assertEquals("Skeleton", EnemyTypes.SKELETON.getName());
        assertEquals("Zombie", EnemyTypes.ZOMBIE.getName());
        assertEquals("Boss", EnemyTypes.BOSS.getName());
    }
    
    @Test
    void testFromName() {
        assertEquals(EnemyTypes.SKELETON, EnemyTypes.fromName("Skeleton"));
        assertEquals(EnemyTypes.ZOMBIE, EnemyTypes.fromName("Zombie"));
        assertEquals(EnemyTypes.BOSS, EnemyTypes.fromName("Boss"));
        
        // Test case-insensitive
        assertEquals(EnemyTypes.SKELETON, EnemyTypes.fromName("skeleton"));
        assertEquals(EnemyTypes.ZOMBIE, EnemyTypes.fromName("zombie"));
        assertEquals(EnemyTypes.BOSS, EnemyTypes.fromName("boss"));
        
        // Test invalid name
        assertThrows(IllegalArgumentException.class, () -> EnemyTypes.fromName("Invalid"));
    }
    
    @Test
    void testGetEnemyHealth() {
        assertEquals(100, EnemyTypes.getEnemyHealth(CharacterType.SKELETON));
        assertEquals(50, EnemyTypes.getEnemyHealth(CharacterType.ZOMBIE));
        assertEquals(500, EnemyTypes.getEnemyHealth(CharacterType.BOSS));
        assertThrows(IllegalArgumentException.class, () -> EnemyTypes.getEnemyHealth(null));
        assertThrows(IllegalArgumentException.class, () -> EnemyTypes.getEnemyHealth(CharacterType.SOLDIER));
    }
    
    @Test
    void testGetEnemyDamage() {
        assertEquals(25, EnemyTypes.getEnemyDamage(CharacterType.SKELETON));
        assertEquals(10, EnemyTypes.getEnemyDamage(CharacterType.ZOMBIE));
        assertEquals(50, EnemyTypes.getEnemyDamage(CharacterType.BOSS));
        assertThrows(IllegalArgumentException.class, () -> EnemyTypes.getEnemyDamage(null));
        assertThrows(IllegalArgumentException.class, () -> EnemyTypes.getEnemyDamage(CharacterType.SOLDIER));
    }
    
    // @Test
    // void testGetEnemySight() {
    //     assertEquals(8, EnemyTypes.getEnemySight(CharacterType.SKELETON));
    //     assertEquals(8, EnemyTypes.getEnemySight(CharacterType.ZOMBIE));
    //     assertEquals(1000, EnemyTypes.getEnemySight(CharacterType.BOSS));
    //     assertThrows(IllegalArgumentException.class, () -> EnemyTypes.getEnemySight(null));
    //     assertThrows(IllegalArgumentException.class, () -> EnemyTypes.getEnemySight(CharacterType.SOLDIER));
    // }
} 