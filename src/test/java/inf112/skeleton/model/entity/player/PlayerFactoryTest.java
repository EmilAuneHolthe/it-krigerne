package inf112.skeleton.model.entity.player;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import inf112.skeleton.BaseTest;
import inf112.skeleton.model.GamePanel;
import inf112.skeleton.controller.KeyHandler;
import inf112.skeleton.model.entity.item.Inventory;
import inf112.skeleton.view.ui.DeathOverlay;

public class PlayerFactoryTest extends BaseTest {
    @Mock private GamePanel mockContext;
    @Mock private World mockWorld;
    @Mock private Body mockBody;
    @Mock private KeyHandler mockKeyHandler;
    @Mock private AssetManager mockAssetManager;
    @Mock private DeathOverlay mockDeathOverlay;
    @Mock private TextureAtlas mockCharacterAtlas;
    @Mock private TextureAtlas mockAttackAtlas;
    @Mock private AtlasRegion mockRegion;
    @Mock private FileHandle mockFileHandle;

    private PlayerFactory playerFactory;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // Mock context methods
        when(mockContext.getKeyHandler()).thenReturn(mockKeyHandler);
        when(mockContext.getAssetManager()).thenReturn(mockAssetManager);
        
        // Mock world methods
        when(mockWorld.createBody(any(BodyDef.class))).thenReturn(mockBody);
        
        // Set the asset manager in GamePanel
        GamePanel.assetManager = mockAssetManager;
        
        // Mock texture atlas methods
        when(mockCharacterAtlas.findRegion(anyString())).thenReturn(mockRegion);
        when(mockAttackAtlas.findRegion(anyString())).thenReturn(mockRegion);
        when(mockRegion.split(anyInt(), anyInt())).thenReturn(new TextureRegion[][]{{mockRegion}});
        
        // Mock file handle methods
        when(mockFileHandle.exists()).thenReturn(true);
        when(mockFileHandle.isDirectory()).thenReturn(false);
        
        playerFactory = new PlayerFactory(mockContext, mockWorld);
    }

    @Test
    void testCreatePlayer() {
        // Test parameters
        float x = 5.0f;
        float y = 10.0f;
        CharacterType characterType = CharacterType.SOLDIER;
        
        // Mock body position
        when(mockBody.getPosition()).thenReturn(new Vector2(x / GamePanel.UNIT_SCALE, y / GamePanel.UNIT_SCALE));
        
        // Mock DeathOverlay and PlayerMovement creation
        try (var mockedDeathOverlay = mockConstruction(DeathOverlay.class);
             var mockedPlayerMovement = mockConstruction(PlayerMovement.class)) {
            
            // Create player
            Player player = playerFactory.createPlayer(x, y, characterType);
            
            // Verify player properties
            assertNotNull(player);
            assertEquals(100, player.getHealth());
            assertEquals(characterType, player.getCharacterType());
            assertEquals(100, player.getMaxMana());
            assertEquals(100, player.getCurrentMana());
            assertFalse(player.hasKey());
            assertTrue(player.isActive());
            assertNotNull(player.getDeathOverlay());
        }
    }

    @Test
    void testCreatePlayerWithDifferentCharacterType() {
        // Test parameters
        float x = 5.0f;
        float y = 10.0f;
        
        // Mock body position
        when(mockBody.getPosition()).thenReturn(new Vector2(x / GamePanel.UNIT_SCALE, y / GamePanel.UNIT_SCALE));
        
        // Test with different character types
        for (CharacterType type : CharacterType.values()) {
            try (var mockedDeathOverlay = mockConstruction(DeathOverlay.class);
                 var mockedPlayerMovement = mockConstruction(PlayerMovement.class)) {
                
                Player player = playerFactory.createPlayer(x, y, type);
                
                assertNotNull(player);
                assertEquals(type, player.getCharacterType());
                assertEquals(100, player.getHealth());
                assertNotNull(player.getDeathOverlay());
            }
        }
    }

    @Test
    void testCreatePlayerWithDifferentPositions() {
        // Test different positions
        float[] xPositions = {0.0f, 5.0f, 10.0f};
        float[] yPositions = {0.0f, 5.0f, 10.0f};
        CharacterType characterType = CharacterType.SOLDIER;
        
        for (float x : xPositions) {
            for (float y : yPositions) {
                // Mock body position for each test case
                when(mockBody.getPosition()).thenReturn(new Vector2(x / GamePanel.UNIT_SCALE, y / GamePanel.UNIT_SCALE));
                
                try (var mockedDeathOverlay = mockConstruction(DeathOverlay.class);
                     var mockedPlayerMovement = mockConstruction(PlayerMovement.class)) {
                    
                    Player player = playerFactory.createPlayer(x, y, characterType);
                    
                    assertNotNull(player);
                    assertEquals(x, player.getX(), 0.001f);
                    assertEquals(y, player.getY(), 0.001f);
                    assertNotNull(player.getDeathOverlay());
                }
            }
        }
    }

    @Test
    void testCreatePlayerWithInventory() {
        // Test parameters
        float x = 5.0f;
        float y = 10.0f;
        CharacterType characterType = CharacterType.SOLDIER;
        
        // Mock body position
        when(mockBody.getPosition()).thenReturn(new Vector2(x / GamePanel.UNIT_SCALE, y / GamePanel.UNIT_SCALE));
        
        try (var mockedDeathOverlay = mockConstruction(DeathOverlay.class);
             var mockedPlayerMovement = mockConstruction(PlayerMovement.class)) {
            
            // Create player
            Player player = playerFactory.createPlayer(x, y, characterType);
            
            // Verify inventory
            Inventory inventory = player.getInventory();
            assertNotNull(inventory);
            assertEquals(4, inventory.getCapacity());
            assertEquals(0, inventory.getItemCount());
            assertNotNull(player.getDeathOverlay());
        }
    }
}
