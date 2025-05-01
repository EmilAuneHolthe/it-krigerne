package inf112.skeleton.model.map;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import inf112.skeleton.model.GamePanel;

class CollisionAreaTest {

    private CollisionArea collisionArea;
    private final float x = 10f;
    private final float y = 20f;
    private final float[] originalVertices = {0f, 0f, 10f, 0f, 10f, 10f, 0f, 10f};

    @BeforeEach
    void setUp() {
        collisionArea = new CollisionArea(x, y, originalVertices.clone());
    }

    @Test
    void testGetXReturnsScaledValue() {
        assertEquals(x * GamePanel.UNIT_SCALE, collisionArea.x(), 0.0001);
    }

    @Test
    void testGetYReturnsScaledValue() {
        assertEquals(y * GamePanel.UNIT_SCALE, collisionArea.y(), 0.0001);
    }

    @Test
    void testVerticesAreScaled() {
        float[] scaled = collisionArea.vertices();
        for (int i = 0; i < originalVertices.length; i++) {
            assertEquals(originalVertices[i] * GamePanel.UNIT_SCALE, scaled[i], 0.0001);
        }
    }
}
