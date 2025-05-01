package inf112.skeleton.audio;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import inf112.skeleton.model.GamePanel;

/**
 * Handles audio playback for the game, including music and sound effects.
 * This class manages the playing, stopping, and switching of audio tracks.
 */
public class AudioHandler {
    private AudioTypes currentMusicType;
    private Music currentMusic;
    private final AssetManager assetManager;

    /**
     * Constructs an AudioHandler instance.
     *
     * @param context The GamePanel context, used to retrieve the AssetManager.
     */
    public AudioHandler(final GamePanel context) {
        this.assetManager = context.getAssetManager();
        currentMusic = null;
        currentMusicType = null;
    }

    /**
     * Plays the audio file of the given type.
     * If the type is music, it will stop the currently playing music (if any)
     * and start the new music. If the type is a sound effect, it will play the sound once.
     *
     * @param type The type of audio to play (music or sound effect).
     */
    public void playAudio(final AudioTypes type) {
        if (type.isMusic()) {
            if (currentMusicType == type) {
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

    /**
     * Stops the currently playing music, if any.
     * This method does not affect sound effects.
     */
    public void stopMusic() {
        if (currentMusic != null) {
            currentMusic.stop();
            currentMusic = null;
            currentMusicType = null;
        }
    }
}