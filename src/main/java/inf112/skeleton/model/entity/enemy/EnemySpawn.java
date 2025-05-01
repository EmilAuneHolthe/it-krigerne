package inf112.skeleton.model.entity.enemy;

import com.badlogic.gdx.math.Vector2;

import inf112.skeleton.model.entity.player.CharacterType;

public record EnemySpawn(Vector2 position, String name, CharacterType characterType) {
}