package inf112.skeleton.model.entity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import inf112.skeleton.model.GamePanel;

public class PlayerTest {
    private Player player;
    private World world;
    private Body body;
    @BeforeEach
    public void setUp() {
        player = new Player(world, body, 0, 0, 0, 0);
        player.setHealth(100); // Initialize with default health
    }

    @Test
    public void testTakeDamage() {
        assertTrue(player.takeDamage(50)); // 
        assertEquals(50, player.getHealth());

        assertFalse(player.takeDamage(50)); // Health should drop to 0
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

    @Test
    public void testAttack() {
        int damage = player.attack();
        assertEquals(10, damage, "Attack damage should match the initialized value");
    }

    @Test
    public void testDie() {
        assertThrows(UnsupportedOperationException.class, () -> {
            player.die();
        }, "Die method should throw UnsupportedOperationException");
    }

    @Test
    public void testCreate() {
        player.create(150, 20, 10, 15);
        assertEquals(150, player.getHealth(), "Health should be set correctly");
        assertEquals(10, player.getX(), "X position should be set correctly");
        assertEquals(15, player.getY(), "Y position should be set correctly");
    }

    @Test
    public void testGetBody() {
        Body playerBody = player.getBody();
        assertSame(mockBody, playerBody, "Body should be the same as the mock");
    }
}
