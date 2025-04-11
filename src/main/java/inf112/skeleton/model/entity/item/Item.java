package inf112.skeleton.model.entity.item;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import inf112.skeleton.model.GamePanel;

public class Item{
  private ItemType itemType;
  private GamePanel context;
  private World world;
  private Body body;
  private Texture itemTexture;
  public Item(GamePanel context, World world, Body body, ItemType itemType) {
    this.itemType = itemType;
    this.context = context;
    this.world = world;
    this.body = body;
    this.itemTexture = ItemType.getItemTexture(itemType);
  }
  public void render(SpriteBatch batch) {
    batch.draw(itemTexture, body.getPosition().x, body.getPosition().y);
  }
}
