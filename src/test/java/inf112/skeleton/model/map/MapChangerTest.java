package inf112.skeleton.model.map;

import static org.mockito.Mockito.*;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import inf112.skeleton.BaseTest;
import inf112.skeleton.model.GamePanel;
import inf112.skeleton.model.entity.enemy.Enemy;
import inf112.skeleton.model.entity.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class MapChangerTest extends BaseTest {
    private MapChanger mapChanger;
    private static final float UNIT_SCALE = 1/32f;
    
    @Mock
    private World mockWorld;
    @Mock
    private Map mockMap;
    @Mock
    private Player mockPlayer;
    @Mock
    private Enemy mockEnemy1;
    @Mock
    private Enemy mockEnemy2;
    @Mock
    private Body mockBody1;
    @Mock
    private Body mockBody2;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mapChanger = new MapChanger();
        
        // Setup mock enemies
        when(mockEnemy1.getBody()).thenReturn(mockBody1);
        when(mockEnemy2.getBody()).thenReturn(mockBody2);
        
        // Setup mock map with coordinates in tiles (32x32 pixels)
        when(mockMap.getPlayerSpawn()).thenReturn(new Vector2(10, 20));
    }
    
    @Test
    void testRemoveObjects() {
        Array<Enemy> enemies = new Array<>();
        enemies.add(mockEnemy1);
        enemies.add(mockEnemy2);
        
        mapChanger.removeObjects(mockWorld, mockMap, enemies);
        
        // Verify that bodies were destroyed and enemies were disposed
        verify(mockWorld).destroyBody(mockBody1);
        verify(mockWorld).destroyBody(mockBody2);
        verify(mockEnemy1).dispose();
        verify(mockEnemy2).dispose();
    }
    
    @Test
    void testMovePlayer() {
        mapChanger.movePlayer(mockWorld, mockMap, mockPlayer);
        
        // Verify player was moved to spawn point (scaled by UNIT_SCALE)
        verify(mockPlayer).setSpawn(10 * UNIT_SCALE, 20 * UNIT_SCALE);
    }
} 