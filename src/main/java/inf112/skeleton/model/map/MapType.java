package inf112.skeleton.model.map;

public enum MapType {
    MAP_1("map/SampleMap/samplemap.tmx"),
    MAP_2("map/testMap/testMap.tmx");

    private final String filePath;

    MapType(final String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }
}
