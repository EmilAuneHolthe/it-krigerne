package inf112.skeleton.model.entity.player;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import inf112.skeleton.BaseTest;
import inf112.skeleton.audio.AudioHandler;
import inf112.skeleton.audio.AudioTypes;
import inf112.skeleton.controller.KeyHandler;
import inf112.skeleton.model.GamePanel;
import inf112.skeleton.model.entity.enemy.Enemy;
import inf112.skeleton.model.entity.item.ItemType;
import inf112.skeleton.view.ui.DeathOverlay;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedConstruction;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class PlayerTest extends BaseTest {
    private Player player;
    
    @Mock private GamePanel gamePanel;
    @Mock private World world;
    @Mock private Body body;
    @Mock private KeyHandler keyHandler;
    @Mock private AudioHandler audioHandler;
    @Mock private Application app;
    @Mock private AssetManager assetManager;
    @Mock private Texture mockTexture;
    
    private static final int INITIAL_HEALTH = 100;
    private static final int INITIAL_DAMAGE = 10;
    private static final float INITIAL_X = 0;
    private static final float INITIAL_Y = 0;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // Setup Gdx.app mock
        Gdx.app = app;
        
        // Setup mocks
        when(gamePanel.getKeyHandler()).thenReturn(keyHandler);
        when(gamePanel.getAudioHandler()).thenReturn(audioHandler);
        when(body.getPosition()).thenReturn(new Vector2(INITIAL_X, INITIAL_Y));
        
        // Setup AssetManager mock
        GamePanel.assetManager = assetManager;
        when(assetManager.get(anyString(), eq(Texture.class))).thenReturn(mockTexture);
        
        // Mock DeathOverlay creation
        try (MockedConstruction<DeathOverlay> ignored = mockConstruction(DeathOverlay.class)) {
            // Create player
            player = new Player(gamePanel, world, body, INITIAL_HEALTH, INITIAL_DAMAGE, INITIAL_X, INITIAL_Y, CharacterType.SOLDIER);
            // Set alive to true
            player.alive = true;
        }
        
        // Reset static flag
        Player.isDead = false;
    }
    
    @Test
    void testInitialState() {
        assertEquals(INITIAL_HEALTH, player.getHealth());
        assertEquals(INITIAL_DAMAGE, player.attack());
        assertEquals(100, player.getMaxMana());
        assertEquals(100, player.getCurrentMana());
        assertFalse(player.hasKey());
        assertFalse(Player.isDead);
        assertTrue(player.canAttack);
    }
    
    @Test
    void testHealthManagement() {
        // Test damage taking
        Enemy mockEnemy = mock(Enemy.class);
        when(mockEnemy.getDamage()).thenReturn(20);
        
        player.playerTakeDamage(mockEnemy);
        assertEquals(80, player.getHealth());
        verify(audioHandler, times(1)).playAudio(AudioTypes.HURT2);
        verify(audioHandler, times(1)).playAudio(AudioTypes.HIT);
        
        // Test healing
        player.setHealth(100);
        assertEquals(100, player.getHealth());
        
        // Test death
        when(mockEnemy.getDamage()).thenReturn(200);
        player.playerTakeDamage(mockEnemy);
        assertTrue(Player.isDead);
        assertEquals(0, player.getHealth());
    }
    
    @Test
    void testManaSystem() {
        // Test mana usage
        player.setCurrentMana(20); // Use setCurrentMana instead of setMana
        assertEquals(20, player.getCurrentMana());
        player.regenerateMana(0.1f); // Small regeneration to trigger canAttack check
        assertFalse(player.canAttack); // Should be false when mana < 30
        
        // Test mana regeneration
        player.setManaRegenRate(10f);
        assertEquals(10f, player.getManaRegenRate());
        
        player.regenerateMana(1.0f); // Regenerate for 1 second
        assertTrue(player.getCurrentMana() > 20);
        
        // Test mana cap
        player.setCurrentMana(150); // Use setCurrentMana instead of setMana
        assertEquals(100, player.getCurrentMana()); // Should be capped at maxMana
    }
    
    @Test
    void testItemManagement() {
        assertFalse(player.hasKey());
        
        player.setKey(true);
        assertTrue(player.hasKey());
        
        player.removeKey();
        assertFalse(player.hasKey());
        
        // Test inventory
        player.ItemPickup(ItemType.SWORD_UPGRADE);
        assertNotNull(player.getInventory());
    }
    
    @Test
    void testMovementAndPosition() {
        float newX = 10f;
        float newY = 20f;
        
        player.setSpawn(newX, newY);
        verify(body).setLinearVelocity(0, 0);
        verify(body).setTransform(newX, newY, 0);
        
        // Test position getters
        when(body.getPosition()).thenReturn(new Vector2(newX, newY));
        assertEquals(newX / 32f, player.getX());
        assertEquals(newY / 32f, player.getY());
        
        Vector2 position = player.getPosition();
        assertEquals(newX, position.x);
        assertEquals(newY, position.y);
    }
    
    @Test
    void testDamageModification() {
        int initialDamage = player.attack();
        player.increaseDamage(5);
        assertEquals(initialDamage + 5, player.attack());
    }
    
    @Test
    void testDeathHandling() {
        assertFalse(Player.isDead);
        player.killPlayer();
        assertTrue(Player.isDead);
        
        // Test that health cannot go below 0
        player.setHealth(-10);
        assertEquals(0, player.getHealth());
    }
    
    @Test
    void testSwordEquipping() {
        String swordType = "UncommonSword";
        player.updateSwordHUDTexturePath(swordType);
        verify(gamePanel).updateEquippedSwordHUD(ItemType.getSwordHUDTexturePath(swordType));
    }
}
