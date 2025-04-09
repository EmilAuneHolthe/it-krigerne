package inf112.skeleton.model.entity;

import com.badlogic.gdx.math.Vector2;

public class EnemySpawn {
    private final Vector2 position;
    private final String name;

    public EnemySpawn(Vector2 position, String name) {
        this.position = position;
        this.name = name;
    }

    public Vector2 getPosition() {
        return position;
    }

    public String getName() {
        return name;
    }
} 