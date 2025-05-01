package inf112.skeleton.model.entity.item;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import inf112.skeleton.model.GamePanel;
import inf112.skeleton.model.entity.GameEntity;

public class Item extends GameEntity{
  private final ItemType itemType;
  private final GamePanel context;
  private final Texture itemTexture;
  private final float x;
  private final float y;
  public Item(GamePanel context, World world, ItemType itemType, float x, float y) {
    super(context, world,itemType, x, y);
    this.itemType = itemType;
    this.context = context;
    this.world = world;
    this.itemTexture = ItemType.getItemTexture(itemType);
    this.x = x;
    this.y = y;
  }
  public void render(SpriteBatch batch) {
    float spriteWidth = ItemType.getItemSize(itemType);
    float spriteHeight = ItemType.getItemSize(itemType);
    batch.draw(itemTexture, x - spriteWidth / 2, y - spriteHeight / 2, spriteWidth, spriteHeight);
  }
  @Override
  public int attack() {
    
    throw new UnsupportedOperationException("Unimplemented method 'attack'");
  }
  @Override
  public void die() {
    
    throw new UnsupportedOperationException("Unimplemented method 'die'");
  }
  @Override
  protected boolean isActive() {
    throw new UnsupportedOperationException("Unimplemented method 'isActive'");
  }
  public ItemType getItemType() {
    return itemType;
  }
  public Vector2 getPosition() {
    return new Vector2(x, y);
  }
  public void remove() {
    context.getItems().removeValue(this, true);
  }
  public Texture getTexture() {
    return itemTexture;
  }
}
