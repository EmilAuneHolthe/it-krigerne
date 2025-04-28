package inf112.skeleton.model.entity.door;


import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import inf112.skeleton.model.GamePanel;

public class Door{
  Vector2 position;
  Vector2 size;
  World world;
  GamePanel context;
  Body body;
  String name;
  Sprite sprite;
  public Door(Vector2 pos, World world, Body body, String name, AssetManager assets,Vector2 size) {
      this.position = pos;
      this.size = size;
      this.world = world;
      this.name = name;
      this.body = body;
      sprite = new Sprite(assets.get("map/Door1.png", Texture.class));
      sprite.setSize(size.x, size.y);
      
    }
  public void render(SpriteBatch batch) {
    sprite.setPosition(body.getPosition().x, body.getPosition().y);
    sprite.draw(batch);
  }
  public void removeDoor() {
    world.destroyBody(body);
    sprite.setAlpha(0);
  } 
  public float getX() {
    return position.x;
  }
  public float getY() {
    return position.y;
  }
  public String getName() {
    return name;
  }
}
