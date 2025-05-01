package inf112.skeleton.model.entity.door;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

class DoorTest {
    @Mock World world;
    @Mock Body body;
    @Mock AssetManager assets;
    @Mock Texture texture;
    @Mock SpriteBatch batch;
    private Door door;
    private Vector2 pos;
    private String name;
    private Vector2 size;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        
        // Initialize the necessary objects for the test
        pos = new Vector2(10, 20);
        name = "TestDoor";
        size = new Vector2(1, 1);
        
        // Setup mock behavior
        when(assets.get("map/Door1.png", Texture.class)).thenReturn(texture);
        when(body.getPosition()).thenReturn(new Vector2(10, 20));
        
        // Create a Door object
        door = new Door(body, name, size);
    }

    @Test
    void testPosition() {
        assertEquals(pos.x, door.getX());
        assertEquals(pos.y, door.getY());
        assertEquals(name, door.getName());
        assertEquals(size, door.getSize());
    }

    @Test
    void testRemoveDoor() {
        door.removeDoor(world);
        verify(world).destroyBody(body);
    }
}
  