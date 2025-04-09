package inf112.skeleton.model.entity;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.maps.MapProperties;

public class EnemySpawn {
    private final Vector2 position;
    private final String name;
    private final CharacterType characterType;

    public EnemySpawn(Vector2 position, String name, CharacterType characterType) {
        this.position = position;
        this.name = name;
        this.characterType = characterType;
    }

    public Vector2 getPosition() {
        return position;
    }

    public String getName() {
        return name;
    }
    public CharacterType getCharacterType() {
        return characterType;
    }
} 