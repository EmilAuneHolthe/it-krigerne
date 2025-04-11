package inf112.skeleton.model.entity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.badlogic.gdx.physics.box2d.Body;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class entityTest {
    private TestEntity testEntity;
    
    @Mock
    private Body mockBody;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testEntity = new TestEntity(mockBody);
    }
    
    @Test
    void testAttack() {
        assertEquals(10, testEntity.attack());
    }
    
    @Test
    void testDie() {
        testEntity.setHealth(100);
        testEntity.die();
        assertEquals(0, testEntity.getHealth());
        assertTrue(testEntity.isRemoved());
    }
    
    @Test
    void testGetHealth() {
        testEntity.setHealth(50);
        assertEquals(50, testEntity.getHealth());
    }
    
    @Test
    void testTakeDamage() {
        testEntity.setHealth(100);
        assertTrue(testEntity.takeDamage(30));
        assertEquals(70, testEntity.getHealth());
        
        // Should return false when health drops to 0 or below
        assertFalse(testEntity.takeDamage(80));
        assertEquals(0, testEntity.getHealth());
    }
    
    @Test
    void testSetHealth() {
        testEntity.setHealth(75);
        assertEquals(75, testEntity.getHealth());
        
        testEntity.setHealth(0);
        assertEquals(0, testEntity.getHealth());
    }
    
    @Test
    void testSetSpawn() {
        testEntity.setSpawn(10.5f, 20.5f);
        assertEquals(10.5f, testEntity.getX());
        assertEquals(20.5f, testEntity.getY());
    }
    
    @Test
    void testGetPosition() {
        testEntity.setSpawn(15.0f, 25.0f);
        assertEquals(15.0f, testEntity.getX());
        assertEquals(25.0f, testEntity.getY());
    }
    
    @Test
    void testCreate() {
        testEntity.create(100, 20, 30.0f, 40.0f);
        assertEquals(100, testEntity.getHealth());
        assertEquals(20, testEntity.attack());
        assertEquals(30.0f, testEntity.getX());
        assertEquals(40.0f, testEntity.getY());
    }
    
    @Test
    void testGetBody() {
        assertSame(mockBody, testEntity.getBody());
    }
    
    /**
     * Test implementation of the entity interface
     */
    private static class TestEntity implements entity {
        private int health;
        private int damage;
        private float x;
        private float y;
        private boolean removed;
        private final Body body;
        
        public TestEntity(Body body) {
            this.body = body;
            this.damage = 10;
            this.removed = false;
        }
        
        @Override
        public int attack() {
            return damage;
        }
        
        @Override
        public void die() {
            health = 0;
            removed = true;
        }
        
        @Override
        public int getHealth() {
            return health;
        }
        
        @Override
        public boolean takeDamage(int damage) {
            health = Math.max(0, health - damage);
            return health > 0;
        }
        
        @Override
        public void setHealth(int health) {
            this.health = health;
        }
        
        @Override
        public void setSpawn(float x, float y) {
            this.x = x;
            this.y = y;
        }
        
        @Override
        public float getX() {
            return x;
        }
        
        @Override
        public float getY() {
            return y;
        }
        
        @Override
        public void create(int health, int damage, float x, float y) {
            this.health = health;
            this.damage = damage;
            this.x = x;
            this.y = y;
        }
        
        @Override
        public Body getBody() {
            return body;
        }
        
        public boolean isRemoved() {
            return removed;
        }
    }
} 