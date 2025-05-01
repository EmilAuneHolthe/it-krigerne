package inf112.skeleton.model.entity;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;

import inf112.skeleton.model.GamePanel;
import inf112.skeleton.model.entity.item.ItemType;
import inf112.skeleton.model.entity.player.CharacterType;
import inf112.skeleton.model.entity.player.PlayerAnimation;
import inf112.skeleton.model.entity.player.PlayerMovement;

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
    protected GameEntity(GamePanel context, World world, ItemType itemType, float x, float y) {
        this.context = context;
        this.world = world;
        this.x = x;
        this.y = y;
        this.itemType = itemType;
    }

    
    @Override
    public int getHealth() {
        return Math.max(0, health);
    }
    
    @Override
    public boolean takeDamage(int damage) {
        System.out.println("Taking damage: " + damage);
        health -= damage;
        return health > 0;
    }
    public PlayerAnimation getAnimation() {
        return animation;
    }
    public PlayerMovement getMovement() {
        return movement;
    }
    public ItemType getItemType() {
        return itemType;
    }
    @Override
    public void setHealth(int health) {
        this.health = health;
    }
    
    @Override
    public void setSpawn(float x, float y) {
        this.x = x;
        this.y = y;
    }
    
    @Override
    public float getX() {
        return x;
    }
    
    @Override
    public float getY() {
        return y;
    }
    
    @Override
    public Body getBody() {
        return body;
    }
    
    @Override
    public void create(int health, int damage, float x, float y) {
        this.health = health;
        this.damage = damage;
        this.x = x;
        this.y = y;
    }
    
    
    // Abstract methods that subclasses must implement
    protected abstract boolean isActive();
    
    public CharacterType getCharacterType() {
        return characterType;
    }
    
    @Override
    public void dispose() {
        if (animation != null) {
            animation.dispose();
        }
    }
}