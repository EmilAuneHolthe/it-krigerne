package inf112.skeleton.model.entity.item;

import com.badlogic.gdx.math.Vector2;

/**
 * Represents the spawn information for an item in the game.
 * This includes the item's position and type.
 *
 * @param position The position where the item will spawn
 * @param itemType The type of item to spawn
 */
public record ItemSpawn(Vector2 position, ItemType itemType) {
}
