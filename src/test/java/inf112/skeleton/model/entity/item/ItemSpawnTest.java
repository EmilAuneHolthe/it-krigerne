package inf112.skeleton.model.entity.item;

import static org.junit.jupiter.api.Assertions.*;

import com.badlogic.gdx.math.Vector2;

import org.junit.jupiter.api.Test;

public class ItemSpawnTest {
    @Test
    void testConstructor() {
        Vector2 position = new Vector2(1.5f, 2.5f);
        ItemType itemType = ItemType.HEALTH;
        
        ItemSpawn spawn = new ItemSpawn(position, itemType);
        
        assertEquals(position, spawn.position());
        assertEquals(itemType, spawn.itemType());
    }
    
    @Test
    void testEquals() {
        Vector2 position1 = new Vector2(1.5f, 2.5f);
        Vector2 position2 = new Vector2(1.5f, 2.5f);
        ItemType itemType = ItemType.HEALTH;
        
        ItemSpawn spawn1 = new ItemSpawn(position1, itemType);
        ItemSpawn spawn2 = new ItemSpawn(position2, itemType);
        
        assertEquals(spawn1, spawn2);
    }
    
    @Test
    void testNotEquals() {
        Vector2 position1 = new Vector2(1.5f, 2.5f);
        Vector2 position2 = new Vector2(3.0f, 4.0f);
        ItemType itemType1 = ItemType.HEALTH;
        ItemType itemType2 = ItemType.MANA;
        
        ItemSpawn spawn1 = new ItemSpawn(position1, itemType1);
        ItemSpawn spawn2 = new ItemSpawn(position2, itemType1);
        ItemSpawn spawn3 = new ItemSpawn(position1, itemType2);
        
        assertNotEquals(spawn1, spawn2);
        assertNotEquals(spawn1, spawn3);
    }
    
    @Test
    void testHashCode() {
        Vector2 position1 = new Vector2(1.5f, 2.5f);
        Vector2 position2 = new Vector2(1.5f, 2.5f);
        ItemType itemType = ItemType.HEALTH;
        
        ItemSpawn spawn1 = new ItemSpawn(position1, itemType);
        ItemSpawn spawn2 = new ItemSpawn(position2, itemType);
        
        assertEquals(spawn1.hashCode(), spawn2.hashCode());
    }
    
    @Test
    void testToString() {
        Vector2 position = new Vector2(1.5f, 2.5f);
        ItemType itemType = ItemType.HEALTH;
        
        ItemSpawn spawn = new ItemSpawn(position, itemType);
        String expected = "ItemSpawn[position=(1.5,2.5), itemType=HEALTH]";
        
        assertEquals(expected, spawn.toString());
    }
}
