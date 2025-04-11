// package inf112.skeleton.model.entity.enemy;

// import static org.junit.jupiter.api.Assertions.*;
// import static org.mockito.Mockito.*;

// import com.badlogic.gdx.Gdx;
// import com.badlogic.gdx.graphics.Texture;
// import com.badlogic.gdx.math.Vector2;
// import com.badlogic.gdx.physics.box2d.*;
// import com.badlogic.gdx.utils.Array;
// import inf112.skeleton.BaseTest;
// import inf112.skeleton.model.GamePanel;
// import inf112.skeleton.model.entity.GameEntity;
// import inf112.skeleton.model.entity.player.CharacterType;
// import inf112.skeleton.model.entity.player.PlayerAnimation;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.Mock;
// import org.mockito.MockitoAnnotations;

// class EnemyTest extends BaseTest {
//     private Enemy enemy;
    
//     @Mock
//     private GamePanel mockGamePanel;
//     @Mock
//     private World mockWorld;
//     @Mock
//     private Body mockBody;
//     @Mock
//     private Array<Enemy> mockEnemyArray;
    
//     @BeforeEach
//     void setUp() {
//         MockitoAnnotations.openMocks(this);
        
//         // Mock body position
//         when(mockBody.getPosition()).thenReturn(new Vector2(0, 0));
        
//         // Mock GamePanel enemy array
//         when(mockGamePanel.getEnemy()).thenReturn(mockEnemyArray);
//         when(mockEnemyArray.indexOf(any(), eq(true))).thenReturn(0);
        
//         // Create enemy instance
//         enemy = new Enemy(mockGamePanel, mockWorld, mockBody, CharacterType.SKELETON, "TestEnemy");
//     }
    
//     @Test
//     void testInitialization() {
//         assertEquals("TestEnemy", enemy.getName());
//         assertEquals(100, enemy.getHealth()); // Skeleton health is 100
//         assertEquals(25, enemy.getDamage()); // Skeleton damage is 25
//         assertEquals(8, enemy.getSightRange()); // Skeleton sight range is 8
//         assertTrue(enemy.isActive());
//     }
    
//     @Test
//     void testTakeDamage() {
//         int initialHealth = enemy.getHealth();
//         int damage = 30;
        
//         boolean isAlive = enemy.takeDamage(damage);
        
//         assertTrue(isAlive);
//         assertEquals(initialHealth - damage, enemy.getHealth());
//     }
    
//     @Test
//     void testTakeDamageAndDie() {
//         boolean isAlive = enemy.takeDamage(200); // Deal more damage than max health
        
//         assertFalse(isAlive);
//         assertEquals(0, enemy.getHealth());
//         verify(mockWorld).destroyBody(mockBody);
//         verify(mockEnemyArray).removeIndex(0);
//     }
    
//     @Test
//     void testSetPosition() {
//         float newX = 10.0f;
//         float newY = 20.0f;
        
//         enemy.setPosition(newX, newY);
        
//         verify(mockBody).setTransform(newX, newY, 0);
//     }
    
//     @Test
//     void testMoveEnemy() {
//         float targetX = 3.0f;
//         float targetY = 4.0f;
//         Vector2 currentPos = new Vector2(0, 0);
//         Vector2 direction = new Vector2(targetX, targetY).sub(currentPos).nor();
//         float speed = 2.0f;
        
//         enemy.moveEnemy(targetX, targetY);
        
//         verify(mockBody).setLinearVelocity(direction.x * speed, direction.y * speed);
//     }
    
//     @Test
//     void testSetHealth() {
//         int newHealth = 50;
//         enemy.setHealth(newHealth);
//         assertEquals(newHealth, enemy.getHealth());
//     }
    
//     @Test
//     void testGetMaxHealth() {
//         assertEquals(100, enemy.getMaxHealth()); // Skeleton max health is 100
//     }
    
//     @Test
//     void testSetLinearVelocity() {
//         float velocityX = 1.0f;
//         float velocityY = 2.0f;
        
//         enemy.setLinearVelocity(velocityX, velocityY);
        
//         verify(mockBody).setLinearVelocity(velocityX, velocityY);
//     }
    
//     @Test
//     void testGetPosition() {
//         Vector2 position = new Vector2(1.0f, 2.0f);
//         when(mockBody.getPosition()).thenReturn(position);
        
//         Vector2 result = enemy.getPosition();
        
//         assertEquals(position.x, result.x);
//         assertEquals(position.y, result.y);
//     }
    
//     @Test
//     void testUnsupportedOperations() {
//         assertThrows(UnsupportedOperationException.class, () -> enemy.attack());
//         assertThrows(UnsupportedOperationException.class, () -> enemy.die());
//     }
// } 