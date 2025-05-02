package inf112.skeleton.model.entity.player;

import static org.mockito.Mockito.*;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import inf112.skeleton.BaseTest;
import inf112.skeleton.audio.AudioHandler;
import inf112.skeleton.audio.AudioTypes;
import inf112.skeleton.model.GamePanel;
import inf112.skeleton.model.entity.enemy.Enemy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


public class PlayerInteractionsTest extends BaseTest {
    @Mock private GamePanel mockContext;
    @Mock private Player mockPlayer;
    @Mock private Enemy mockEnemy1;
    @Mock private Enemy mockEnemy2;
    @Mock private Enemy mockEnemy3;
    @Mock private AudioHandler mockAudioHandler;
    @Mock private Vector2 mockPlayerPos;
    @Mock private Vector2 mockEnemy1Pos;
    @Mock private Vector2 mockEnemy2Pos;
    @Mock private Vector2 mockEnemy3Pos;

    private PlayerInteractions playerInteractions;
    private Array<Enemy> enemies;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // Setup audio handler
        when(mockContext.getAudioHandler()).thenReturn(mockAudioHandler);
        
        // Setup player mock
        doReturn(mockPlayerPos).when(mockPlayer).getPosition();
        doReturn(10).when(mockPlayer).attack();
        
        // Setup enemy mocks
        doReturn("Enemy1").when(mockEnemy1).getName();
        doReturn("Enemy2").when(mockEnemy2).getName();
        doReturn("Enemy3").when(mockEnemy3).getName();
        
        // Setup enemy positions
        doReturn(mockEnemy1Pos).when(mockEnemy1).getPosition();
        doReturn(mockEnemy2Pos).when(mockEnemy2).getPosition();
        doReturn(mockEnemy3Pos).when(mockEnemy3).getPosition();
        
        // Setup distance calculations
        doReturn(1.5f).when(mockEnemy1Pos).dst(mockPlayerPos); // Within range
        doReturn(3.0f).when(mockEnemy2Pos).dst(mockPlayerPos); // Out of range
        doReturn(1.8f).when(mockEnemy3Pos).dst(mockPlayerPos); // Within range
        
        // Create enemies array
        enemies = new Array<>();
        enemies.add(mockEnemy1, mockEnemy2, mockEnemy3);
        
        playerInteractions = new PlayerInteractions(mockContext, mockPlayer);
    }

    @Test
    void testAttackEnemyWithEnemiesInRange() {
        // Setup
        mockPlayer.canAttack = true;
        
        // Execute
        playerInteractions.attackEnemy(mockPlayer, enemies);
        
        // Verify
        verify(mockAudioHandler).playAudio(AudioTypes.ATTACK);
        verify(mockEnemy1).takeDamage(10);
        verify(mockEnemy3).takeDamage(10);
        verify(mockEnemy2, never()).takeDamage(anyInt());
    }

    @Test
    void testAttackEnemyWhenCannotAttack() {
        // Setup
        mockPlayer.canAttack = false;
        
        // Execute
        playerInteractions.attackEnemy(mockPlayer, enemies);
        
        // Verify
        verify(mockAudioHandler, never()).playAudio(any());
        verify(mockEnemy1, never()).takeDamage(anyInt());
        verify(mockEnemy2, never()).takeDamage(anyInt());
        verify(mockEnemy3, never()).takeDamage(anyInt());
    }

    @Test
    void testAttackEnemyWithNoEnemiesInRange() {
        // Setup
        mockPlayer.canAttack = true;
        doReturn(3.0f).when(mockEnemy1Pos).dst(mockPlayerPos);
        doReturn(4.0f).when(mockEnemy3Pos).dst(mockPlayerPos);
        
        // Execute
        playerInteractions.attackEnemy(mockPlayer, enemies);
        
        // Verify
        verify(mockAudioHandler).playAudio(AudioTypes.ATTACK);
        verify(mockEnemy1, never()).takeDamage(anyInt());
        verify(mockEnemy2, never()).takeDamage(anyInt());
        verify(mockEnemy3, never()).takeDamage(anyInt());
    }

    @Test
    void testAttackEnemyWithEmptyEnemyList() {
        // Setup
        mockPlayer.canAttack = true;
        Array<Enemy> emptyEnemies = new Array<>();
        
        // Execute
        playerInteractions.attackEnemy(mockPlayer, emptyEnemies);
        
        // Verify
        verify(mockAudioHandler).playAudio(AudioTypes.ATTACK);
        verify(mockEnemy1, never()).takeDamage(anyInt());
        verify(mockEnemy2, never()).takeDamage(anyInt());
        verify(mockEnemy3, never()).takeDamage(anyInt());
    }
}