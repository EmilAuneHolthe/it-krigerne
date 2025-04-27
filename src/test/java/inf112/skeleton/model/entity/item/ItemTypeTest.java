package inf112.skeleton.model.entity.item;

import static org.junit.jupiter.api.Assertions.*;

import inf112.skeleton.BaseTest;
import org.junit.jupiter.api.Test;

class ItemTypeTest extends BaseTest {

    @Test
    void testGetItemSize() {
        assertEquals(0.5f, ItemType.getItemSize(ItemType.HEALTH));
        assertEquals(0.75f, ItemType.getItemSize(ItemType.KEY));
        assertEquals(1f, ItemType.getItemSize(ItemType.ATTACK));
        assertEquals(0.75f, ItemType.getItemSize(ItemType.MANA));
        assertEquals(0.5f, ItemType.getItemSize(ItemType.DIAMOND_SWORD)); // Updated to match default behavior
        assertEquals(0.5f, ItemType.getItemSize(ItemType.EMERALD_SWORD)); // Updated to match default behavior
    }

    @Test
    void testGetItemType() {
        assertEquals("health", ItemType.getItemType(ItemType.HEALTH));
        assertEquals("key", ItemType.getItemType(ItemType.KEY));
        assertEquals("attack", ItemType.getItemType(ItemType.ATTACK));
        assertEquals("mana", ItemType.getItemType(ItemType.MANA));
        assertEquals("sword2", ItemType.getItemType(ItemType.DIAMOND_SWORD));
        assertEquals("sword", ItemType.getItemType(ItemType.EMERALD_SWORD));
    }

    @Test
    void testGetItemAction() {
        assertEquals("Heal", ItemType.getItemAction(ItemType.HEALTH));
        assertEquals("key", ItemType.getItemAction(ItemType.KEY));
        assertEquals("AttackDMG", ItemType.getItemAction(ItemType.ATTACK));
        assertEquals("maxMana", ItemType.getItemAction(ItemType.MANA));
    }

    @Test
    void testGetItemTexture() {
        assertNotNull(ItemType.getItemTexture(ItemType.HEALTH));
        assertNotNull(ItemType.getItemTexture(ItemType.KEY));
        assertNotNull(ItemType.getItemTexture(ItemType.ATTACK));
        assertNotNull(ItemType.getItemTexture(ItemType.MANA));
        assertNotNull(ItemType.getItemTexture(ItemType.DIAMOND_SWORD));
        assertNotNull(ItemType.getItemTexture(ItemType.EMERALD_SWORD));
    }
}