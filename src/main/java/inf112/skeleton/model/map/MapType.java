package inf112.skeleton.model.map;

public enum MapType {
    MAP_START("map/SampleMap/samplemap.tmx"),
    MAP_BOSS("map/testMap/testMap.tmx"),
    MAP_CASTLE("map/SecondMap/SecondMap.tmx");

    private final String filePath;

    MapType(final String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }
}
