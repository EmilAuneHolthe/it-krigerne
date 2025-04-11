package inf112.skeleton;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    @Test
    void testMainClassExists() {
        assertNotNull(Main.class, "Main class should exist");
    }

    @Test
    void testMainMethodExists() {
        try {
            Main.class.getMethod("main", String[].class);
        } catch (NoSuchMethodException e) {
            fail("Main class should have a main method");
        }
    }
}