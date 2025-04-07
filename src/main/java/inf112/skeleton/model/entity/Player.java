package inf112.skeleton.model.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import inf112.skeleton.audio.AudioTypes;
import inf112.skeleton.controller.KeyHandler;
import inf112.skeleton.controller.Keys;
import inf112.skeleton.model.GamePanel;
import inf112.skeleton.view.screen.ScreenType;
import inf112.skeleton.view.ui.DeathOverlay;

public class Player implements entity, Disposable {
    private int health;
    private int damage;
    private float x;
    private float y;
    private Body body;
    private final GamePanel context;
    
    private boolean isDead;
    private DeathOverlay deathOverlay;
    
    
    
    private PlayerAnimation animation;
    private PlayerMovement movement;
    
    public Player(GamePanel context, World world, Body body, int health, int damage, float x, float y, CharacterType characterType) {
        this.context = context;
        this.body = body;
        this.health = health;
        this.damage = damage;
        this.x = x;
        this.y = y;
        this.animation = new PlayerAnimation(characterType);
        this.movement = new PlayerMovement(world, body);
        isDead = false;
        deathOverlay = new DeathOverlay(context);
    }
    
    public void render(SpriteBatch batch) {
        if (isDead) {
            deathOverlay.render(batch);
            return;
        }
        movement.update();
        animation.update(Gdx.graphics.getDeltaTime());
        animation.render(batch, body);
    }
    
    public void playerInput(KeyHandler keyHandler, Keys key) {
        if (key == Keys.ATTACK) {
            animation.startAttack();
        } else {
            movement.handleInput(key);
            animation.setMoving(movement.isMoving());
            animation.setDirection(movement.getDirection());
        }
    }
    
    public void movePlayerReleased(KeyHandler keyHandler, Keys key) {
        movement.handleInputRelease(key);
        animation.setMoving(movement.isMoving());
        animation.setDirection(movement.getDirection());
    }
    
    public void playerTakeDamage(KeyHandler keyHandler, Keys key) {
        if (key == Keys.INTERACT) {
            boolean alive = takeDamage(10);
            context.getAudioHandler().playAudio(AudioTypes.HURT);
            Gdx.app.log("DAMAGE", "Player took 10 damage. Current HP: " + getHealth());
            
            if (!alive) {
                Gdx.app.log("DAMAGE", "Player has died!");
            }
        }
    }
    
    @Override
    public int attack() {
        return damage;
    }
    
    @Override
    public void die() {
        if (deathOverlay != null) {
            deathOverlay.show();
        }
    }
    
    @Override
    public int getHealth() {
        if (health < 0) {
            return 0;
        }
        return health;
    }
    
    @Override
    public boolean takeDamage(int damage) {
        health -= damage;
        boolean alive = health > 0;
        if (!alive && !isDead) {
            isDead = true;
            die();
        }
        return alive;
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
    public void create(int health, int damage, float x, float y) {
        this.health = health;
        this.damage = damage;
        this.x = x;
        this.y = y;
    }
    
    public Body getBody() {
        return body;
    }
    
    @Override
    public void dispose() {
        animation.dispose();
        if (deathOverlay != null) {
            deathOverlay.dispose();
        }
    }
    
    public DeathOverlay getDeathOverlay() {
        return deathOverlay;
    }
}
