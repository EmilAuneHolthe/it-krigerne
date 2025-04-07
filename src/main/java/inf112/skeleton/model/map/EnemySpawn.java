package inf112.skeleton.model.map;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.maps.MapProperties;

public class EnemySpawn {
    private final Vector2 position;
    private final MapProperties properties;

    public EnemySpawn(Vector2 position, MapProperties properties) {
        this.position = position;
        this.properties = properties;
    }

    public Vector2 getPosition() {
        return position;
    }

    public MapProperties getProperties() {
        return properties;
    }
} 