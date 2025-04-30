package inf112.skeleton.model.entity.player;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import inf112.skeleton.controller.KeyHandler;
import inf112.skeleton.controller.Keys;
import inf112.skeleton.model.GamePanel;
import inf112.skeleton.view.screen.GameScreen;

public class PlayerMovement {
    private Body body;
    private float xFactor;
    private float yFactor;
    private boolean directionChange;
    private String direction;
    private KeyHandler keyHandler;

    
    public PlayerMovement(GamePanel context, World world, Body body) {
        this.body = body;
        this.keyHandler = context.getKeyHandler();
        this.direction = "Down";
    }
    
    public void update() {
        if(directionChange) {
            body.applyLinearImpulse(
                (xFactor * 2 - body.getLinearVelocity().x * body.getMass()),
                (yFactor * 2 - body.getLinearVelocity().y * body.getMass()),
                body.getWorldCenter().x, body.getWorldCenter().y, true
            );
        }
    }
    
    public void handleInput(Keys key) {
        switch (key) {
            case LEFT:
                xFactor = -3;
                direction = "Left";
                directionChange = true;
                break;
            case RIGHT:
                xFactor = 3;
                direction = "Right";
                directionChange = true;
                break;
            case UP:
                yFactor = 3;
                direction = "Up";
                directionChange = true;
                break;
            case DOWN:
                yFactor = -3;
                direction = "Down";
                directionChange = true;
                break;
            default:
                break;
        }
        dontAccelerate();
    }
    
    public void handleInputRelease(Keys key) {
        switch (key) {
            case LEFT:
                if(keyHandler.isKeyPressed(Keys.RIGHT)) {
                    xFactor = 3;
                    direction = "Right";
                } else {
                    xFactor = 0;
                }
                break;
            case RIGHT:
                xFactor = 0;
                if(keyHandler.isKeyPressed(Keys.LEFT)) {
                    xFactor = -3;
                    direction = "Left";
                } else {
                    xFactor = 0;
                }
                break;
            case UP:
                yFactor = 0;
                if(keyHandler.isKeyPressed(Keys.DOWN)) {
                    yFactor = -3;
                    direction = "Down";
                } else {
                    yFactor = 0;
                }
                break;
            case DOWN:
                yFactor = 0;
                if(keyHandler.isKeyPressed(Keys.UP)) {
                    yFactor = 3;
                    direction = "Up";
                } else {
                    yFactor = 0;
                }
                break;
            default:
                break;
        }
        if (xFactor == 0 && yFactor == 0) {
            directionChange = false;
        } else if (Math.abs(xFactor) > Math.abs(yFactor)) {
            direction = xFactor > 0 ? "Right" : "Left";
        } else {
            direction = yFactor > 0 ? "Up" : "Down";
        }
        directionChange = true;
        dontAccelerate();
    }
    
    private void dontAccelerate() {
        float speed = 2.5f;
        float magnitude = (float) Math.sqrt(xFactor * xFactor + yFactor * yFactor);
        if (magnitude > 0) {
            xFactor = (xFactor / magnitude) * speed;
            yFactor = (yFactor / magnitude) * speed;
        }
    }
    
    public String getDirection() {
        return direction;
    }
    
    public boolean isMoving() {
        return xFactor != 0 || yFactor != 0;
    }
    public void setDirection(String direction) {
        this.direction = direction;
    }
} 