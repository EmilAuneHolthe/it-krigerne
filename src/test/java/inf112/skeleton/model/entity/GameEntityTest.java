package inf112.skeleton.model.entity;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import inf112.skeleton.model.GamePanel;
import inf112.skeleton.model.entity.player.CharacterType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameEntityTest {
    private GameEntity gameEntity;
    private GamePanel mockGamePanel;
    private World mockWorld;
    private Body mockBody;
    private CharacterType testCharacterType;

    @BeforeEach
    void setUp() {
        mockGamePanel = mock(GamePanel.class);
        mockWorld = mock(World.class);
        mockBody = mock(Body.class);
        testCharacterType = CharacterType.SOLDIER;
        
        // Create a concrete implementation of GameEntity for testing
        gameEntity = new GameEntity(mockGamePanel, mockWorld, mockBody, 100, 10, testCharacterType) {
            @Override
            protected boolean isActive() {
                return true;
            }

            @Override
            public int attack() {
                return damage;
            }

            @Override
            public void die() {
                health = 0;
            }
        };
    }

    @Test
    void testConstructor() {
        assertNotNull(gameEntity, "GameEntity should be initialized");
        assertEquals(100, gameEntity.getHealth());
        assertEquals(testCharacterType, gameEntity.getCharacterType());
    }

    @Test
    void testTakeDamage() {
        assertTrue(gameEntity.takeDamage(30), "Entity should survive 30 damage");
        assertEquals(70, gameEntity.getHealth());
        
        assertFalse(gameEntity.takeDamage(80), "Entity should die from 80 damage");
        assertEquals(0, gameEntity.getHealth());
    }

    @Test
    void testSetHealth() {
        gameEntity.setHealth(50);
        assertEquals(50, gameEntity.getHealth());
        
        gameEntity.setHealth(0);
        assertEquals(0, gameEntity.getHealth());
    }

    @Test
    void testSetSpawn() {
        gameEntity.setSpawn(10.5f, 20.5f);
        assertEquals(10.5f, gameEntity.getX());
        assertEquals(20.5f, gameEntity.getY());
    }

    @Test
    void testGetPosition() {
        gameEntity.setSpawn(15.0f, 25.0f);
        assertEquals(15.0f, gameEntity.getX());
        assertEquals(25.0f, gameEntity.getY());
    }

    @Test
    void testGetBody() {
        assertSame(mockBody, gameEntity.getBody());
    }

    @Test
    void testCreate() {
        gameEntity.create(200, 20, 30.0f, 40.0f);
        assertEquals(200, gameEntity.getHealth());
        assertEquals(30.0f, gameEntity.getX());
        assertEquals(40.0f, gameEntity.getY());
    }

    @Test
    void testAttack() {
        assertEquals(10, gameEntity.attack());
    }

    @Test
    void testDie() {
        gameEntity.setHealth(100);
        gameEntity.die();
        assertEquals(0, gameEntity.getHealth());
    }
} 