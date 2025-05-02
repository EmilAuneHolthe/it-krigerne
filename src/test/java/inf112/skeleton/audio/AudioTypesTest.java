package inf112.skeleton.audio;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AudioTypesTest {
    @Test
    void testAudioTypeEnumValues() {
        // Test that all enum values exist
        assertNotNull(AudioTypes.INTRO);
        assertNotNull(AudioTypes.SELECT);
        assertNotNull(AudioTypes.HURT1);
        assertNotNull(AudioTypes.BOSSMUSIC);
    }

    @Test
    void testGetPath() {
        // Test that paths are correctly set
        assertEquals("audio/music/MainMusic.mp3", AudioTypes.INTRO.getPath());
        assertEquals("audio/soundEffects/select.mp3", AudioTypes.SELECT.getPath());
        assertEquals("audio/soundEffects/hurt.mp3", AudioTypes.HURT1.getPath());
        assertEquals("audio/music/BossMusic.mp3", AudioTypes.BOSSMUSIC.getPath());
    }

    @Test
    void testIsMusic() {
        // Test that music flags are correctly set
        assertTrue(AudioTypes.INTRO.isMusic());
        assertFalse(AudioTypes.SELECT.isMusic());
        assertFalse(AudioTypes.HURT1.isMusic());
        assertTrue(AudioTypes.BOSSMUSIC.isMusic());
    }

    @Test
    void testGetVolume() {
        // Test that volumes are correctly set
        assertEquals(0.1f, AudioTypes.INTRO.getVolume());
        assertEquals(0.3f, AudioTypes.SELECT.getVolume());
        assertEquals(0.3f, AudioTypes.HURT1.getVolume());
        assertEquals(0.2f, AudioTypes.BOSSMUSIC.getVolume());
    }
} 