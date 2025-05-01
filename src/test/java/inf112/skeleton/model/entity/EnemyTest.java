package inf112.skeleton.model.entity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import inf112.skeleton.BaseTest;
import inf112.skeleton.model.GamePanel;
import inf112.skeleton.model.entity.enemy.Enemy;
import inf112.skeleton.model.entity.player.CharacterType;
import inf112.skeleton.model.entity.player.Player;

class EnemyTest extends BaseTest {
    @Mock private GamePanel context;
    @Mock private World world;
    @Mock private Body body;
    @Mock private Player player;
    @Mock private AssetManager assetManager;
    
    private Enemy enemy;
    private final String enemyName = "TestEnemy";
    private final CharacterType enemyType = CharacterType.SKELETON;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // Setup mock behavior
        when(context.getAssetManager()).thenReturn(assetManager);
        when(body.getPosition()).thenReturn(new Vector2(10, 10));
        Array<Enemy> enemies = new Array<>();
        when(context.getEnemy()).thenReturn(enemies);
        
        // Create enemy
        enemy = new Enemy(context, world, body, enemyType, enemyName);
    }

    @Test
    void testInitialization() {
        assertEquals(enemyName, enemy.getName());
        assertEquals(100, enemy.getHealth()); // Skeleton has 100 health
        assertEquals(25, enemy.getDamage()); // Skeleton has 25 damage
        assertEquals(8, enemy.getSightRange()); // Skeleton has 8 sight range
    }

    @Test
    void testTakeDamage() {
        // Test normal damage
        assertTrue(enemy.takeDamage(30));
        assertEquals(70, enemy.getHealth());

        // Test fatal damage
        assertFalse(enemy.takeDamage(80));
        assertEquals(0, enemy.getHealth());
        
        // Verify body was destroyed
        verify(world).destroyBody(body);
    }

    @Test
    void testMovement() {
        // Setup player position
        when(player.getPosition()).thenReturn(new Vector2(12, 12));
        
        // Test enemy movement towards player
        enemy.update(player);
        
        // Verify enemy is moving (has non-zero velocity)
        verify(body).setLinearVelocity(anyFloat(), anyFloat());
    }

    @Test
    void testNoMovementOutsideSightRange() {
        // Setup player position far away
        when(player.getPosition()).thenReturn(new Vector2(100, 100));
        
        // Test enemy movement
        enemy.update(player);
        
        // Verify enemy stops moving
        verify(body).setLinearVelocity(0, 0);
    }

    @Test
    void testPlayerDamageInRange() {
        // Setup player position very close
        when(player.getPosition()).thenReturn(new Vector2(10.5f, 10.5f));
        
        // Test enemy update
        enemy.update(player);
        
        // Verify player takes damage
        verify(player).playerTakeDamage(enemy);
    }

    @Test
    void testPosition() {
        Vector2 position = enemy.getPosition();
        assertEquals(10, position.x);
        assertEquals(10, position.y);
    }

    @Test
    void testSetPosition() {
        enemy.setPosition(20, 30);
        verify(body).setTransform(20, 30, 0);
    }
}
