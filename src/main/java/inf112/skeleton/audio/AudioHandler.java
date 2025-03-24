package inf112.skeleton.audio;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import inf112.skeleton.model.GamePanel;

public class AudioHandler {
    private AudioTypes currentMusicType;
    private Music currentMusic;
    private final AssetManager assetManager;
    
    
    public AudioHandler(final GamePanel context) {
        this.assetManager = context.getAssetManager();
        currentMusic = null;
        currentMusicType = null;
    }
    
    /**
    * Plays the audio file of the given type.
    * 
    * @param type The type of audio to play.
    */
    
    public void playAudio(final AudioTypes type) {
        if(type.isMusic()) {
            if(currentMusicType == type) {
                return; // Do nothing if the same music is already playing
                
            } else if (currentMusic != null) {
                currentMusic.stop(); // Stop the current music if it is not the same as the new music
            }
            currentMusicType = type;
            currentMusic = assetManager.get(type.getPath(), Music.class);
            currentMusic.setLooping(true);
            currentMusic.setVolume(type.getVolume());
            currentMusic.play();
            
        } else {
            assetManager.get(type.getPath(), Sound.class).play(type.getVolume()); // Play the sound effect
        }
        
        
    }
    public void stopMusic() {
        if(currentMusic != null) {
            currentMusic.stop();
            currentMusic = null;
            currentMusicType = null;
        }
        
    }
}