package inf112.skeleton.model.entity.item;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import inf112.skeleton.BaseTest;
import inf112.skeleton.model.GamePanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

class ItemTest extends BaseTest {
    private Item item;
    private float testX = 10.0f;
    private float testY = 20.0f;
    
    @Mock
    private GamePanel mockGamePanel;
    @Mock
    private World mockWorld;
    @Mock
    private Texture mockTexture;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // Mock the static method getItemTexture
        try (MockedStatic<ItemType> itemTypeMock = mockStatic(ItemType.class)) {
            itemTypeMock.when(() -> ItemType.getItemTexture(any(ItemType.class))).thenReturn(mockTexture);
            itemTypeMock.when(() -> ItemType.getItemSize(any(ItemType.class))).thenReturn(1.0f);
            
            when(mockGamePanel.getItems()).thenReturn(new Array<>());
            item = new Item(mockGamePanel, mockWorld, ItemType.HEALTH, testX, testY);
        }
    }
    
    @Test
    void testGetPosition() {
        Vector2 position = item.getPosition();
        assertEquals(testX, position.x);
        assertEquals(testY, position.y);
    }
    
    @Test
    void testGetItemType() {
        assertEquals(ItemType.HEALTH, item.getItemType());
    }
    
    @Test
    void testRemove() {
        Array<Item> items = new Array<>();
        items.add(item);
        when(mockGamePanel.getItems()).thenReturn(items);
        
        item.remove();
        
        verify(mockGamePanel).getItems();
        assertTrue(items.isEmpty());
    }
    
    @Test
    void testUnsupportedOperations() {
        // Test that unsupported operations throw UnsupportedOperationException
        assertThrows(UnsupportedOperationException.class, () -> item.attack());
        assertThrows(UnsupportedOperationException.class, () -> item.die());
        assertThrows(UnsupportedOperationException.class, () -> item.isActive());
    }
} 