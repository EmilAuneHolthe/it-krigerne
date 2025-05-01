package inf112.skeleton.model.entity.door;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import inf112.skeleton.model.GamePanel;

/**
 * Represents a door entity in the game.
 * A door can be rendered, removed, and provides information about its position and name.
 */
public class Door {
    final Vector2 position;
    final Vector2 size;
    final World world;
    GamePanel context;
    final Body body;
    final String name;
    final Sprite sprite;

    /**
     * Constructs a Door instance.
     *
     * @param pos     The position of the door.
     * @param world   The Box2D world the door belongs to.
     * @param body    The physical body of the door.
     * @param name    The name of the door.
     * @param assets  The asset manager to load the door's texture.
     * @param size    The size of the door.
     */
    public Door(Vector2 pos, World world, Body body, String name, AssetManager assets, Vector2 size) {
        this.position = pos;
        this.size = size;
        this.world = world;
        this.name = name;
        this.body = body;
        sprite = new Sprite(assets.get("map/Door1.png", Texture.class));
        sprite.setSize(size.x, size.y);
    }

    /**
     * Renders the door on the screen.
     *
     * @param batch The SpriteBatch used to draw the door.
     */
    public void render(SpriteBatch batch) {
        sprite.setPosition(body.getPosition().x, body.getPosition().y);
        sprite.draw(batch);
    }

    /**
     * Removes the door from the game world.
     * Destroys the physical body and makes the door sprite invisible.
     */
    public void removeDoor() {
        world.destroyBody(body);
        sprite.setAlpha(0);
    }

    /**
     * Gets the X-coordinate of the door's position.
     *
     * @return The X-coordinate of the door.
     */
    public float getX() {
        return position.x;
    }

    /**
     * Gets the Y-coordinate of the door's position.
     *
     * @return The Y-coordinate of the door.
     */
    public float getY() {
        return position.y;
    }

    /**
     * Gets the name of the door.
     *
     * @return The name of the door.
     */
    public String getName() {
        return name;
    }
}
