package inf112.skeleton.model.entity.item;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import inf112.skeleton.model.GamePanel;
import inf112.skeleton.model.map.Map;

/**
 * Factory class for creating items in the game.
 * Handles the creation of items based on map data or specific parameters.
 */
public class ItemFactory {
  private final GamePanel context;
  private final World world;

  /**
   * Constructs a new ItemFactory instance.
   *
   * @param context The game panel context
   * @param world The physics world where items will be created
   */
  public ItemFactory(GamePanel context, World world) {
    this.context = context;
    this.world = world;
  }

  /**
   * Creates a list of items based on the item spawn points defined in the map.
   *
   * @param map The map containing item spawn points
   * @return An array of created items
   */
  public Array<Item> createItemFromMap(Map map) {
    Array<Item> items = new Array<>();
    for (ItemSpawn spawn : map.getItemSpawn()) {
      Item item = new Item(context, world, spawn.itemType(), spawn.position().x * GamePanel.UNIT_SCALE, spawn.position().y * GamePanel.UNIT_SCALE);
      items.add(item);
    }
    return items;
  }
}
