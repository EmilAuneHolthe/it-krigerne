package inf112.skeleton.model.entity;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class EnemyMovement {
    private Body body;
    private float speed;
    private String direction;
    
    public EnemyMovement(Body body, float speed) {
        this.body = body;
        this.speed = speed;
        this.direction = "Down";
    }
    
    public void moveTo(float targetX, float targetY) {
        // Calculate direction vector
        Vector2 target = new Vector2(targetX - body.getPosition().x, targetY - body.getPosition().y);
        
        // Only move if we're not already at the target
        if (target.len2() > 0.01f) {  // Small threshold to prevent jittering
            target.nor(); // Normalize to get unit vector
            target.scl(speed); // Scale by speed
            body.setLinearVelocity(target);
            
            // Update direction based on movement
            if (Math.abs(target.x) > Math.abs(target.y)) {
                direction = target.x > 0 ? "Right" : "Left";
            } else {
                direction = target.y > 0 ? "Up" : "Down";
            }
        } else {
            body.setLinearVelocity(0, 0);
        }
    }
    
    public String getDirection() {
        return direction;
    }
    
    public boolean isMoving() {
        return body.getLinearVelocity().len2() > 0.01f;
    }
    
    public void setSpeed(float speed) {
        this.speed = speed;
    }
} 