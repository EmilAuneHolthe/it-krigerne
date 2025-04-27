package inf112.skeleton.model.entity.door;

import static inf112.skeleton.model.GamePanel.UNIT_SCALE;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import inf112.skeleton.model.GamePanel;

public class Door{
  float x;
  float y;
  float width;
  float height;
  World world;
  GamePanel context;
  Body body;
  String name;
  Sprite sprite;
  DoorType doorType;
  public Door(float x, float y, World world, Body body, String name, AssetManager assets, float width, float height) {
      this.x = x;
      this.y = y;
      this.width = width;
      this.height = height;
      this.world = world;
      this.name = name;
      this.body = body;
      this.doorType = DoorType.valueOf(name.toUpperCase());
      sprite = new Sprite(doorType.getTexture(assets));
      sprite.setSize(width, height);
      
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
    return x;
  }
  public float getY() {
    return y;
  }
  public String getName() {
    return name;
  }
}
