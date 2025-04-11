// package inf112.skeleton.model.entity.enemy;

// import static org.junit.jupiter.api.Assertions.*;
// import static org.mockito.Mockito.*;

// import java.util.ArrayList;
// import com.badlogic.gdx.math.Vector2;
// import com.badlogic.gdx.physics.box2d.*;
// import com.badlogic.gdx.utils.Array;
// import inf112.skeleton.BaseTest;
// import inf112.skeleton.model.GamePanel;
// import inf112.skeleton.model.entity.player.CharacterType;
// import inf112.skeleton.model.map.Map;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.Mock;
// import org.mockito.MockitoAnnotations;

// class EnemyFactoryTest extends BaseTest {
//     private EnemyFactory enemyFactory;
    
//     @Mock
//     private GamePanel mockGamePanel;
//     @Mock
//     private World mockWorld;
//     @Mock
//     private Map mockMap;
//     @Mock
//     private Body mockBody;
//     @Mock
//     private FixtureDef mockFixtureDef;
    
//     @BeforeEach
//     void setUp() {
//         MockitoAnnotations.openMocks(this);
//         enemyFactory = new EnemyFactory(mockGamePanel, mockWorld);
        
//         // Mock World behavior
//         when(mockWorld.createBody(any(BodyDef.class))).thenReturn(mockBody);
//     }
    
//     @Test
//     void testCreateEnemy() {
//         float x = 1.0f;
//         float y = 2.0f;
//         CharacterType type = CharacterType.SKELETON;
//         String name = "TestEnemy";
        
//         Enemy enemy = enemyFactory.createEnemy(x, y, type, name);
        
//         assertNotNull(enemy);
//         assertEquals(name, enemy.getName());
//         verify(mockWorld).createBody(any(BodyDef.class));
//         verify(mockBody).createFixture(any(FixtureDef.class));
//     }
    
//     @Test
//     void testCreateEnemiesFromMap() {
//         // Create test enemy spawns
//         ArrayList<EnemySpawn> spawns = new ArrayList<>();
//         spawns.add(new EnemySpawn(new Vector2(1, 1), "Enemy1", CharacterType.SKELETON));
//         spawns.add(new EnemySpawn(new Vector2(2, 2), "Enemy2", CharacterType.ZOMBIE));
        
//         when(mockMap.getEnemySpawn()).thenReturn(spawns);
        
//         Array<Enemy> enemies = enemyFactory.createEnemiesFromMap(mockMap);
        
//         assertEquals(2, enemies.size);
//         assertEquals("Enemy1", enemies.get(0).getName());
//         assertEquals("Enemy2", enemies.get(1).getName());
//         verify(mockWorld, times(2)).createBody(any(BodyDef.class));
//     }
    
//     @Test
//     void testCreateEnemyBody() {
//         float x = 1.0f;
//         float y = 2.0f;
        
//         Body body = enemyFactory.createEnemyBody(x, y);
        
//         assertNotNull(body);
//         verify(mockWorld).createBody(argThat(bodyDef -> 
//             bodyDef.type == BodyDef.BodyType.DynamicBody &&
//             bodyDef.position.x == x &&
//             bodyDef.position.y == y &&
//             bodyDef.fixedRotation
//         ));
//     }
// } 