package inf112.skeleton.model.entity.door;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Represents a door entity in the game.
 * 
 * <p>A door is a physical object in the game world that has a position, size, and name. 
 * It can be removed from the game world by destroying its associated physics body.</p>
 */

public class Door {
  private final Vector2 size;
  private final Body body;
  private final String name;

  /**
   * Constructs a new Door instance.
   *
   * @param body The physics body of the door
   * @param name The name/identifier of the door
   * @param size The size of the door
   */
  public Door(Body body, String name, Vector2 size) {
      this.size = size;
      this.name = name;
      this.body = body;
      
    }

  /**
   * Removes the door from the physics world.
   *
   * @param world The physics world to remove the door from
   */
  public void removeDoor(World world) {
    world.destroyBody(body);
  } 

  /**
   * Gets the x-coordinate of the door's position.
   *
   * @return The x-coordinate
   */
  public float getX() {
    return body.getPosition().x;
  }

  /**
   * Gets the y-coordinate of the door's position.
   *
   * @return The y-coordinate
   */
  public float getY() {
    return body.getPosition().y;
  }

  /**
   * Gets the name/identifier of the door.
   *
   * @return The door's name
   */
  public String getName() {
    return name;
  }

  /**
   * Gets the size of the door.
   *
   * @return Vector2 containing the door's dimensions
   */
  public Vector2 getSize() {
    return size;
  }
}
