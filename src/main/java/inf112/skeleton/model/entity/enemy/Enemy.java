package inf112.skeleton.model.entity.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import inf112.skeleton.audio.AudioTypes;
import inf112.skeleton.model.GamePanel;
import inf112.skeleton.model.WorldFunctions;
import inf112.skeleton.model.entity.GameEntity;
import inf112.skeleton.model.entity.player.CharacterType;
import inf112.skeleton.model.entity.player.Player;

/**
 * Represents an enemy entity in the game.
 * Handles enemy behavior, including movement, attacking the player, and taking damage.
 */
public class Enemy extends GameEntity {
    private final String name;
    private final Texture healthTexture;
    private final Texture backgroundTexture;
    private final boolean isDead;
    private final float maxHealth;
    private final int sight;

    /**
     * Constructs an Enemy instance.
     *
     * @param context        The game context, providing access to shared resources.
     * @param world          The Box2D world the enemy belongs to.
     * @param body           The physical body of the enemy.
     * @param characterType  The type of character (e.g., normal enemy, boss).
     * @param name           The name of the enemy.
     */
    public Enemy(GamePanel context, World world, Body body, CharacterType characterType, String name) {
        super(context, world, body, CharacterType.getEnemyHealth(characterType), CharacterType.getEnemyDamage(characterType), characterType);
        this.world = world;
        this.characterType = characterType;
        this.isDead = false;
        this.name = name;
        this.health = CharacterType.getEnemyHealth(characterType);
        this.damage = CharacterType.getEnemyDamage(characterType);
        this.sight = CharacterType.getEnemySight(characterType);
        this.maxHealth = CharacterType.getEnemyHealth(characterType);

        // Load health bar textures
        this.healthTexture = new Texture(Gdx.files.internal("Ui/redtexture.png"));
        this.backgroundTexture = new Texture(Gdx.files.internal("Ui/graytexture.png"));
    }

    /**
     * Updates the enemy's behavior, including movement and attacking the player.
     *
     * @param player The player to interact with.
     */
    public void update(Player player) {
        Vector2 playerPosition = player.getPosition();
        float distance = getPosition().dst(playerPosition);

        if (distance < sight) {
            moveEnemy(playerPosition.x, playerPosition.y);

            // Check if the enemy is within attack range, then deal damage to the player
            if (distance < 0.8) {
                player.playerTakeDamage(this);
            }
        } else {
            setLinearVelocity(0, 0);
        }
    }

    /**
     * Moves the enemy toward a target position.
     *
     * @param x The target X-coordinate.
     * @param y The target Y-coordinate.
     */
    public void moveEnemy(float x, float y) {
        Vector2 currentPos = body.getPosition();
        Vector2 targetPos = new Vector2(x, y);
        Vector2 direction = targetPos.sub(currentPos).nor();

        // Set velocity based on direction
        float speed = 2f;
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

    /**
     * Handles the enemy taking damage.
     *
     * @param damage The amount of damage taken.
     * @return True if the enemy is still alive, false otherwise.
     */
    @Override
    public boolean takeDamage(int damage) {
        health -= damage;

        if (health < 1) {
            context.getAudioHandler().playAudio(AudioTypes.HIT);
            int index = context.getEnemy().indexOf(this, true);
            if (index >= 0) {
                // Destroy the Box2D body
                world.destroyBody(body);
                // Remove from the enemy list
                context.getEnemy().removeIndex(index);
                if (characterType == CharacterType.BOSS) {
                    WorldFunctions.victory = true;
                }
            }
        }
        return health > 0;
    }

    /**
     * Gets the enemy's current position.
     *
     * @return A {@link Vector2} representing the enemy's position.
     */
    public Vector2 getPosition() {
        return new Vector2(body.getPosition().x, body.getPosition().y);
    }

    /**
     * Sets the position of the enemy in the game world.
     * Updates both the internal position coordinates and the physics body position.
     *
     * @param x The new x-coordinate
     * @param y The new y-coordinate
     */
    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        if (body != null) {
            body.setTransform(x, y, 0);
        }
    }

    /**
     * Gets the name of the enemy.
     *
     * @return The name of the enemy.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the enemy's damage value.
     *
     * @return The amount of damage the enemy deals.
     */
    public int getDamage() {
        return damage;
    }

    /**
     * Sets the enemy's linear velocity.
     *
     * @param x The velocity in the X direction.
     * @param y The velocity in the Y direction.
     */
    public void setLinearVelocity(float x, float y) {
        body.setLinearVelocity(x, y);
    }

    /**
     * Checks if the enemy is active (not dead).
     *
     * @return True if the enemy is active, false otherwise.
     */
    public boolean isActive() {
        return !isDead;
    }

    /**
     * Gets the maximum health of the enemy.
     *
     * @return The maximum health of the enemy.
     */
    public int getMaxHealth() {
        return (int) maxHealth;
    }

    /**
     * Gets the sight range of the enemy.
     *
     * @return The sight range of the enemy.
     */
    public int getSightRange() {
        return sight;
    }

    /**
     * Disposes of the enemy's resources, including textures and animations.
     */
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

    /**
     * Unsupported operation for attacking.
     *
     * @throws UnsupportedOperationException Always thrown.
     */
    @Override
    public int attack() {
        throw new UnsupportedOperationException("Unimplemented method 'attack'");
    }

    /**
     * Unsupported operation for dying.
     *
     * @throws UnsupportedOperationException Always thrown.
     */
    @Override
    public void die() {
        throw new UnsupportedOperationException("Unimplemented method 'die'");
    }
}
