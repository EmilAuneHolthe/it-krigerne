package inf112.skeleton.model.entity;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EnemyTest {
    private Enemy enemy;

    @BeforeEach
    public void setUp() {
        enemy = new Enemy();
        enemy.setHealth(100); // Initialize with default health
    }

    @Test
    public void testTakeDamage() {
        assertTrue(enemy.takeDamage(30)); // Health should not drop to 0
        assertEquals(70, enemy.getHealth());

        assertFalse(enemy.takeDamage(70)); // Health should drop to 0
        assertEquals(0, enemy.getHealth());
    }

    @Test
    public void testSetHealth() {
        enemy.setHealth(150);
        assertEquals(150, enemy.getHealth());
    }

    @Test
    public void testSetSpawn() {
        enemy.setSpawn(3, 7);
        assertEquals(3, enemy.getX());
        assertEquals(7, enemy.getY());
    }
}
