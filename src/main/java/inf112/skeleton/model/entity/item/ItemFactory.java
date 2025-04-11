package inf112.skeleton.model.entity.item;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import inf112.skeleton.model.GamePanel;
import inf112.skeleton.model.map.Map;

public class ItemFactory {
  private final GamePanel context;
  private final World world;

  public ItemFactory(GamePanel context, World world) {
    this.context = context;
    this.world = world;
  }
  public Array<Item> createItemFromMap(Map map) {
    Array<Item> items = new Array<>();
    for (ItemSpawn spawn : map.getItemSpawn()) {
      Item item = new Item(context, world, spawn.getItemType(), spawn.getPosition().x * GamePanel.UNIT_SCALE, spawn.getPosition().y * GamePanel.UNIT_SCALE);
      items.add(item);
    }
    return items;
  }
  }
