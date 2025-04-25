package inf112.skeleton.model.entity.door;

import com.badlogic.gdx.graphics.g2d.Sprite;
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
 public Door(float x, float y, World world, GamePanel context, String name, Sprite sprite) {
    this.x = x;
    this.y = y;
    this.world = world;
    this.context = context;
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
}
