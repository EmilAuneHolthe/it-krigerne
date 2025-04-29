package inf112.skeleton.model.entity;

import com.badlogic.gdx.physics.box2d.Body;

/**
 * Represents a game entity with basic attributes and actions.
 */
public interface entity {

    /**
     * Returns the amount of damage the entity does.
     *
     * @return the damage value
     */
    int attack();

    /**
     * Sets the entity's health to 0 and removes it from the board.
     */
    void die();

    /**
     * Returns the entity's current health.
     *
     * @return the health value
     */
    int getHealth();

    /**
     * Reduces the entity's health by the given damage amount.
     * Returns true if the entity is still alive (health > 0), false otherwise.
     *
     * @param damage the amount of damage to apply
     * @return true if the entity is alive, false otherwise
     */
    boolean takeDamage(int damage);

    /**
     * Sets the entity's health to the given value.
     *
     * @param health the new health value
     */
    void setHealth(int health);

    /**
     * Sets the entity's spawn point to the given coordinates.
     *
     * @param x the x-coordinate of the spawn point
     * @param y the y-coordinate of the spawn point
     */
    void setSpawn(float x, float y);

    /**
     * Returns the x-coordinate of the entity.
     *
     * @return the x-coordinate
     */
    float getX();

    /**
     * Returns the y-coordinate of the entity.
     *
     * @return the y-coordinate
     */
    float getY();

    /**
     * Creates a new entity with the specified attributes.
     *
     * @param health the initial health of the entity
     * @param damage the damage the entity can deal
     * @param x the x-coordinate of the entity's spawn point
     * @param y the y-coordinate of the entity's spawn point
     */
    void create(int health, int damage, float x, float y);

    /**
     * Returns the entity's physical body.
     *
     * @return the {@link Body} of the entity
     */
    Body getBody();
}