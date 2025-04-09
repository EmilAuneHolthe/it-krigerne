package inf112.skeleton.audio;

import static org.mockito.Mockito.*;

import com.badlogic.gdx.audio.Music;

import inf112.skeleton.model.GamePanel;

import com.badlogic.gdx.assets.AssetManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AudioHandlerTest {
    
    private AudioHandler audioHandler;
    private AssetManager mockAssetManager;
    private Music mockMusic1;
    private Music mockMusic2;
    private GamePanel mockGamePanel;
    private AudioTypes mockAudioType1;
    private AudioTypes mockAudioType2;
    
    @BeforeEach
    void setUp() {
        mockAssetManager = mock(AssetManager.class);
        mockMusic1 = mock(Music.class);
        mockMusic2 = mock(Music.class);
        mockGamePanel = mock(GamePanel.class);
        mockAudioType1 = mock(AudioTypes.class);
        mockAudioType2 = mock(AudioTypes.class);
        
        when(mockGamePanel.getAssetManager()).thenReturn(mockAssetManager);
        when(mockAudioType1.isMusic()).thenReturn(true);
        when(mockAudioType2.isMusic()).thenReturn(true);
        when(mockAudioType1.getPath()).thenReturn("audio/music/test_music.mp3");
        when(mockAudioType2.getPath()).thenReturn("audio/music/new_music.mp3");
        when(mockAssetManager.get("audio/music/test_music.mp3", Music.class)).thenReturn(mockMusic1);
        when(mockAssetManager.get("audio/music/new_music.mp3", Music.class)).thenReturn(mockMusic2);
        
        audioHandler = new AudioHandler(mockGamePanel);
    }
    
    @Test
    void testPlayNewAudio() {
        audioHandler.playAudio(mockAudioType1);
        verify(mockMusic1, times(1)).play();
    }
    
    @Test
    void testSwitchMusicStopsPrevious() {
        audioHandler.playAudio(mockAudioType1);
        audioHandler.playAudio(mockAudioType2);
        
        verify(mockMusic1, times(1)).stop();
        verify(mockMusic2, times(1)).play();
    }
    
    @Test
    void testPlayingSameMusicDoesNothing() {
        audioHandler.playAudio(mockAudioType1);
        audioHandler.playAudio(mockAudioType1);
        
        verify(mockMusic1, times(1)).play(); 
    }
}
