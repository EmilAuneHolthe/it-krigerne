package inf112.skeleton.model.entity;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.badlogic.gdx.physics.box2d.Body;

public class EnemyTest {
    private Enemy enemy;

    @BeforeEach
    public void setUp() {
        enemy = new Enemy(null, null, null, 0, 0, 0, 0);
        enemy.create(100, 10, 0, 0); // Initialize with default values
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

    @Test
    public void testAttack() {
        assertThrows(UnsupportedOperationException.class, () -> {
            enemy.attack();
        }, "Attack method should throw UnsupportedOperationException");
    }

    @Test
    public void testDie() {
        assertThrows(UnsupportedOperationException.class, () -> {
            enemy.die();
        }, "Die method should throw UnsupportedOperationException");
    }

    @Test
    public void testCreate() {
        enemy.create(120, 15, 8, 12);
        assertEquals(120, enemy.getHealth(), "Health should be set correctly");
        assertEquals(8, enemy.getX(), "X position should be set correctly");
        assertEquals(12, enemy.getY(), "Y position should be set correctly");
    }

    @Test
    public void testGetBody() {
        assertThrows(UnsupportedOperationException.class, () -> {
            enemy.getBody();
        }, "GetBody method should throw UnsupportedOperationException");
    }
}
