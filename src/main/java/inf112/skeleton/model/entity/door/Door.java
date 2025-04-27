package inf112.skeleton.model.entity.door;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import inf112.skeleton.model.GamePanel;

public class Door{
  float x;
  float y;
  World world;
  GamePanel context;
  Body body;
  String name;
  Sprite sprite;
  DoorType doorType;
 public Door(float x, float y, World world, Body body, String name) {
    this.x = x;
    this.y = y;
    this.world = world;
    this.name = name;
    this.body = body;
    this.doorType = DoorType.valueOf(name.toUpperCase());
    this.sprite = new Sprite();
  }
  public void removeDoor() {
    world.destroyBody(body);
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
  public void render(SpriteBatch batch) {
    batch.draw(sprite, x - spriteWidth / 2, y - spriteHeight / 2, spriteWidth, spriteHeight);
  }
}
