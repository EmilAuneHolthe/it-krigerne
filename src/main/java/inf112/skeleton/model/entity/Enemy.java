package inf112.skeleton.model.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import inf112.skeleton.model.GamePanel;
import inf112.skeleton.view.ui.DeathOverlay;
import inf112.skeleton.controller.KeyHandler;

public class Enemy extends AnimatedEntity {
    private String direction;
    public Texture playerIdleFrontTexture, playerIdleUpTexture, playerIdleRightTexture, playerIdleLeftTexture;
    private String name;
    private Texture healthTexture;
    private Texture backgroundTexture;
    private float speed = 2f;
    private boolean isDead;
    private DeathOverlay deathOverlay;
    private int health;
    private int damage;
    private float x;
    private float y;
    private final GamePanel context;
    private final World world;
    private Body body;
    private Sprite playerSprite;

    public Enemy(GamePanel context, World world, Body body, int health, int damage, float x, float y, CharacterType characterType, String name, KeyHandler keyHandler) {
        super(body, characterType, keyHandler, world);
        this.context = context;
        this.world = world;
        this.body = body;
        this.health = health;
        this.damage = damage;
        this.x = x;
        this.y = y;
        this.isDead = false;
        this.deathOverlay = new DeathOverlay(context);
        this.direction = "Down";
        this.name = name;
        
        // Load health bar textures
        this.healthTexture = new Texture(Gdx.files.internal("redtexture.png"));
        this.backgroundTexture = new Texture(Gdx.files.internal("graytexture.png"));
        loadTextures();
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);
        
        // Draw health bar
        Vector2 pos = body.getPosition();
        batch.begin();
        batch.draw(backgroundTexture, pos.x - 0.5f, pos.y + 0.5f, 1, 0.1f);
        batch.draw(healthTexture, pos.x - 0.5f, pos.y + 0.5f, 1 * (health / 100f), 0.1f);
        batch.end();
    }

    public void update(float deltaTime) {
        // Add enemy-specific update logic here
    }

    public int getHealth() {
        if (health < 0) {
            return 0;
        }
        return health;
    }

    public boolean takeDamage(int damage) {
        health -= damage;
        if (health < 1) {
            int index = context.getEnemy().indexOf(this, true);
            if (index >= 0) {
                // Destroy the Box2D body
                world.destroyBody(body);
                // Remove from the enemy list
                context.getEnemy().removeIndex(index);
                System.out.println("Enemy removed from list: " + name);
            }
        }
        return health > 0;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        if (body != null) {
            body.setTransform(x, y, 0);
        }
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void create(int health, int damage, float x, float y) {
        this.health = health;
        this.damage = damage;
        this.x = x;
        this.y = y;
    }

    @Override
    public Body getBody() {
        return body;
    }

    public Vector2 getPosition() {
        return new Vector2(body.getPosition().x, body.getPosition().y);
    }

    public String getName() {
        return name;
    }

    public void moveEnemy(float x, float y) {
        // Calculate direction vector
        Vector2 currentPos = body.getPosition();
        Vector2 targetPos = new Vector2(x, y);
        Vector2 direction = targetPos.sub(currentPos).nor();
        
        // Set velocity based on direction
        float speed = 2.0f;
        body.setLinearVelocity(direction.x * speed, direction.y * speed);
        
        // Update animation direction
        if (Math.abs(direction.x) > Math.abs(direction.y)) {
            if (direction.x > 0) {
                animation.setDirection("Right");
            } else {
                animation.setDirection("Left");
            }
        } else {
            if (direction.y > 0) {
                animation.setDirection("Up");
            } else {
                animation.setDirection("Down");
            }
        }
        animation.setMoving(true);
    }

    public void setDirection(String direction) {
        this.direction = direction;
        switch (direction) {
            case "Front":
                playerSprite.setTexture(playerIdleFrontTexture);
                break;
            case "Up":
                playerSprite.setTexture(playerIdleUpTexture);
                break;
            case "Left":
                playerSprite.setTexture(playerIdleLeftTexture);
                break;
            case "Right":
                playerSprite.setTexture(playerIdleRightTexture);
                break;
        }
    }

    public int getDamage() {
        System.out.println("Damage: " + damage);
        return damage;
    }

    public void setLinearVelocity(float x, float y) {
        body.setLinearVelocity(x, y);
    }

    public boolean isActive() {
        return !isDead;
    }

    public void die() {
        isDead = true;
        if (deathOverlay != null) {
            deathOverlay.show();
        }
        if (body != null) {
            world.destroyBody(body);
            body = null;
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        if (deathOverlay != null) {
            deathOverlay.dispose();
        }
        if (animation != null) {
            animation.dispose();
        }
        if (healthTexture != null) {
            healthTexture.dispose();
        }
        if (backgroundTexture != null) {
            backgroundTexture.dispose();
        }
    }

    public void loadTextures() {
        playerIdleFrontTexture = new Texture(Gdx.files.internal("Player/PlayerIdle/PlayerIdleFront.png"));
        playerIdleUpTexture = new Texture(Gdx.files.internal("Player/PlayerIdle/PlayerIdleUp.png"));
        playerIdleLeftTexture = new Texture(Gdx.files.internal("Player/PlayerIdle/PlayerIdleLeft.png"));
        playerIdleRightTexture = new Texture(Gdx.files.internal("Player/PlayerIdle/PlayerIdleRight.png"));
        playerSprite = new Sprite(playerIdleFrontTexture);
        playerSprite.setSize(1, 1);
        playerSprite.setTexture(playerIdleFrontTexture);
    }
}
