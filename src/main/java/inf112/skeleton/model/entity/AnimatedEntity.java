package inf112.skeleton.model.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import inf112.skeleton.controller.KeyHandler;
import inf112.skeleton.controller.Keys;

public abstract class AnimatedEntity implements Disposable {
    protected Body body;
    protected PlayerAnimation animation;
    protected PlayerMovement movement;
    
    public AnimatedEntity(Body body, CharacterType characterType, KeyHandler keyHandler, World world) {
        this.body = body;
        this.animation = new PlayerAnimation(characterType);
        this.movement = new PlayerMovement(world, body, keyHandler);
    }
    
    public void render(SpriteBatch batch) {
        movement.update();
        animation.update(Gdx.graphics.getDeltaTime());
        animation.render(batch, body);
    }
    
    public void handleInput(Keys key) {
        movement.handleInput(key);
        animation.setMoving(movement.isMoving());
        animation.setDirection(movement.getDirection());
    }
    
    public void handleInputRelease(Keys key) {
        movement.handleInputRelease(key);
        animation.setMoving(movement.isMoving());
        animation.setDirection(movement.getDirection());
    }
    
    public Body getBody() {
        return body;
    }
    
    public String getDirection() {
        return movement.getDirection();
    }
    
    public boolean isMoving() {
        return movement.isMoving();
    }
    
    @Override
    public void dispose() {
        animation.dispose();
    }
} 