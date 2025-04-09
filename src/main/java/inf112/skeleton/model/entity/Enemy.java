package inf112.skeleton.model.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import inf112.skeleton.model.GamePanel;
import inf112.skeleton.view.ui.DeathOverlay;

public class Enemy extends GameEntity {
    private String name;
    private Texture healthTexture;
    private Texture backgroundTexture;
    private float speed = 2f;
    private boolean isDead;
    private DeathOverlay deathOverlay;
    private EnemyAnimation animation;
    private EnemyMovement movement;
    
    public Enemy(GamePanel context, World world, Body body, int health, int damage, float x, float y, CharacterType characterType, String name) {
        super(context, world, body, health, damage, x, y, characterType);
        this.isDead = false;
        this.deathOverlay = new DeathOverlay(context);
        this.name = name;
        
        // Load health bar textures
        this.healthTexture = new Texture(Gdx.files.internal("redtexture.png"));
        this.backgroundTexture = new Texture(Gdx.files.internal("graytexture.png"));
        
        // Initialize animation and movement
        this.animation = new EnemyAnimation(characterType);
        this.movement = new EnemyMovement(body, speed);
    }

    @Override
    public void render(SpriteBatch batch) {
        if (body == null) return;
        
        // Update animation
        animation.update(Gdx.graphics.getDeltaTime());
        animation.setMoving(movement.isMoving());
        animation.setDirection(movement.getDirection());
        animation.render(batch, body);
        
        // Draw health bar
        batch.begin();
        batch.draw(backgroundTexture, body.getPosition().x - 1f / 2, body.getPosition().y + 1f / 2, 1, 0.1f);
        batch.draw(healthTexture, body.getPosition().x - 1f / 2, body.getPosition().y + 1f / 2, 1 * (health / 100f), 0.1f);
        batch.end();
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
        movement.moveTo(x, y);
    }
    public void setSpeed(float speed) {
        this.speed = speed;
        movement.setSpeed(speed);
    }
    public void setLinearVelocity(float x, float y) {
        body.setLinearVelocity(x, y);
    }
    @Override
    protected boolean isActive() {
        return !isDead;
    }
    @Override
    public int attack() {
        return damage;
    }
    @Override
    public void die() {
        isDead = true;
        if (deathOverlay != null) {
            deathOverlay.show();
        }
    }
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
}
