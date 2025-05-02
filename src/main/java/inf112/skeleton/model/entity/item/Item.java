package inf112.skeleton.model.entity.item;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import inf112.skeleton.model.GamePanel;
import inf112.skeleton.model.entity.GameEntity;

/**
 * Represents an item entity in the game.
 * Items can be picked up by the player and provide various effects or benefits.
 * Extends GameEntity to inherit common entity functionality.
 */
public class Item extends GameEntity{
  private final ItemType itemType;
  private final GamePanel context;
  private final Texture itemTexture;
  private final float x;
  private final float y;

  /**
   * Constructs a new Item instance.
   *
   * @param context The game panel context
   * @param world The physics world
   * @param itemType The type of item
   * @param x The x-coordinate of the item
   * @param y The y-coordinate of the item
   */
  public Item(GamePanel context, World world, ItemType itemType, float x, float y) {
    super(context, world,itemType, x, y);
    this.itemType = itemType;
    this.context = context;
    this.world = world;
    this.itemTexture = ItemType.getItemTexture(itemType);
    this.x = x;
    this.y = y;
  }

  /**
   * Renders the item on the screen.
   *
   * @param batch The SpriteBatch to render with
   */
  public void render(SpriteBatch batch) {
    float spriteWidth = ItemType.getItemSize(itemType);
    float spriteHeight = ItemType.getItemSize(itemType);
    batch.draw(itemTexture, x - spriteWidth / 2, y - spriteHeight / 2, spriteWidth, spriteHeight);
  }

  /**
   * Unsupported operation for attacking.
   *
   * @throws UnsupportedOperationException Always thrown
   */
  @Override
  public int attack() {
    
    throw new UnsupportedOperationException("Unimplemented method 'attack'");
  }

  /**
   * Unsupported operation for dying.
   *
   * @throws UnsupportedOperationException Always thrown
   */
  @Override
  public void die() {
    
    throw new UnsupportedOperationException("Unimplemented method 'die'");
  }

  /**
   * Unsupported operation for checking active status.
   *
   * @throws UnsupportedOperationException Always thrown
   */
  @Override
  protected boolean isActive() {
    throw new UnsupportedOperationException("Unimplemented method 'isActive'");
  }

  /**
   * Gets the type of this item.
   *
   * @return The ItemType of this item
   */
  public ItemType getItemType() {
    return itemType;
  }

  /**
   * Gets the current position of the item.
   *
   * @return Vector2 containing the item's position
   */
  public Vector2 getPosition() {
    return new Vector2(x, y);
  }

  /**
   * Removes this item from the game world.
   */
  public void remove() {
    context.getItems().removeValue(this, true);
  }

  /**
   * Gets the texture used to render this item.
   *
   * @return The Texture of this item
   */
  public Texture getTexture() {
    return itemTexture;
  }
}
