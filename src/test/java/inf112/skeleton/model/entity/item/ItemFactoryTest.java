package inf112.skeleton.model.entity.item;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import inf112.skeleton.model.GamePanel;
import inf112.skeleton.model.map.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;

public class ItemFactoryTest {
    private ItemFactory itemFactory;
    private GamePanel mockGamePanel;
    private World mockWorld;
    private Map mockMap;

    @BeforeEach
    void setUp() {
        mockGamePanel = mock(GamePanel.class);
        mockWorld = mock(World.class);
        mockMap = mock(Map.class);
        itemFactory = new ItemFactory(mockGamePanel, mockWorld);
    }

    @Test
    void testConstructor() {
        assertNotNull(itemFactory, "ItemFactory should be initialized");
    }

    @Test
    void testCreateItemFromMap() {
        // Create mock item spawns
        ItemSpawn mockSpawn1 = mock(ItemSpawn.class);
        ItemSpawn mockSpawn2 = mock(ItemSpawn.class);
        
        // Set up mock spawn positions
        when(mockSpawn1.getPosition()).thenReturn(new com.badlogic.gdx.math.Vector2(10, 20));
        when(mockSpawn2.getPosition()).thenReturn(new com.badlogic.gdx.math.Vector2(30, 40));
        
        // Set up mock item types
        when(mockSpawn1.getItemType()).thenReturn(ItemType.HEALTH);
        when(mockSpawn2.getItemType()).thenReturn(ItemType.GOLD_SWORD);
        
        // Create array of spawns
        ArrayList<ItemSpawn> spawns = new ArrayList<>();
        spawns.add(mockSpawn1);
        spawns.add(mockSpawn2);
        
        // Set up map to return spawns
        when(mockMap.getItemSpawn()).thenReturn(spawns);
        
        // Create items
        Array<Item> items = itemFactory.createItemFromMap(mockMap);
        
        // Verify results
        assertNotNull(items, "Items array should not be null");
        assertEquals(2, items.size, "Should create 2 items");
        
        // Verify item positions (scaled by UNIT_SCALE)
        Item item1 = items.get(0);
        Item item2 = items.get(1);
        
        assertEquals(10 * GamePanel.UNIT_SCALE, item1.getX());
        assertEquals(20 * GamePanel.UNIT_SCALE, item1.getY());
        assertEquals(30 * GamePanel.UNIT_SCALE, item2.getX());
        assertEquals(40 * GamePanel.UNIT_SCALE, item2.getY());
    }

    @Test
    void testCreateItemFromMapWithNoSpawns() {
        // Set up map with no spawns
        when(mockMap.getItemSpawn()).thenReturn(new ArrayList<>());
        
        // Create items
        Array<Item> items = itemFactory.createItemFromMap(mockMap);
        
        // Verify results
        assertNotNull(items, "Items array should not be null");
        assertTrue(items.isEmpty(), "Should create no items when there are no spawns");
    }

    @Test
    void testCreateItem() {
        // Test item creation
    }

    @Test
    void testCreateItemWithType() {
        // Test item creation with specific type
    }

    @Test
    void testCreateRandomItem() {
        // Test random item creation
    }
} 