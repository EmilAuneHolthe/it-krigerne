package inf112.skeleton.audio;

/**
 * Enum representing different types of audio used in the game.
 * Each audio type includes its file path, whether it is music or a sound effect, and its volume level.
 */
public enum AudioTypes {
    INTRO ("audio/music/intro.mp3", true, 0.3f),
    SELECT ("audio/soundEffects/select.mp3", false, 0.3f),
    HURT1 ("audio/soundEffects/hurt.mp3", false, 0.3f),
    HURT2 ("audio/soundEffects/Hurt2.mp3", false, 0.3f),
    VICTORY ("audio/soundEffects/Victory.mp3", false, 0.3f),
    ACHIEVMENT("audio/soundEffects/AchievementSound.mp3", false, 0.5f),
    ATTACK ("audio/soundEffects/SwordAttack.mp3", false, 0.1f),
    USE_ITEM ("audio/soundEffects/useItem.mp3", false, 0.3f),
    DOOR ("audio/soundEffects/Door.mp3", false, 0.3f),
    BONUS ("audio/soundEffects/Bonus.mp3", false, 0.1f),
    HIT ("audio/soundEffects/Hit.mp3", false, 1f),
    DEATH( "audio/soundEffects/Death.mp3", false, 0.5f);

    private final String path;
    private final boolean isMusic; 
    private final float volume;

    /**
     * Constructs an AudioTypes enum value.
     *
     * @param path    The file path to the audio resource.
     * @param isMusic True if the audio is music, false if it is a sound effect.
     * @param volume  The volume level of the audio (0.0f to 1.0f).
     */
    AudioTypes(String path, boolean isMusic, float volume) {
        this.path = path;
        this.isMusic = isMusic;
        this.volume = volume;
    }

    /**
     * Gets the file path of the audio resource.
     *
     * @return The file path as a string.
     */
    public String getPath() {
        return path;
    }

    /**
     * Checks if the audio type is music.
     *
     * @return True if the audio is music, false if it is a sound effect.
     */
    public boolean isMusic() {
        return isMusic;
    }

    /**
     * Gets the volume level of the audio.
     *
     * @return The volume level as a float (0.0f to 1.0f).
     */
    public float getVolume() {
        return volume;
    }
}
