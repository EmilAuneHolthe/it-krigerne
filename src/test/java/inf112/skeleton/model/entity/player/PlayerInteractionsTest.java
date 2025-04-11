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
    private PlayerInteractions playerInteractions;
    
    @Mock
    private GamePanel mockGamePanel;
    @Mock
    private Player mockPlayer;
    @Mock
    private Enemy mockEnemy1;
    @Mock
    private Enemy mockEnemy2;
    @Mock
    private Item mockItem;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        playerInteractions = new PlayerInteractions(mockGamePanel);
        
        // Setup mock player
        when(mockPlayer.getPosition()).thenReturn(new Vector2(10, 10));
        mockPlayer.canAttack = true;
        when(mockPlayer.attack()).thenReturn(25);
        
        // Setup mock enemies
        when(mockEnemy1.getPosition()).thenReturn(new Vector2(11, 10)); // Within attack range
        when(mockEnemy1.getName()).thenReturn("Enemy1");
        when(mockEnemy2.getPosition()).thenReturn(new Vector2(15, 15)); // Outside attack range
        when(mockEnemy2.getName()).thenReturn("Enemy2");
        
        // Setup mock item
        when(mockItem.getPosition()).thenReturn(new Vector2(10.5f, 10)); // Within pickup range
        when(mockItem.getItemType()).thenReturn(ItemType.HEALTH);
    }
    
    @Test
    void testAttackEnemyInRange() {
        Array<Enemy> enemies = new Array<>();
        enemies.add(mockEnemy1);
        enemies.add(mockEnemy2);
        
        playerInteractions.attackEnemy(mockPlayer, enemies);
        
        // Only enemy1 should take damage since it's in range
        verify(mockEnemy1).takeDamage(25);
        verify(mockEnemy2, never()).takeDamage(anyInt());
    }
    
    @Test
    void testAttackEnemyOutOfRange() {
        Array<Enemy> enemies = new Array<>();
        enemies.add(mockEnemy2); // Only out-of-range enemy
        
        playerInteractions.attackEnemy(mockPlayer, enemies);
        
        // No enemies should take damage
        verify(mockEnemy2, never()).takeDamage(anyInt());
    }
    
    @Test
    void testAttackEnemyWhenCannotAttack() {
        mockPlayer.canAttack = false;
        Array<Enemy> enemies = new Array<>();
        enemies.add(mockEnemy1);
        
        playerInteractions.attackEnemy(mockPlayer, enemies);
        
        // No enemies should take damage when player can't attack
        verify(mockEnemy1, never()).takeDamage(anyInt());
    }
    
    @Test
    void testPickUpItemInRange() {
        Array<Item> items = new Array<>();
        items.add(mockItem);
        
        playerInteractions.pickUpItem(mockPlayer, items);
        
        // Item should be picked up and removed
        verify(mockPlayer).pickUpItem(ItemType.HEALTH);
        verify(mockItem).remove();
        assertTrue(items.isEmpty());
    }
    
    @Test
    void testPickUpItemOutOfRange() {
        when(mockItem.getPosition()).thenReturn(new Vector2(15, 15)); // Out of range
        Array<Item> items = new Array<>();
        items.add(mockItem);
        
        playerInteractions.pickUpItem(mockPlayer, items);
        
        // Item should not be picked up
        verify(mockPlayer, never()).pickUpItem(any(ItemType.class));
        verify(mockItem, never()).remove();
        assertEquals(1, items.size);
    }
} 