package inf112.skeleton.model.map;

import static inf112.skeleton.model.GamePanel.UNIT_SCALE;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import inf112.skeleton.model.entity.enemy.Enemy;
import inf112.skeleton.model.entity.player.Player;

/**
 * Handles map transitions and object management during map changes.
 * This class provides functionality to remove objects from the current map
 * and move the player to the spawn point of the new map.
 */
public class MapChanger {

    /**
     * Removes all enemies from the current map and disposes of their resources.
     * This method is called during map transitions to clean up the current map.
     *
     * @param world The Box2D world containing the physics bodies
     * @param map The current map being cleaned up
     * @param enemies The array of enemies to be removed
     */
    public void removeObjects(World world, Map map, Array<Enemy> enemies) {
        for (Enemy enemy : enemies) {
            if (enemy.getBody() != null) {
                world.destroyBody(enemy.getBody());
            }
            enemy.dispose();
        }
    }

    /**
     * Moves the player to the spawn point of the new map.
     * The spawn coordinates are automatically scaled by UNIT_SCALE.
     *
     * @param world The Box2D world containing the physics bodies
     * @param map The new map containing the spawn point
     * @param player The player to be moved
     */
    public void movePlayer(World world, Map map, Player player) {
        player.setSpawn(map.getPlayerSpawn().x * UNIT_SCALE, map.getPlayerSpawn().y * UNIT_SCALE);
    }
}
