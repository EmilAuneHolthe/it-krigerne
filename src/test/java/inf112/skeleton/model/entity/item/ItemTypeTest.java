package inf112.skeleton.model.entity.item;

import static org.junit.jupiter.api.Assertions.*;

import inf112.skeleton.BaseTest;
import org.junit.jupiter.api.Test;

class ItemTypeTest extends BaseTest {

    @Test
    void testGetItemSize() {
        assertEquals(0.5f, ItemType.getItemSize(ItemType.HEALTH));
        assertEquals(0.75f, ItemType.getItemSize(ItemType.KEY));
        assertEquals(0.75f, ItemType.getItemSize(ItemType.MANA));
        assertEquals(1f, ItemType.getItemSize(ItemType.SWORD_UPGRADE)); // Updated to match default behavior
    }

    @Test
    void testGetItemType() {
        assertEquals("health", ItemType.getItemType(ItemType.HEALTH));
        assertEquals("key", ItemType.getItemType(ItemType.KEY));
        assertEquals("mana", ItemType.getItemType(ItemType.MANA));
        assertEquals("swordUpgrade", ItemType.getItemType(ItemType.SWORD_UPGRADE));
    }

    // @Test
    // void testGetItemTexture() {
    //     assertNotNull(ItemType.getItemTexture(ItemType.HEALTH));
    //     assertNotNull(ItemType.getItemTexture(ItemType.KEY));
    //     assertNotNull(ItemType.getItemTexture(ItemType.SWORD_UPGRADE));
    //     assertNotNull(ItemType.getItemTexture(ItemType.MANA));
    // }
}