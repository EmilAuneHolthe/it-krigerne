package inf112.skeleton.model.entity.player;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import inf112.skeleton.audio.AudioTypes;
import inf112.skeleton.controller.KeyHandler;
import inf112.skeleton.controller.Keys;
import inf112.skeleton.model.GamePanel;
import inf112.skeleton.model.entity.GameEntity;
import inf112.skeleton.model.entity.enemy.Enemy;
import inf112.skeleton.model.entity.item.Inventory;
import inf112.skeleton.model.entity.item.Item;
import inf112.skeleton.model.entity.item.ItemType;
import inf112.skeleton.view.ui.DeathOverlay;

/**
 * Represents the player character in the game.
 * Handles player-specific functionality including movement, combat, inventory, and mana management.
 */
public class Player extends GameEntity {
    public static boolean isDead;
    private final DeathOverlay deathOverlay;
    public boolean alive;
    private int currentMana;
    private int maxMana;
    private float manaRegenRate = 10f; // Mana per second
    private float manaRegenAccumulator = 0.0f;
    public boolean canAttack = true; 
    private final int maxHealth;
    private boolean hasKey = false;
    private PlayerInteractions playerInteractions;
    private final Item[] items;
    private int selectedItemIndex;
    private final KeyHandler keyHandler;
    private final Inventory inventory;

    public Player(GamePanel context, World world, Body body, int health, int damage, float x, float y, CharacterType characterType) {
        super(context, world, body, health, damage, characterType);
        isDead = false;
        deathOverlay = new DeathOverlay(context);
        this.movement = new PlayerMovement(context, world, body);
        this.currentMana = 100;
        this.maxMana = 100;
        this.keyHandler = context.getKeyHandler();
        maxHealth = health;
        items = new Item[4]; // 4 slots for items
        selectedItemIndex = 0;
        inventory = new Inventory(4, this, context, world);
    }

    /**
     * Gets the player's inventory.
     *
     * @return The player's Inventory instance
     */
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Renders the death overlay if the player is dead.
     *
     * @param batch The SpriteBatch to render with
     */
    public void renderDeathOverlay(SpriteBatch batch) {
        if (isDead && deathOverlay != null) {
            deathOverlay.render(batch);
        }
    }

    /**
     * Checks if the player is currently active (alive).
     *
     * @return true if the player is alive, false otherwise
     */
    @Override
    protected boolean isActive() {
        return !isDead;
    }

    /**
     * Handles item pickup and adds it to the player's inventory.
     *
     * @param item The type of item to pick up
     */
    public void ItemPickup(ItemType item) {
        inventory.pickUpItem(item);
    }

    /**
     * Handles key release events for player movement.
     *
     * @param keyHandler The KeyHandler managing the input
     * @param key The key that was released
     */
    public void movePlayerReleased(KeyHandler keyHandler, Keys key) {
        movement.handleInputRelease(key);
        animation.setMoving(movement.isMoving());
        animation.setDirection(movement.getDirection());
    }

    /**
     * Increases the player's damage by the specified amount.
     *
     * @param damage The amount to increase damage by
     */
    public void increaseDamage(int damage) {
        this.damage += damage;
    }

    /**
     * Handles the player taking damage from an enemy.
     * Plays appropriate sound effects and handles death if health reaches zero.
     *
     * @param enemy The enemy that caused the damage
     */
    public void playerTakeDamage(Enemy enemy) {
        if (alive) {
            context.getAudioHandler().playAudio(AudioTypes.HURT2);
            context.getAudioHandler().playAudio(AudioTypes.HIT);
        }
        alive = takeDamage(enemy.getDamage());
        if (!alive) {
            isDead = true;
            killPlayer();     
        }
    }

    /**
     * Kills the player and shows the death overlay.
     */
    public void killPlayer() {
        Gdx.app.log("DAMAGE", "Player has died!");
        isDead = true;
        die();
    }

    /**
     * Gets the player's attack damage.
     *
     * @return The player's damage value
     */
    @Override
    public int attack() {
        return damage;
    }
    
    /**
     * Handles player death by showing the death overlay.
     */
    @Override
    public void die() {
        if (deathOverlay != null) {
            deathOverlay.show();
        }
    }
    
    /**
     * Gets the player's current health.
     * Returns 0 if health is negative.
     *
     * @return The player's current health
     */
    @Override
    public int getHealth() {
        if (health < 0) {
            return 0;
        }
        return health;
    }

    /**
     * Sets the player's spawn position and resets velocity.
     *
     * @param x The x-coordinate of the spawn point
     * @param y The y-coordinate of the spawn point
     */
    @Override
    public void setSpawn(float x, float y) {
        this.x = x;
        this.y = y;
        if (body != null) {
            // Reset velocity to prevent sliding
            body.setLinearVelocity(0, 0);
            // Set the new position
            body.setTransform(x, y, 0);
        }
    }
    
