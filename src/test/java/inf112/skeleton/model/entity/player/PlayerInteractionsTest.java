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
    private Item mockItem1;
    @Mock
    private Item mockItem2;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        playerInteractions = new PlayerInteractions(mockGamePanel);
        
        // Set up player position and attack properties
        when(mockPlayer.getPosition()).thenReturn(new Vector2(0, 0));
        when(mockPlayer.attack()).thenReturn(10);
        mockPlayer.canAttack = true;
        
        // Set up enemy positions and names
        when(mockEnemy1.getPosition()).thenReturn(new Vector2(1, 1));
        when(mockEnemy1.getName()).thenReturn("Enemy1");
        when(mockEnemy2.getPosition()).thenReturn(new Vector2(3, 3));
        when(mockEnemy2.getName()).thenReturn("Enemy2");
        
        // Set up item positions and types
        when(mockItem1.getPosition()).thenReturn(new Vector2(0.5f, 0.5f));
        when(mockItem1.getItemType()).thenReturn(ItemType.HEALTH);
        when(mockItem2.getPosition()).thenReturn(new Vector2(2, 2));
        when(mockItem2.getItemType()).thenReturn(ItemType.MANA);
    }
    
    @Test
    void testAttackEnemy_WithinRange() {
        Array<Enemy> enemies = new Array<>();
        enemies.add(mockEnemy1);
        enemies.add(mockEnemy2);
        
        playerInteractions.attackEnemy(mockPlayer, enemies);
        
        verify(mockEnemy1).takeDamage(10);
        verify(mockEnemy2, never()).takeDamage(anyInt());
    }
    
    @Test
    void testAttackEnemy_OutOfRange() {
        when(mockEnemy1.getPosition()).thenReturn(new Vector2(3, 3));
        when(mockEnemy2.getPosition()).thenReturn(new Vector2(4, 4));
        
        Array<Enemy> enemies = new Array<>();
        enemies.add(mockEnemy1);
        enemies.add(mockEnemy2);
        
        playerInteractions.attackEnemy(mockPlayer, enemies);
        
        verify(mockEnemy1, never()).takeDamage(anyInt());
        verify(mockEnemy2, never()).takeDamage(anyInt());
    }
    
    @Test
    void testAttackEnemy_CannotAttack() {
        mockPlayer.canAttack = false;
        
        Array<Enemy> enemies = new Array<>();
        enemies.add(mockEnemy1);
        
        playerInteractions.attackEnemy(mockPlayer, enemies);
        
        verify(mockEnemy1, never()).takeDamage(anyInt());
    }
    
    @Test
    void testPickUpItem_WithinRange() {
        Array<Item> items = new Array<>();
        items.add(mockItem1);
        items.add(mockItem2);
        
        playerInteractions.pickUpItem(mockPlayer, items);
        
        verify(mockPlayer).pickUpItem(ItemType.HEALTH);
        verify(mockPlayer, never()).pickUpItem(ItemType.MANA);
    }
    
    @Test
    void testPickUpItem_OutOfRange() {
        when(mockItem1.getPosition()).thenReturn(new Vector2(2, 2));
        when(mockItem2.getPosition()).thenReturn(new Vector2(3, 3));
        
        Array<Item> items = new Array<>();
        items.add(mockItem1);
        items.add(mockItem2);
        
        playerInteractions.pickUpItem(mockPlayer, items);
        
        verify(mockPlayer, never()).pickUpItem(any());
    }
} 