package inf112.skeleton.model.entity;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;

import inf112.skeleton.model.GamePanel;
import inf112.skeleton.model.entity.item.ItemType;
import inf112.skeleton.model.entity.player.CharacterType;
import inf112.skeleton.model.entity.player.PlayerAnimation;
import inf112.skeleton.model.entity.player.PlayerMovement;

/**
 * Abstract base class for all game entities.
 * Provides common functionality and properties for entities in the game world.
 * Implements the entity interface and Disposable for resource management.
 */
public abstract class GameEntity implements entity, Disposable {
    // Core properties
    protected int health;
    protected int damage;
    protected float x;
    protected float y;
    protected World world;
    protected Body body;
    protected CharacterType characterType;
    protected final GamePanel context;
    
    // Animation and movement
    protected PlayerAnimation animation;
    protected PlayerMovement movement;
    protected ItemType itemType;
    
    /**
     * Constructs a new GameEntity with the specified attributes.
     *
     * @param context The game panel context
     * @param world The physics world
     * @param body The physics body
     * @param health Initial health value
     * @param damage Initial damage value
     * @param characterType The type of character
     */
    protected GameEntity(GamePanel context, World world, Body body, int health, int damage, CharacterType characterType) {
        this.context = context;
        this.world = world;
        this.body = body;
        this.health = health;
        this.damage = damage;
        this.characterType = characterType;
        
        // Initialize components
        this.animation = new PlayerAnimation(characterType);
        this.movement = new PlayerMovement(context, world, body);
    }

    /**
     * Constructs a new GameEntity for items.
     *
     * @param context The game panel context
     * @param world The physics world
     * @param itemType The type of item
     * @param x The x-coordinate
     * @param y The y-coordinate
     */
    protected GameEntity(GamePanel context, World world, ItemType itemType, float x, float y) {
        this.context = context;
        this.world = world;
        this.x = x;
        this.y = y;
        this.itemType = itemType;
    }

    /**
     * Gets the current health of the entity.
     * Returns 0 if health is negative.
     *
     * @return The current health value
     */
    @Override
    public int getHealth() {
        return Math.max(0, health);
    }
    
    /**
     * Applies damage to the entity and checks if it's still alive.
     *
     * @param damage The amount of damage to apply
     * @return true if the entity is still alive, false otherwise
     */
    @Override
    public boolean takeDamage(int damage) {
        health -= damage;
        return health > 0;
    }

    /**
     * Gets the animation component of the entity.
     *
     * @return The PlayerAnimation instance
     */
    public PlayerAnimation getAnimation() {
        return animation;
    }

    /**
     * Gets the movement component of the entity.
     *
     * @return The PlayerMovement instance
     */
    public PlayerMovement getMovement() {
        return movement;
    }

    /**
     * Gets the item type of the entity.
     *
     * @return The ItemType of the entity
     */
    public ItemType getItemType() {
        return itemType;
    }

    /**
     * Sets the health of the entity.
     *
     * @param health The new health value
     */
    @Override
    public void setHealth(int health) {
        this.health = health;
    }
    
    /**
     * Sets the spawn position of the entity.
     *
     * @param x The x-coordinate of the spawn point
     * @param y The y-coordinate of the spawn point
     */
    @Override
    public void setSpawn(float x, float y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     * Gets the x-coordinate of the entity.
     *
     * @return The x-coordinate
     */
    @Override
    public float getX() {
        return x;
    }
    
    /**
     * Gets the y-coordinate of the entity.
     *
     * @return The y-coordinate
     */
    @Override
    public float getY() {
        return y;
    }
    
    /**
     * Gets the physics body of the entity.
     *
     * @return The Box2D Body instance
     */
    @Override
    public Body getBody() {
        return body;
    }
    
    /**
     * Creates a new entity with the specified attributes.
     *
     * @param health The initial health value
     * @param damage The initial damage value
     * @param x The x-coordinate
     * @param y The y-coordinate
     */
    @Override
    public void create(int health, int damage, float x, float y) {
        this.health = health;
        this.damage = damage;
        this.x = x;
        this.y = y;
    }
    
    /**
     * Checks if the entity is currently active.
     * Must be implemented by subclasses.
     *
     * @return true if the entity is active, false otherwise
     */
    protected abstract boolean isActive();
    
    /**
     * Gets the character type of the entity.
     *
     * @return The CharacterType of the entity
     */
    public CharacterType getCharacterType() {
        return characterType;
    }
    
    /**
     * Disposes of the entity's resources.
     * Cleans up animations and other disposable resources.
     */
    @Override
    public void dispose() {
        if (animation != null) {
            animation.dispose();
        }
    }
}