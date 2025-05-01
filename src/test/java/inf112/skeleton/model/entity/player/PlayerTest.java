package inf112.skeleton.model.entity.player;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import inf112.skeleton.model.GamePanel;
import inf112.skeleton.model.entity.enemy.Enemy;
import inf112.skeleton.model.entity.item.Item;
import inf112.skeleton.model.entity.item.ItemType;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class PlayerTest {

    @Mock GamePanel context;
    @Mock World world;
    @Mock Body body;
    @Mock Files files;
    @Mock FileHandle fileHandle;
    @Mock TextureAtlas characterAtlas;
    @Mock TextureAtlas attackAtlas;
    @Mock AtlasRegion atlasRegion;
    Player player;

    @BeforeEach
    void setUp() {
        // Initialize Mockito mocks
        MockitoAnnotations.openMocks(this);
        
        // Mock Gdx.app and Gdx.files
        Gdx.app = mock(Application.class);
        Gdx.files = files;
        
        // Mock file system
        when(files.internal(anyString())).thenReturn(fileHandle);
        
        // Mock texture atlas
        when(characterAtlas.findRegion(anyString())).thenReturn(atlasRegion);
        when(attackAtlas.findRegion(anyString())).thenReturn(atlasRegion);
        
        // Create real texture regions array
        TextureRegion[][] regions = new TextureRegion[1][3];
        regions[0][0] = atlasRegion;
        regions[0][1] = atlasRegion;
        regions[0][2] = atlasRegion;
        
        when(atlasRegion.split(anyInt(), anyInt())).thenReturn(regions);
        when(atlasRegion.getRegionWidth()).thenReturn(96);
        when(atlasRegion.getRegionHeight()).thenReturn(128);
        
        // Create a Player with initial health=100, damage=20, position (0,0)
        player = new Player(context, world, body, 100, 20, 0f, 0f, CharacterType.SOLDIER);
        // Reset static flag before each test
        Player.isDead = false;
    }

    @Test
    void healthReducesWhenTakingDamage() {
        assertTrue(player.getHealth() == 100, "Player should have 100 health at start");
        player.takeDamage(30);
        // Expect health to be reduced from 100 to 70
        assertEquals(70, player.getHealth(), "Health should be reduced by 30");
    }

    @Test
    void healthDoesNotGoNegative() {
        player.takeDamage(150);  // More damage than player's health (100)
        // Expect getHealth() to return 0 (not negative)
        assertEquals(0, player.getHealth(), "Health should not go below 0");
        // After lethal damage, player should be marked as dead
        assertFalse(player.alive, "Player should be 'alive = false' when health reaches 0");
        assertTrue(Player.isDead, "Static flag isDead should be true at death");
    }

    @Test
    void manaRegeneratesUpToMax() {
        player.setCurrentMana(50);
        player.setMaxMana(100);
        // Simulate time passing to regenerate mana
        for (int i = 0; i < 300; i++) {
            player.regenerateMana(i);
        }
        // Expect mana to have increased but not exceed maxMana
        int regeneratedMana = player.getCurrentMana();
        assertTrue(regeneratedMana > 50, "Mana skal ha økt etter regenerering");
        assertEquals(100, regeneratedMana, "Mana skal ikke overstige maksverdien (100)");
    }

    @Test
    void useHealthItemIncreasesHealth() {
        // **Bruk av helse-item:** Å bruke en helsegjenstand skal øke spillerens helse (opp til en øvre grense).
        // Skad spilleren litt først
        player.takeDamage(60);  // reduser helse til 40
        assertEquals(40, player.getHealth());
        // Opprett et helse-item og simuler at spilleren plukker det opp
        Item healthPotion = new Item(context, world, ItemType.HEALTH, player.getX(), player.getY());
        player.getInventory().pickUpItems(new Array<Item>(new Item[]{healthPotion}));
        // Bruk helse-itemet (antar at det finnes en metode for å bruke valgt item)
        player.getInventory().useSelectedItem();
        // Forvent at spillerens helse har økt, men ikke over opprinnelig verdi 100
        assertTrue(player.getHealth() > 40, "Helse skal øke ved bruk av helse-potion");
        assertTrue(player.getHealth() <= 100, "Helse skal ikke overstige opprinnelig maks (100) ved bruk av potion");
    }

    @Test
    void playerDiesWhenHealthDepletes() {
        player.takeDamage(100);  // Exactly lethal damage
        // Expect player to be marked as dead
        assertFalse(player.alive, "Player should not be alive after taking lethal damage");
        assertTrue(Player.isDead, "isDead flag should be set when player dies");
    }
}
