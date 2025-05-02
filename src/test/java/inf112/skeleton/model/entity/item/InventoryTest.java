package inf112.skeleton.model.entity.item;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import inf112.skeleton.BaseTest;
import inf112.skeleton.model.GamePanel;
import inf112.skeleton.model.entity.player.Player;
import inf112.skeleton.audio.AudioHandler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class InventoryTest extends BaseTest {
    @Mock private Player mockPlayer;
    @Mock private GamePanel mockContext;
    @Mock private World mockWorld;
    @Mock private AudioHandler mockAudioHandler;
    @Mock private AssetManager mockAssetManager;
    @Mock private Texture mockTexture;

    private Inventory inventory;
    private static final int CAPACITY = 4;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Mock context methods
        when(mockContext.getAudioHandler()).thenReturn(mockAudioHandler);
        when(mockContext.getAssetManager()).thenReturn(mockAssetManager);

        // Set the asset manager in GamePanel
        GamePanel.assetManager = mockAssetManager;

        // Mock asset manager methods
        when(mockAssetManager.get(anyString())).thenReturn(mockTexture);

        // Mock player methods
        when(mockPlayer.getPosition()).thenReturn(new Vector2(0, 0));
        when(mockPlayer.getHealth()).thenReturn(50);
        when(mockPlayer.getMaxHealth()).thenReturn(100);
        when(mockPlayer.getMana()).thenReturn(50);
        when(mockPlayer.getMaxMana()).thenReturn(100);
        when(mockPlayer.getManaRegenRate()).thenReturn(1.0f);

        // Initialize inventory
        inventory = new Inventory(CAPACITY, mockPlayer, mockContext, mockWorld);
    }

    @Test
    void testConstructor() {
        assertEquals(CAPACITY, inventory.getCapacity());
        assertEquals(0, inventory.getItemCount());
        assertEquals(0, inventory.getSelectedItemIndex());
    }

    @Test
    void testPickUpItems() {
        // Create items with positions close to player
        Item item1 = new Item(mockContext, mockWorld, ItemType.HEALTH, 0, 0);
        Item item2 = new Item(mockContext, mockWorld, ItemType.MANA, 0, 0);

        Array<Item> items = new Array<>();
        Array<Item> contextItems = new Array<>();
        items.add(item1);
        items.add(item2);
        contextItems.add(item1);
        contextItems.add(item2);
        // Mock the context's items array
        when(mockContext.getItems()).thenReturn(contextItems);

        // Mock player position
        when(mockPlayer.getPosition()).thenReturn(new Vector2(0, 0));

        // Call pickUpItems
        inventory.pickUpItems(items);

        // Verify items were picked up
        assertEquals(2, inventory.getItemCount());


        // Verify item types in inventory
        Item firstItem = inventory.getItem(0);
        Item secondItem = inventory.getItem(1);
        assertNotNull(firstItem);
        assertNotNull(secondItem);
        assertEquals(ItemType.HEALTH, firstItem.getItemType());
        assertEquals(ItemType.MANA, secondItem.getItemType());
    }

    @Test
    void testPickUpItem() {
        inventory.pickUpItem(ItemType.HEALTH);
        Item item = inventory.getItem(0);
        assertNotNull(item);
        assertEquals(ItemType.HEALTH, item.getItemType());
        assertEquals(1, inventory.getItemCount());
    }

    @Test
    void testPickUpItemWhenFull() {
        // Fill inventory
        for (int i = 0; i < CAPACITY; i++) {
            inventory.pickUpItem(ItemType.HEALTH);
        }

        // Add one more item
        inventory.pickUpItem(ItemType.MANA);

        // Verify the selected slot was replaced
        Item item = inventory.getItem(inventory.getSelectedItemIndex());
        assertNotNull(item);
        assertEquals(ItemType.MANA, item.getItemType());
    }

    @Test
    void testUseSelectedItem_Health() {
        // Add health item
        inventory.pickUpItem(ItemType.HEALTH);
        inventory.selectItem(0);

        // Use item
        inventory.useSelectedItem();

        // Verify health was increased
<<<<<<< HEAD
        verify(mockPlayer).setHealth(100); // 50 + 50
=======
        verify(mockPlayer).setHealth(100); // 50 + 20
>>>>>>> 2afad3b (Endret litt av hvordan hud er)
    }

    @Test
    void testUseSelectedItem_Mana() {
        // Add mana item
        inventory.pickUpItem(ItemType.MANA);
        inventory.selectItem(0);

        // Use item
        inventory.useSelectedItem();

        // Verify mana was increased and regen rate updated
        verify(mockPlayer).setMana(100); // 50 + 20
        verify(mockPlayer).setManaRegenRate(11f); // 1.0 + 0.5
    }

    @Test
    void testUseSelectedItem_SwordUpgrade() {
        // Add sword upgrade item
        inventory.pickUpItem(ItemType.SWORD_UPGRADE);
        inventory.selectItem(0);

        // Use item
        inventory.useSelectedItem();

        // Verify sword upgrade effects
        verify(mockPlayer).increaseDamage(5);
        verify(mockPlayer).updateSwordHUDTexturePath("UncommonSword");
    }

    @Test
    void testUseSelectedItem_Key() {
        // Add key item
        inventory.pickUpItem(ItemType.KEY);
        inventory.selectItem(0);

        // Use item
        inventory.useSelectedItem();

        // Verify key effects
        verify(mockPlayer).setKey(true);
        verify(mockAudioHandler).playAudio(any());
    }

    @Test
    void testRemoveItem() {
        inventory.pickUpItem(ItemType.HEALTH);
        Item removed = inventory.removeItem(0);
        assertNotNull(removed);
        assertEquals(ItemType.HEALTH, removed.getItemType());
        assertEquals(0, inventory.getItemCount());
    }

    @Test
    void testSelectItem() {
        inventory.selectItem(2);
        assertEquals(2, inventory.getSelectedItemIndex());
    }

    @Test
    void testSelectItem_InvalidIndex() {
        inventory.selectItem(-1);
        assertEquals(0, inventory.getSelectedItemIndex()); // Should not change

        inventory.selectItem(CAPACITY);
        assertEquals(0, inventory.getSelectedItemIndex()); // Should not change
    }
}
