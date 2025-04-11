package inf112.skeleton.audio;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AudioTypesTest {
    @Test
    void testAudioTypeEnumValues() {
        // Test that all enum values exist
        assertNotNull(AudioTypes.INTRO);
        assertNotNull(AudioTypes.SELECT);
        assertNotNull(AudioTypes.HURT);
    }

    @Test
    void testGetPath() {
        // Test that paths are correctly set
        assertEquals("audio/music/intro.mp3", AudioTypes.INTRO.getPath());
        assertEquals("audio/soundEffects/select.mp3", AudioTypes.SELECT.getPath());
        assertEquals("audio/soundEffects/hurt.mp3", AudioTypes.HURT.getPath());
    }

    @Test
    void testIsMusic() {
        // Test that music flags are correctly set
        assertTrue(AudioTypes.INTRO.isMusic());
        assertFalse(AudioTypes.SELECT.isMusic());
        assertFalse(AudioTypes.HURT.isMusic());
    }

    @Test
    void testGetVolume() {
        // Test that volumes are correctly set
        assertEquals(0.3f, AudioTypes.INTRO.getVolume());
        assertEquals(0.3f, AudioTypes.SELECT.getVolume());
        assertEquals(0.3f, AudioTypes.HURT.getVolume());
    }
} 