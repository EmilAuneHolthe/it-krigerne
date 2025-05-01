package inf112.skeleton.app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class WorldFunctionsTest {
    private World world;

    @BeforeEach
    void setUp() {
        // Initialize world with test configuration
        world = new World();
    }

    @Test
    void testCollisionDetection() {
        // Test that player cannot move through solid objects
        Player player = world.getPlayer();
        int initialX = player.getX();
        int initialY = player.getY();
        
        // Attempt to move player into a wall
        player.move(1, 0); // Move right
        world.update();
        
        // Player should not have moved if there was a wall
        assertEquals(initialX, player.getX());
        assertEquals(initialY, player.getY());
    }

    @Test
    void testEnemySpawn() {
        // Test that enemies spawn correctly in the world
        int initialEnemyCount = world.getEnemies().size();
        
        // Trigger enemy spawn
        world.spawnEnemy(100, 100);
        
        assertEquals(initialEnemyCount + 1, world.getEnemies().size());
    }

    @Test
    void testItemCollection() {
        // Test that items can be collected by the player
        Player player = world.getPlayer();
        Item item = new Item(ItemType.HEALTH_POTION, 100, 100);
        world.addItem(item);
        
        // Move player to item position
        player.setPosition(100, 100);
        world.update();
        
        // Item should be collected and removed from world
        assertFalse(world.getItems().contains(item));
        assertTrue(player.getInventory().contains(item));
    }
} 