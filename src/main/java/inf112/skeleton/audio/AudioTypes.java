package inf112.skeleton.audio;

public enum AudioTypes {
    INTRO ("audio/music/intro.mp3", true, 0.3f),
    SELECT ("audio/soundEffects/select.mp3", false, 0.3f),
    HURT ("audio/soundEffects/hurt.mp3", false, 0.3f);

    private final String path;
    private final boolean isMusic;
    private final float volume;

    AudioTypes(String path, boolean isMusic, float volume) {
        this.path = path;
        this.isMusic = isMusic;
        this.volume = volume;
    }

    public String getPath() {
        return path;
    }

    public boolean isMusic() {
        return isMusic;
    }

    public float getVolume() {
        return volume;
    }

}
