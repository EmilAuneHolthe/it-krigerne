package inf112.skeleton.model.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;

import inf112.skeleton.controller.KeyHandler;
import inf112.skeleton.model.GamePanel;
import inf112.skeleton.controller.KeyHandler;

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
    protected final KeyHandler keyHandler;
    
    // Animation and movement
    protected PlayerAnimation animation;
    protected PlayerMovement movement;
    protected KeyHandler keyHandler;
    
    public GameEntity(GamePanel context, World world, Body body, int health, int damage, float x, float y, CharacterType characterType, KeyHandler keyHandler) {
        this.context = context;
        this.world = world;
        this.body = body;
        this.health = health;
        this.damage = damage;
        this.x = x;
        this.y = y;
        this.characterType = characterType;
        this.keyHandler = keyHandler;
        
        // Initialize components
        this.animation = new PlayerAnimation(characterType);
        this.movement = new PlayerMovement(world, body, keyHandler);
    }
    
    // Common implementation of entity interface methods
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
    
    // Common rendering method
    public void render(SpriteBatch batch) {
        if(characterType == CharacterType.ZOMBIE) {
            animation.update(Gdx.graphics.getDeltaTime());
            animation.setMoving(movement.isMoving());
            animation.render(batch, body);
            return;
        }
        if (isActive()) {
            movement.update();
            animation.update(Gdx.graphics.getDeltaTime());
            animation.render(batch, body);
        }
    }
    
    // Abstract methods that subclasses must implement
    protected abstract boolean isActive();
    
    @Override
    public void dispose() {
        if (animation != null) {
            animation.dispose();
        }
    }
}