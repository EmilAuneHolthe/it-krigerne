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
  
    private  Vector2 size;
    private Body body;
    private String name;

    /**
     * Constructs a new Door instance.
     *
     * @param body The physics body associated with the door.
     * @param name The name of the door.
     * @param size The size of the door, represented as a 2D vector (width, height).
     */
    public Door(Body body, String name, Vector2 size) {
        this.size = size;
        this.name = name;
        this.body = body;
    }

    /**
     * Removes the door entity from the specified world by destroying its associated body.
     *
     * @param world The world from which the door should be removed. This is the context
     *              in which the door's body exists and will be destroyed.
     */
    public void removeDoor(World world) {
        world.destroyBody(body);
    }

    /**
     * Gets the x-coordinate of the door's position in the game world.
     *
     * @return The x-coordinate of the door's position.
     */
    public float getX() {
        return body.getPosition().x;
    }

    /**
     * Gets the y-coordinate of the door's position in the game world.
     *
     * @return The y-coordinate of the door's position.
     */
    public float getY() {
        return body.getPosition().y;
    }

    /**
     * Gets the name of the door.
     *
     * @return The name of the door.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the size of the door.
     *
     * @return The size of the door, represented as a 2D vector (width, height).
     */
    public Vector2 getSize() {
        return size;
    }
}
