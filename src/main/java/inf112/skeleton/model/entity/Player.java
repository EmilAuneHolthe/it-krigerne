package inf112.skeleton.model.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;

import inf112.skeleton.audio.AudioTypes;
import inf112.skeleton.controller.KeyHandler;
import inf112.skeleton.controller.Keys;
import inf112.skeleton.model.GamePanel;

public class Player implements entity, Disposable {
    private int health;
    private int damage;
    private float x;
    private float y;
    private World world;
    private Body body;
    
    private float xFactor, yFactor;
    private boolean directionChange;
    private String direction;
    private Sprite playerSprite;
    public Texture playerIdleFrontTexture, playerIdleUpTexture, playerIdleRightTexture, playerIdleLeftTexture;
    private Texture playerTexture;
    private final GamePanel context;
    
    public Player(GamePanel context, World world, Body body, int health, int damage, float x, float y) {
        this.context = context;
        this.world = world;
        this.body = body;
        this.health = health;
        this.damage = damage;
        this.x = x;
        this.y = y;
        direction = "Front";
    }
    
    
    public void render(SpriteBatch batch) {
        movePlayer();
        setPlayeSprite();
        playerSprite.setPosition(body.getPosition().x - playerSprite.getWidth() / 2, 
        body.getPosition().y - playerSprite.getHeight() / 2);
        batch.begin();
        playerSprite.draw(batch);
        batch.end();
    }
    
    
    public void playerInput(KeyHandler keyHandler, Keys key) {
        System.err.println("Key pressed: " + key);
        
        switch (key) {
            case LEFT:
            direction = "Left";
            xFactor = -3;
            break;
            case RIGHT:
            direction = "Right";
            xFactor = 3;
            break;
            case UP:
            direction = "Up";
            yFactor = 3;
            break;
            case DOWN:
            direction = "Front";
            yFactor = -3;
            break;
            default:
            break;
        }
        
        updateDirection();
        dontAccelerate();
    }
    
    public void movePlayerReleased (KeyHandler keyHandler, Keys key) {
        
        switch (key) {
            case LEFT:
            case RIGHT:
            xFactor = 0;
            if (keyHandler.isKeyPressed(Keys.LEFT)) {
                xFactor = -3;
            } else if (keyHandler.isKeyPressed(Keys.RIGHT)) {
                xFactor = 3;
            }
            break;
            case UP:
            case DOWN:
            yFactor = 0;
            if (keyHandler.isKeyPressed(Keys.UP)) {
                yFactor = 3;
            } else if (keyHandler.isKeyPressed(Keys.DOWN)) {
                yFactor = -3;
            }
            break;
            default:
            break;
        }
        updateDirection();
        dontAccelerate();
    }
    
    
    
    
    private void updateDirection() {
        directionChange = true;
    }
    
    private void dontAccelerate() {
        //Player speed is not multiplied by pressing multiple keys
        float speed = 3.0f;
        float magnitude = (float) Math.sqrt(xFactor * xFactor + yFactor * yFactor);
        if (magnitude > 0) {
            xFactor = (xFactor / magnitude) * speed;
            yFactor = (yFactor / magnitude) * speed;
        }
    }
    public void movePlayer() {
        if(directionChange) {
            body.applyLinearImpulse(
            (xFactor * 3 - body.getLinearVelocity().x * body.getMass()),
            (yFactor * 3 - body.getLinearVelocity().y * body.getMass()),
            body.getWorldCenter().x, body.getWorldCenter().y, true
            );
        }
    }
    
    public void loadTextures(){
        playerIdleFrontTexture = new Texture(Gdx.files.internal("Player/PlayerIdle/PlayerIdleFront.png"));
        playerIdleUpTexture = new Texture(Gdx.files.internal("Player/PlayerIdle/PlayerIdleUp.png"));
        playerIdleLeftTexture = new Texture(Gdx.files.internal("Player/PlayerIdle/PlayerIdleLeft.png"));
        playerIdleRightTexture = new Texture(Gdx.files.internal("Player/PlayerIdle/PlayerIdleRight.png"));
        
    }
    
    public void setPlayeSprite() {
        
        switch (direction) {
            case "Front":
            playerTexture = playerIdleFrontTexture;
            createSprite(playerTexture);
            break;
            case "Up":
            playerTexture = playerIdleUpTexture;
            createSprite(playerTexture);
            break;
            
            case "Left":
            playerTexture = playerIdleLeftTexture;
            createSprite(playerTexture);
            break;
            
            case "Right":
            playerTexture = playerIdleRightTexture;
            createSprite(playerTexture);
            break;
            
            default:
            break;
            
        }
    }
    
    public void playerTakeDamage(KeyHandler keyHandler, Keys key) {
        if (key == Keys.INTERACT ) {
            boolean alive = takeDamage(10); // deal 10 damage
            context.getAudioHandler().playAudio(AudioTypes.HURT);
            Gdx.app.log("DAMAGE", "Player took 10 damage. Current HP: " + getHealth());
            
            
            if (!alive) {
                Gdx.app.log("DAMAGE", "Player has died!");
                // Optional: trigger death state, animation, etc.
            }
        }
    }
    
    public void createSprite(Texture playerTexture) {
        playerSprite = new Sprite(playerTexture);
        playerSprite.setSize(1, 1);
    }
    
    @Override
    public int attack() {
        return damage;
    }
    
    @Override
    public void die() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'die'");
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
        playerIdleFrontTexture.dispose();
    }
}
