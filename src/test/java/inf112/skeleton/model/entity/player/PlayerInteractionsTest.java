package inf112.skeleton.model.entity.player;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import inf112.skeleton.BaseTest;
import inf112.skeleton.model.GamePanel;
import inf112.skeleton.model.entity.enemy.Enemy;
import inf112.skeleton.model.entity.item.Item;
import inf112.skeleton.model.entity.item.ItemType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class PlayerInteractionsTest extends BaseTest {
    private static final float ATTACK_RANGE = 2.0f;
    private static final float PICKUP_RANGE = 1.0f;
    
    private PlayerInteractions playerInteractions;
    
    @Mock
    private GamePanel mockGamePanel;
    @Mock
    private Player mockPlayer;
    @Mock
    private Enemy mockEnemy;
    @Mock
    private Item mockItem;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        playerInteractions = new PlayerInteractions(mockGamePanel);
        
        // Setup mock player
        when(mockPlayer.getPosition()).thenReturn(new Vector2(0f, 0f));
        when(mockPlayer.attack()).thenReturn(10);
        mockPlayer.canAttack = true;
        
        // Setup mock enemy
        when(mockEnemy.getPosition()).thenReturn(new Vector2(1f, 0f));
        when(mockEnemy.getName()).thenReturn("TestEnemy");
        
        // Setup mock item
        when(mockItem.getPosition()).thenReturn(new Vector2(0.5f, 0f));
        when(mockItem.getItemType()).thenReturn(ItemType.HEALTH);
    }
    
    @Test
    void testAttackEnemyInRange() {
        Array<Enemy> enemies = new Array<>();
        enemies.add(mockEnemy);
        
        playerInteractions.attackEnemy(mockPlayer, enemies);
        
        verify(mockEnemy).takeDamage(10);
    }
    
    @Test
    void testAttackEnemyOutOfRange() {
        when(mockEnemy.getPosition()).thenReturn(new Vector2(3f, 0f));
        
        Array<Enemy> enemies = new Array<>();
        enemies.add(mockEnemy);
        
        playerInteractions.attackEnemy(mockPlayer, enemies);
        
        verify(mockEnemy, never()).takeDamage(anyInt());
    }
    
    @Test
    void testPickUpItemInRange() {
        Array<Item> items = new Array<>();
        items.add(mockItem);
        
        playerInteractions.pickUpItem(mockPlayer, items);
        
        verify(mockPlayer).pickUpItem(ItemType.HEALTH);
        verify(mockItem).remove();
        assertTrue(items.isEmpty());
    }
    
    @Test
    void testPickUpItemOutOfRange() {
        when(mockItem.getPosition()).thenReturn(new Vector2(2f, 0f));
        
        Array<Item> items = new Array<>();
        items.add(mockItem);
        
        playerInteractions.pickUpItem(mockPlayer, items);
        
        verify(mockPlayer, never()).pickUpItem(any());
        verify(mockItem, never()).remove();
        assertFalse(items.isEmpty());
    }
} 