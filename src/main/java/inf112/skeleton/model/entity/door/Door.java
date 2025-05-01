package inf112.skeleton.model.entity.door;



import com.badlogic.gdx.graphics.g2d.Sprite;
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
  public Door(Body body, String name, Vector2 size) {
      this.size = size;
      this.name = name;
      this.body = body;
    }
  public void removeDoor(World world) {
    world.destroyBody(body);
  } 
  public float getX() {
    return body.getPosition().x;
  }
  public float getY() {
    return  body.getPosition().y;
  }
  public String getName() {
    return name;
  }
  public Vector2 getSize() {
    return size;
  }
}
