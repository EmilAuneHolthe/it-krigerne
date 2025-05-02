package inf112.skeleton.model.entity.taskBoard;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import inf112.skeleton.BaseTest;
import inf112.skeleton.model.GamePanel;

public class TaskBoardTest extends BaseTest {
    @Mock private AssetManager mockAssets;
    @Mock private Texture mockTexture;
    
    private TaskBoard taskBoard;
    private static final float TEST_X = 100f;
    private static final float TEST_Y = 200f;
    private static final float TEST_WIDTH = 32f;
    private static final float TEST_HEIGHT = 32f;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // Mock asset loading
        when(mockAssets.get("map/miniMap.png", Texture.class)).thenReturn(mockTexture);
        
        // Create TaskBoard instance
        taskBoard = new TaskBoard(TEST_X, TEST_Y, TEST_WIDTH, TEST_HEIGHT, mockAssets);
    }
    
    @Test
    void testTaskBoardInitialization() {
        // Verify asset was loaded
        verify(mockAssets).get("map/miniMap.png", Texture.class);
        
        // Verify initial state
        assertFalse(taskBoard.isActive());
        assertNotNull(taskBoard.getSprite());
    }
    
    @Test
    void testSpriteProperties() {
        Sprite sprite = taskBoard.getSprite();
        
        // Verify sprite position
        assertEquals(TEST_X, sprite.getX());
        assertEquals(TEST_Y, sprite.getY());
        
        // Verify sprite size (accounting for UNIT_SCALE and +2 adjustment)
        float expectedWidth = TEST_WIDTH * GamePanel.UNIT_SCALE + 2;
        float expectedHeight = TEST_HEIGHT * GamePanel.UNIT_SCALE + 2;
        assertEquals(expectedWidth, sprite.getWidth());
        assertEquals(expectedHeight, sprite.getHeight());
    }
    
    @Test
    void testActiveStateManagement() {
        // Test initial state
        assertFalse(taskBoard.isActive());
        
        // Test setting to active
        taskBoard.setActive(true);
        assertTrue(taskBoard.isActive());
        
        // Test setting back to inactive
        taskBoard.setActive(false);
        assertFalse(taskBoard.isActive());
    }
    
    @Test
    void testDispose() {
        // Execute dispose
        taskBoard.dispose();
        
        // Verify texture was disposed
        verify(mockTexture).dispose();
    }
    
    @Test
    void testGetSprite() {
        // Verify sprite is not null
        Sprite sprite = taskBoard.getSprite();
        assertNotNull(sprite);
        
        // Verify sprite has correct texture
        assertEquals(mockTexture, sprite.getTexture());
    }
}
