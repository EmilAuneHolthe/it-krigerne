package inf112.skeleton.model.entity.item;

import com.badlogic.gdx.math.Vector2;

public class ItemSpawn {
  private final Vector2 position;
  private final ItemType itemType;

  public ItemSpawn(Vector2 position, ItemType itemType) {
    this.position = position;
    this.itemType = itemType;
  }

  public Vector2 getPosition() {
    return position;
  }

  public ItemType getItemType() {
    return itemType;
  }
}