    /**
     * Gets the player's x-coordinate in world units.
     *
     * @return The x-coordinate multiplied by the unit scale
     */
    @Override
    public float getX() {
        return body.getPosition().x * GamePanel.UNIT_SCALE;
    }
    
    /**
     * Gets the player's y-coordinate in world units.
     *
     * @return The y-coordinate multiplied by the unit scale
     */
    @Override
    public float getY() {
        return body.getPosition().y * GamePanel.UNIT_SCALE;
    }

    /**
     * Disposes of the player's resources.
     * Cleans up the death overlay and other disposable resources.
     */
    @Override
    public void dispose() {
        super.dispose();
        if (deathOverlay != null) {
            deathOverlay.dispose();
        }
    }
    
    /**
     * Gets the player's current position in the world.
     *
     * @return Vector2 containing the player's position
     */
    public Vector2 getPosition() {
        return new Vector2(body.getPosition().x, body.getPosition().y);
    }

    /**
     * Sets the player's current mana value, clamped between 0 and maxMana.
     *
     * @param mana The new mana value
     */
    public void setCurrentMana(int mana) {
        this.currentMana = Math.min(Math.max(0, mana), maxMana);
    }

    /**
     * Sets the player's maximum mana value.
     *
     * @param maxMana The new maximum mana value
     */
    public void setMaxMana(int maxMana) {
        this.maxMana = maxMana;
    }

    /**
     * Regenerates mana over time based on the regeneration rate.
     * Updates the canAttack flag based on mana availability.
     *
     * @param deltaTime The time elapsed since the last update
     */
    public void regenerateMana(float deltaTime) {
        // Update mana regeneration
        manaRegenAccumulator += manaRegenRate * deltaTime;
        if (manaRegenAccumulator >= 1.0f) {
            int manaToAdd = (int) manaRegenAccumulator;
            setCurrentMana(getCurrentMana() + manaToAdd);
            manaRegenAccumulator -= manaToAdd;

            canAttack = currentMana >= 30;
        }
    }

    /**
     * Sets the player's mana regeneration rate.
     *
     * @param manaRegenRate The new mana regeneration rate
     */
    public void setManaRegenRate(float manaRegenRate) {
        this.manaRegenRate = manaRegenRate;
    }

    /**
     * Updates the sword HUD texture based on the equipped sword.
     *
     * @param Sword The name of the equipped sword
     */
    public void updateSwordHUDTexturePath(String Sword) {
        String swordHUDTexture = ItemType.getSwordHUDTexturePath(Sword);
        if (swordHUDTexture != null) {
            context.updateEquippedSwordHUD(swordHUDTexture);
        }
    }

    /**
     * Checks if the player has a key.
     *
     * @return true if the player has a key, false otherwise
     */
    public boolean hasKey() {
        return hasKey;
    }

    /**
     * Removes an item from the player's inventory at the specified index.
     *
     * @param index The index of the item to remove
     */
    public void removeItem(int index) {
        if (index >= 0 && index < items.length) {
            items[index] = null;
        }
    }

    /**
     * Selects an item in the player's inventory.
     *
     * @param index The index of the item to select
     */
    public void selectItem(int index) {
        if (index >= 0 && index < items.length) {
            selectedItemIndex = index;
        }
    }

    // Getters and setters with JavaDoc
    /**
     * Gets the game panel context.
     *
     * @return The GamePanel instance
     */
    public GamePanel getContext() { 
        return context;
    }

    /**
     * Gets the mana regeneration rate.
     *
     * @return The current mana regeneration rate
     */
    public float getManaRegenRate() { 
        return manaRegenRate;
    }

    /**
     * Gets the player's maximum health.
     *
     * @return The maximum health value
     */
    public int getMaxHealth() { 
        return maxHealth;
    }

    /**
     * Gets the player's maximum mana.
     *
     * @return The maximum mana value
     */
    public int getMaxMana() { 
        return maxMana;
    }

    /**
     * Gets the player's current mana.
     *
     * @return The current mana value
     */
    public int getMana() { 
        return currentMana;
    }

    /**
     * Sets the player's current mana.
     *
     * @param mana The new mana value
     */
    public void setMana(int mana) { 
        this.currentMana = mana;
    }

    /**
     * Gets the player's current mana.
     *
     * @return The current mana value
     */
    public int getCurrentMana() { 
        return currentMana;
    }

    /**
     * Gets the death overlay.
     *
     * @return The DeathOverlay instance
     */
    public DeathOverlay getDeathOverlay() { 
        return deathOverlay;
    }

    /**
     * Sets whether the player has a key.
     *
     * @param hasKey true if the player has a key, false otherwise
     */
    public void setKey(Boolean hasKey) {
        this.hasKey = hasKey;
    }

    /**
     * Removes the key from the player.
     */
    public void removeKey() {
        this.hasKey = false;
    }
}