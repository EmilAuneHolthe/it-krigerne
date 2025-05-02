package inf112.skeleton.model.entity.taskBoard;

import static inf112.skeleton.model.GamePanel.UNIT_SCALE;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Represents a task board in the game that can be displayed on the screen.
 * The task board is a sprite that can be activated or deactivated.
 */
public class TaskBoard {

    private final Sprite sprite;
    private boolean active = false;

    /**
     * Creates a new TaskBoard instance with the specified position and dimensions.
     * 
     * @param x The x-coordinate of the task board
     * @param y The y-coordinate of the task board
     * @param width The width of the task board
     * @param height The height of the task board
     * @param assets The asset manager containing the task board texture
     */
    public TaskBoard(float x, float y, float width, float height, AssetManager assets) {
        this.sprite = new Sprite(assets.get("map/miniMap.png", Texture.class));
        sprite.setPosition(x, y);
        sprite.setSize(width * UNIT_SCALE + 2, height * UNIT_SCALE + 2);
    }

    /**
     * Disposes of the task board's texture resources.
     * Should be called when the task board is no longer needed to prevent memory leaks.
     */
    public void dispose() {
        sprite.getTexture().dispose();
    }

    /**
     * Sets whether the task board is active.
     * 
     * @param active true to activate the task board, false to deactivate it
     */
    public void setActive(Boolean active) {
        this.active = active;
    }

    /**
     * Gets the sprite representing the task board.
     * 
     * @return The task board's sprite
     */
    public Sprite getSprite() {
        return sprite;
    }

    /**
     * Checks if the task board is currently active.
     * 
     * @return true if the task board is active, false otherwise
     */
    public Boolean isActive() {
        return active;
    }
}