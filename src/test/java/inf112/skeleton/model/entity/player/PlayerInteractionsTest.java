package inf112.skeleton.model.entity.player;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import inf112.skeleton.BaseTest;
import inf112.skeleton.audio.AudioHandler;
import inf112.skeleton.audio.AudioTypes;
import inf112.skeleton.model.GamePanel;
import inf112.skeleton.model.entity.enemy.Enemy;
import inf112.skeleton.model.entity.item.Item;
import inf112.skeleton.model.entity.item.ItemType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
/* 
class PlayerInteractionsTest extends BaseTest {
    private static final float ATTACK_RANGE = 2.0f;
    private static final float PICKUP_RANGE = 1.0f;

    
    private PlayerInteractions playerInteractions;

    @Mock
    private GamePanel mockGamePanel;
    @Mock
    private AudioHandler mockAudioHandler;
    @Mock
    private Player mockPlayer;
    @Mock
    private Enemy mockEnemy;
    @Mock
    private Item mockItem;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(mockGamePanel.getAudioHandler()).thenReturn(mockAudioHandler);

        playerInteractions = new PlayerInteractions(mockGamePanel, mockPlayer);

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

        // Verify that the enemy takes damage when in range
        verify(mockEnemy).takeDamage(10);

        // Verify that the attack sound is played
        verify(mockAudioHandler).playAudio(AudioTypes.ATTACK);
    }

    @Test
    void testAttackEnemyOutOfRange() {
        when(mockEnemy.getPosition()).thenReturn(new Vector2(3f, 0f)); // Out of range
        Array<Enemy> enemies = new Array<>();
        enemies.add(mockEnemy);

        playerInteractions.attackEnemy(mockPlayer, enemies);

        // Verify that the enemy does not take damage when out of range
        verify(mockEnemy, never()).takeDamage(anyInt());
    }
}*/