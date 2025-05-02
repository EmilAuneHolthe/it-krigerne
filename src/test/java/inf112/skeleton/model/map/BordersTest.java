package inf112.skeleton.model.map;

import static org.junit.jupiter.api.Assertions.*;
import static inf112.skeleton.model.GamePanel.UNIT_SCALE;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BordersTest {
    private Borders borders;
    private static final float X1 = 10f;
    private static final float Y1 = 20f;
    private static final float X2 = 30f;
    private static final float Y2 = 40f;
    private static final String NAME = "TestBorder";

    @BeforeEach
    void setUp() {
        borders = new Borders(X1, Y1, X2, Y2, NAME);
    }

    @Test
    void testInitialization() {
        // Test that coordinates are properly scaled
        assertEquals(X1 * UNIT_SCALE, borders.getX1());
        assertEquals(Y1 * UNIT_SCALE, borders.getY1());
        assertEquals(X2 * UNIT_SCALE, borders.getX2());
        assertEquals(Y2 * UNIT_SCALE, borders.getY2());
        
        // Test name and dimensions
        assertEquals(NAME, borders.getName());
        assertEquals(X2, borders.width);
        assertEquals(Y2, borders.height);
    }

    @Test
    void testIsInside() {
        // Test point inside the border (using scaled coordinates)
        float insideX = (X1 + X2) / 2 * UNIT_SCALE;
        float insideY = (Y1 + Y2) / 2 * UNIT_SCALE;
        assertEquals(NAME, borders.isInside(insideX * UNIT_SCALE, insideY * UNIT_SCALE));

        // Test point outside the border
        float outsideX = (X2 + 10) * UNIT_SCALE;
        float outsideY = (Y2 + 10) * UNIT_SCALE;
        assertNull(borders.isInside(outsideX * UNIT_SCALE, outsideY * UNIT_SCALE));

        // Test point on the border edge
        assertEquals(NAME, borders.isInside(X1 * UNIT_SCALE * UNIT_SCALE, Y1 * UNIT_SCALE * UNIT_SCALE));
        assertEquals(NAME, borders.isInside(X2 * UNIT_SCALE * UNIT_SCALE, Y2 * UNIT_SCALE * UNIT_SCALE));
    }

    @Test
    void testGetterMethods() {
        // Test all getter methods
        assertEquals(X1 * UNIT_SCALE, borders.getX1());
        assertEquals(Y1 * UNIT_SCALE, borders.getY1());
        assertEquals(X2 * UNIT_SCALE, borders.getX2());
        assertEquals(Y2 * UNIT_SCALE, borders.getY2());
        assertEquals(NAME, borders.getName());
        assertEquals(X2, borders.getWidth());
        assertEquals(Y2, borders.getHeight());
    }
}
