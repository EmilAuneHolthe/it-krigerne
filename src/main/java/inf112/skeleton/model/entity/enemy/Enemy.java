package inf112.skeleton.model.entity.enemy;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import inf112.skeleton.model.GamePanel;
import inf112.skeleton.model.entity.GameEntity;
import inf112.skeleton.model.entity.player.CharacterType;
import inf112.skeleton.view.ui.DeathOverlay;
import inf112.skeleton.controller.KeyHandler;

public class Enemy extends GameEntity {
    private String direction;
    public Texture playerIdleFrontTexture, playerIdleUpTexture, playerIdleRightTexture, playerIdleLeftTexture;
    private String name;
    private  Texture healthTexture;
    private Texture backgroundTexture;
    private float speed = 2f;
    private boolean isDead;
    private final CharacterType characterType;
    private float maxHealth;
    public Enemy(GamePanel context, World world, Body body, CharacterType characterType) {
        super(context, world, body, EnemyTypes.getEnemyHealth(characterType), EnemyTypes.getEnemyDamage(characterType), characterType);
        this.world = world;
        this.characterType = characterType;
        this.health = EnemyTypes.getEnemyHealth(characterType);
        this.damage = EnemyTypes.getEnemyDamage(characterType);
        this.isDead = false;
        this.direction = "Down";
        this.maxHealth = EnemyTypes.getEnemyHealth(characterType);
        
        // Load health bar textures
        this.healthTexture = new Texture(Gdx.files.internal("redtexture.png"));
        this.backgroundTexture = new Texture(Gdx.files.internal("graytexture.png"));
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);
        if(characterType != CharacterType.BOSS) {
            // Draw health bar for regular enemies
            Vector2 pos = body.getPosition();
            float barWidth = maxHealth/100;
            float barHeight = 0.1f;
            float x = pos.x - (barWidth / 2); // Center the bar horizontally
            float y = pos.y + 0.5f; // Position above the enemy
            
            batch.draw(backgroundTexture, pos.x - barWidth/2.3f, pos.y + 0.5f, barWidth, 0.2f);
            batch.draw(healthTexture, pos.x - barWidth/2.3f, pos.y + 0.5f, 1 * (health / 100f), 0.2f);
        }
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
                if( characterType == CharacterType.BOSS) {
                    System.out.println("Boss defeated!");
                }
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
    @Override
    public void dispose() {
        super.dispose();
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

    @Override
    public int attack() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'attack'");
    }

    @Override
    public void die() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'die'");
    }

    public int getMaxHealth() {
        return (int)maxHealth;
    }
}
