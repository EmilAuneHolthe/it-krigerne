package inf112.skeleton.model.entity.item;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
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
      Item item = createItem(spawn.getPosition().x, spawn.getPosition().y, spawn.getItemType());
      items.add(item);
    }
    return items;
  }
  public Item createItem(float x, float y, ItemType itemType) {
    BodyDef bodyDef = new BodyDef();
    bodyDef.type = BodyDef.BodyType.DynamicBody;
    bodyDef.position.set(x, y);
    bodyDef.fixedRotation = true;
    
    Body body = world.createBody(bodyDef);
    
    PolygonShape shape = new PolygonShape();
    shape.setAsBox(0.3f, 0.3f);
    
    FixtureDef fixtureDef = new FixtureDef();
    fixtureDef.shape = shape;
    fixtureDef.density = 100f;
    fixtureDef.friction = 0.0f;
    fixtureDef.restitution = 0.0f;

    body.createFixture(fixtureDef);
    shape.dispose();
    
    return new Item(context, world, body, itemType);
  }
}
