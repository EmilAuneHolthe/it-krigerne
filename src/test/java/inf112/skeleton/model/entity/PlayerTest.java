package inf112.skeleton.model.entity;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PlayerTest {
    private Player player;

    @BeforeEach
    public void setUp() {
        player = new Player();
        player.setHealth(100); // Initialize with default health
    }

    @Test
    public void testTakeDamage() {
        assertFalse(player.takeDamage(50)); // Health should not drop to 0
        assertEquals(50, player.getHealth());

        assertTrue(player.takeDamage(50)); // Health should drop to 0
        assertEquals(0, player.getHealth());
    }

    @Test
    public void testSetHealth() {
        player.setHealth(200);
        assertEquals(200, player.getHealth());
    }

    @Test
    public void testSetSpawn() {
        player.setSpawn(5, 10);
        assertEquals(5, player.getX());
        assertEquals(10, player.getY());
    }
}
