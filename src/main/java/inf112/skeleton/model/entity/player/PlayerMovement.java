package inf112.skeleton.model.entity.player;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import inf112.skeleton.controller.KeyHandler;
import inf112.skeleton.controller.Keys;
import inf112.skeleton.model.GamePanel;

/**
 * Handles player movement mechanics including physics-based movement and direction control.
 * Manages input handling, movement speed, and directional state using Box2D physics.
 */
public class PlayerMovement {

    private final Body body;
    private final KeyHandler keyHandler;
    private float xFactor;
    private float yFactor;
    private String direction;

    private static final float ACCELERATION = 3f;
    private static final float MAX_SPEED = 2f;
    private static final float NORMALIZED_SPEED = 2.5f;

    /**
     * Creates a new PlayerMovement instance.
     * 
     * @param context The game panel context containing the key handler
     * @param world The physics world (unused parameter, could be removed)
     * @param body The physics body to control
     */
    public PlayerMovement(GamePanel context, World world, Body body) {
        this.body = body;
        this.keyHandler = context.getKeyHandler();
        this.direction = "Down";
    }

    /**
     * Applies movement physics to the player body.
     * Only applies movement if the player is not dead.
     * Uses linear impulse to control movement speed and direction.
     */
    public void update() {
        if (!Player.isDead) {
            float impulseX = xFactor * MAX_SPEED - body.getLinearVelocity().x * body.getMass();
            float impulseY = yFactor * MAX_SPEED - body.getLinearVelocity().y * body.getMass();
            body.applyLinearImpulse(impulseX, impulseY, 
                                    body.getWorldCenter().x, 
                                    body.getWorldCenter().y, true);
        }
    }

    /**
     * Handles key press input for player movement.
     * Updates movement factors and direction based on the pressed key.
     * Normalizes movement speed after updating.
     * 
     * @param key The key that was pressed
     */
    public void handleInput(Keys key) {
        switch (key) {
            case LEFT -> {
                xFactor = -ACCELERATION;
                direction = "Left";
            }
            case RIGHT -> {
                xFactor = ACCELERATION;
                direction = "Right";
            }
            case UP -> {
                yFactor = ACCELERATION;
                direction = "Up";
            }
            case DOWN -> {
                yFactor = -ACCELERATION;
                direction = "Down";
            }
            default -> {}
        }
        normalizeMovement();
    }

    /**
     * Handles key release input for player movement.
     * Updates movement factors and direction based on remaining pressed keys.
     * Normalizes movement speed after updating.
     * 
     * @param key The key that was released
     */
    public void handleInputRelease(Keys key) {
        switch (key) {
            case LEFT, RIGHT -> handleHorizontalRelease();
            case UP, DOWN -> handleVerticalRelease();
            default -> {}
        }
        updateDirection();
        normalizeMovement();
    }

    private void handleHorizontalRelease() {
        if (keyHandler.isKeyPressed(Keys.LEFT)) {
            xFactor = -ACCELERATION;
            direction = "Left";
        } else if (keyHandler.isKeyPressed(Keys.RIGHT)) {
            xFactor = ACCELERATION;
            direction = "Right";
        } else {
            xFactor = 0;
        }
    }

    private void handleVerticalRelease() {
        if (keyHandler.isKeyPressed(Keys.UP)) {
            yFactor = ACCELERATION;
            direction = "Up";
        } else if (keyHandler.isKeyPressed(Keys.DOWN)) {
            yFactor = -ACCELERATION;
            direction = "Down";
        } else {
            yFactor = 0;
        }
    }

    private void updateDirection() {
        if (xFactor == 0 && yFactor == 0) return;

        if (Math.abs(xFactor) > Math.abs(yFactor)) {
            direction = xFactor > 0 ? "Right" : "Left";
        } else {
            direction = yFactor > 0 ? "Up" : "Down";
        }
    }

    /**
     * Prevents faster diagonal movement by normalizing speed.
     */
    private void normalizeMovement() {
        float magnitude = (float) Math.sqrt(xFactor * xFactor + yFactor * yFactor);
        if (magnitude > 0) {
            xFactor = (xFactor / magnitude) * NORMALIZED_SPEED;
            yFactor = (yFactor / magnitude) * NORMALIZED_SPEED;
        }
    }

    /**
     * Gets the current movement direction of the player.
     * 
     * @return The current direction ("Up", "Down", "Left", or "Right")
     */
    public String getDirection() {
        return direction;
    }

    /**
     * Checks if the player is currently moving.
     * 
     * @return true if the player is moving (xFactor or yFactor is non-zero), false otherwise
     */
    public boolean isMoving() {
        return xFactor != 0 || yFactor != 0;
    }

    /**
     * Sets the player's movement direction.
     * 
     * @param direction The new direction to set ("Up", "Down", "Left", or "Right")
     */
    public void setDirection(String direction) {
        this.direction = direction;
    }
}
