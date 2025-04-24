package inf112.skeleton.model.entity.item;

import static org.junit.jupiter.api.Assertions.*;

import com.badlogic.gdx.graphics.Texture;
import inf112.skeleton.BaseTest;
import org.junit.jupiter.api.Test;

class ItemTypeTest extends BaseTest {
    
    @Test
    void testGetItemType() {
        assertEquals("health", ItemType.getItemType(ItemType.HEALTH));
        assertEquals("key", ItemType.getItemType(ItemType.KEY));
        assertEquals("attack", ItemType.getItemType(ItemType.GOLD_SWORD));
        assertEquals("mana", ItemType.getItemType(ItemType.MANA));
    }
    
    @Test
    void testGetItemAction() {
        assertEquals("Heal", ItemType.getItemAction(ItemType.HEALTH));
        assertEquals("key", ItemType.getItemAction(ItemType.KEY));
        assertEquals("AttackDMG", ItemType.getItemAction(ItemType.GOLD_SWORD));
        assertEquals("maxMana", ItemType.getItemAction(ItemType.MANA));
    }
    
    @Test
    void testGetItemSize() {
        assertEquals(0.5f, ItemType.getItemSize(ItemType.HEALTH));
        assertEquals(0.75f, ItemType.getItemSize(ItemType.KEY));
        assertEquals(1f, ItemType.getItemSize(ItemType.GOLD_SWORD));
        assertEquals(0.75f, ItemType.getItemSize(ItemType.MANA));
    }
    
    @Test
    void testGetItemTexture() {
        // Test that textures are loaded correctly
        assertNotNull(ItemType.getItemTexture(ItemType.HEALTH));
        assertNotNull(ItemType.getItemTexture(ItemType.KEY));
        assertNotNull(ItemType.getItemTexture(ItemType.GOLD_SWORD));
        assertNotNull(ItemType.getItemTexture(ItemType.MANA));
        
        // Test that textures are of the correct type
        assertTrue(ItemType.getItemTexture(ItemType.HEALTH) instanceof Texture);
        assertTrue(ItemType.getItemTexture(ItemType.KEY) instanceof Texture);
        assertTrue(ItemType.getItemTexture(ItemType.GOLD_SWORD) instanceof Texture);
        assertTrue(ItemType.getItemTexture(ItemType.MANA) instanceof Texture);
    }
} 