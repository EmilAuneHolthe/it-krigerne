package inf112.skeleton.model.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import inf112.skeleton.audio.AudioTypes;
import inf112.skeleton.controller.KeyHandler;
import inf112.skeleton.controller.Keys;
import inf112.skeleton.model.GamePanel;
import inf112.skeleton.view.screen.ScreenType;
import inf112.skeleton.view.ui.DeathOverlay;

public class Player extends GameEntity {
    private boolean isDead;
    private DeathOverlay deathOverlay;
    private final KeyHandler keyHandler;
    protected boolean alive;
    public Player(GamePanel context, World world, Body body, int health, int damage, float x, float y, CharacterType characterType, KeyHandler keyHandler) {
        super(context, world, body, health, damage, x, y, characterType);
        isDead = false;
        deathOverlay = new DeathOverlay(context);
        this.keyHandler = keyHandler;
        this.movement = new PlayerMovement(world, body, keyHandler);
    }
    
    @Override
    public void render(SpriteBatch batch) {
        if (isDead) {
            deathOverlay.render(batch);
            return;
        }
        super.render(batch);
    }
    
    @Override
    protected boolean isActive() {
        return !isDead;
    }
    
    public void playerInput(KeyHandler keyHandler, Keys key) {
        if (isDead) {
            handleDeadPlayerInput(key);
            return;
        }
        
        if (key == Keys.ATTACK) {
            animation.startAttack();
        } else {
            movement.handleInput(key);
            animation.setMoving(movement.isMoving());
            animation.setDirection(movement.getDirection());
        }
    }
    
    private void handleDeadPlayerInput(Keys key) {
        if (key == Keys.INTERACT) {
            context.getAudioHandler().playAudio(AudioTypes.SELECT);
            context.resetPlayer();
            context.setScreen(ScreenType.MAIN_MENU);
        }
    }
    
    public void movePlayerReleased(KeyHandler keyHandler, Keys key) {
        movement.handleInputRelease(key);
        animation.setMoving(movement.isMoving());
        animation.setDirection(movement.getDirection());
    }
    
    public void playerTakeDamage(Enemy enemy) {
        if (alive) {
            context.getAudioHandler().playAudio(AudioTypes.HURT);
          }
          alive = takeDamage(enemy.getDamage());
          if (!alive) {
            killPlayer();
          }
        }


    public void killPlayer() {
            Gdx.app.log("DAMAGE", "Player has died!");
            isDead = true;
            die();
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
        super.dispose();
        if (deathOverlay != null) {
            deathOverlay.dispose();
        }
    }
    
    public DeathOverlay getDeathOverlay() {
        return deathOverlay;
    }
    public Vector2 getPosition() {
        return new Vector2(body.getPosition().x, body.getPosition().y);
    }
}