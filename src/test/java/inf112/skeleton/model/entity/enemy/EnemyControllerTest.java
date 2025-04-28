package inf112.skeleton.model.entity.enemy;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import inf112.skeleton.model.GamePanel;
import inf112.skeleton.model.entity.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EnemyControllerTest {
    private EnemyController enemyController;
    private Player mockPlayer;
    private Array<Enemy> enemies;
    private Enemy mockEnemy;

    @BeforeEach
    void setUp() {
        mockPlayer = mock(Player.class);
        mockEnemy = mock(Enemy.class);
        
        enemies = new Array<>();
        enemies.add(mockEnemy);
        
        enemyController = new EnemyController(enemies, mockPlayer);
    }

    @Test
    void testConstructor() {
        assertNotNull(enemyController, "EnemyController should be initialized");
    }

    @Test
    void testUpdateEnemies() {
        // Given
        Array<Enemy> newEnemies = new Array<>();
        Enemy newEnemy = mock(Enemy.class);
        newEnemies.add(newEnemy);

        // When
        enemyController.updateEnemies(newEnemies);

        // Then
        assertEquals(1, enemyController.getEnemies().size, "Enemies array should have one enemy");
        assertSame(newEnemy, enemyController.getEnemies().first(), "The enemy should be the same as the one we added");
    }

    @Test
    void testSightWhenEnemyInRange() {
        // Given
        Vector2 enemyPosition = new Vector2(0, 0);
        Vector2 playerPosition = new Vector2(1, 0);
        
        when(mockEnemy.getPosition()).thenReturn(enemyPosition);
        when(mockPlayer.getPosition()).thenReturn(playerPosition);
        when(mockEnemy.getSightRange()).thenReturn(2);

        // When
        enemyController.sight();

        verify(mockEnemy).moveEnemy(playerPosition.x, playerPosition.y);
    }

    @Test
    void testSightWhenEnemyOutOfRange() {
        // Given
        Vector2 enemyPosition = new Vector2(0, 0);
        Vector2 playerPosition = new Vector2(10, 0);
        
        when(mockEnemy.getPosition()).thenReturn(enemyPosition);
        when(mockPlayer.getPosition()).thenReturn(playerPosition);
        when(mockEnemy.getSightRange()).thenReturn(2);

        // When
        enemyController.sight();

        // Then
        verify(mockEnemy).setLinearVelocity(0, 0);
        verify(mockEnemy, never()).moveEnemy(anyFloat(), anyFloat());
    }

    @Test
    void testSightWhenEnemyInAttackRange() {
        // Given
        Vector2 enemyPosition = new Vector2(0, 0);
        Vector2 playerPosition = new Vector2(0.5f, 0);
        
        when(mockEnemy.getPosition()).thenReturn(enemyPosition);
        when(mockPlayer.getPosition()).thenReturn(playerPosition);
        when(mockEnemy.getSightRange()).thenReturn(2);

        // When
        enemyController.sight();

        // Then
        verify(mockPlayer).playerTakeDamage(mockEnemy);
    }

    @Test
    void testUpdate() {
        // Test update method
    }

    @Test
    void testAddEnemy() {
        // Test enemy addition
    }

    @Test
    void testRemoveEnemy() {
        // Test enemy removal
    }

    @Test
    void testGetEnemies() {
        // Test enemies getter
    }
} 