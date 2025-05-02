package inf112.skeleton.model.entity.enemy;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import inf112.skeleton.BaseTest;
import inf112.skeleton.model.GamePanel;
import inf112.skeleton.model.map.Map;
import inf112.skeleton.model.entity.player.CharacterType;

class EnemyFactoryTest extends BaseTest {
    @Mock private GamePanel context;
    @Mock private World world;
    @Mock private Map map;
    @Mock private Body mockBody;
    
    private EnemyFactory enemyFactory;
    private final float UNIT_SCALE = 32f; // Same as GamePanel.UNIT_SCALE

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        enemyFactory = new EnemyFactory(context, world);
        
        // Setup mock behavior
        when(world.createBody(any(BodyDef.class))).thenReturn(mockBody);
    }

    @Test
    void testCreateEnemiesFromMap() {
        // Setup mock map with enemy spawns
        ArrayList<EnemySpawn> spawns = new ArrayList<>();
        spawns.add(new EnemySpawn(new Vector2(1, 1), "Skeleton1", CharacterType.SKELETON));
        spawns.add(new EnemySpawn(new Vector2(2, 2), "Boss1", CharacterType.BOSS));
        when(map.getEnemySpawn()).thenReturn(spawns);

        // Create enemies from map
        Array<Enemy> enemies = enemyFactory.createEnemiesFromMap(map);

        // Verify results
        assertEquals(2, enemies.size, "Should create 2 enemies from map");
        
        // Verify first enemy
        Enemy skeleton = enemies.get(0);
        assertEquals("Skeleton1", skeleton.getName());
        assertEquals(CharacterType.SKELETON, skeleton.getCharacterType());
        
        // Verify second enemy
        Enemy boss = enemies.get(1);
        assertEquals("Boss1", boss.getName());
        assertEquals(CharacterType.BOSS, boss.getCharacterType());
    }

    @Test
    void testCreateEnemy() {
        // Create an enemy
        Enemy enemy = enemyFactory.createEnemy(10f, 20f, CharacterType.SKELETON, "TestSkeleton");

        // Verify enemy properties
        assertEquals("TestSkeleton", enemy.getName());
        assertEquals(CharacterType.SKELETON, enemy.getCharacterType());
        assertEquals(100, enemy.getHealth()); // Skeleton has 100 health
        assertEquals(25, enemy.getDamage()); // Skeleton has 25 damage
        assertEquals(8, enemy.getSightRange()); // Skeleton has 8 sight range

        // Verify body creation
        verify(world).createBody(any(BodyDef.class));
    }

    @Test
    void testCreateEnemyBody() {
        // Create an enemy body
        Body body = enemyFactory.createEnemyBody(10f, 20f);

        // Verify body creation
        verify(world).createBody(any(BodyDef.class));
        assertEquals(mockBody, body);
    }
}
