package inf112.skeleton.model.entity.enemy;

import com.badlogic.gdx.math.Vector2;

import inf112.skeleton.model.entity.player.CharacterType;

/**
 * Represents the spawn information for an enemy in the game.
 * This includes the enemy's position, name, and character type.
 *
 * @param position      The position where the enemy will spawn.
 * @param name          The name of the enemy.
 * @param characterType The type of character (e.g., normal enemy, boss).
 */
public record EnemySpawn(Vector2 position, String name, CharacterType characterType) {
}