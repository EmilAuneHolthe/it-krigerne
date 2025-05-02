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
 * Handles player-specific functionality including health, mana, inventory management,
 * combat interactions, and death handling. Extends GameEntity to inherit common entity functionality.
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
    private final Item[] items;
    private final Inventory inventory;
    
    public Player(GamePanel context, World world, Body body, int health, int damage, float x, float y, CharacterType characterType) {
        
        super(context, world, body, health, damage, characterType);
        isDead = false;
        deathOverlay = new DeathOverlay();
        this.movement = new PlayerMovement(context, world, body);
        this.currentMana = 100;
        this.maxMana = 100;
        maxHealth = health;
        items = new Item[4]; // 4 slots for items
        inventory = new Inventory(4, this, context, world);
    }
    /**
    * Renders a death overlay on the screen if the player is dead.
    * This method checks if the player is marked as dead and if a death overlay
    * is available. If both conditions are met, it renders the death overlay
    * using the provided SpriteBatch.
    *
    * @param batch The SpriteBatch used to draw the death overlay.
    */
    public void renderDeathOverlay(SpriteBatch batch) {
        if (isDead && deathOverlay != null) {
            deathOverlay.render(batch);
        }
    }
    @Override
    protected boolean isActive() {
        return !isDead;
    }
    /**
    * Picks up an item and adds it to the player's inventory.
    * @param item The type of item to be picked up
    */
    public void ItemPickup(ItemType item){inventory.pickUpItem(item);}
    
    /**
    * Handles the release of movement keys and updates the player's movement and animation state.
    * @param keyHandler The key handler managing input
    * @param key The key that was released
    */
    public void movePlayerReleased(KeyHandler keyHandler, Keys key) {
        movement.handleInputRelease(key);
        animation.setMoving(movement.isMoving());
        animation.setDirection(movement.getDirection());
    }
    /**
    * Increases the player's damage by the specified amount.
    * @param damage The amount to increase the player's damage by
    */
    public void increaseDamage(int damage) {
        this.damage += damage;
    }
    /**
    * Makes the player take damage from an enemy and handles death if health reaches zero.
    * @param enemy The enemy that is dealing damage to the player
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
    * Kills the player and triggers the death sequence.
    */
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
    
    @Override
    public float getX() {
        return body.getPosition().x * GamePanel.UNIT_SCALE;
    }
    
    @Override
    public float getY() {
        return body.getPosition().y * GamePanel.UNIT_SCALE;
    }
    
    /**
     * Disposes of all resources used by the player, including the death overlay.
     * This method should be called when the player is no longer needed to prevent memory leaks.
     */
    @Override
    public void dispose() {
        super.dispose();
        if (deathOverlay != null) {
            deathOverlay.dispose();
        }
    }
    
    public Vector2 getPosition() {
        return new Vector2(body.getPosition().x, body.getPosition().y);
    }
    /**
    * Sets the player's current mana value, ensuring it stays within valid bounds.
    * 
    * @param mana The new mana value to set
    */
    public void setCurrentMana(int mana) {
        this.currentMana = Math.min(Math.max(0, mana), maxMana);
    }
    /**
    * Sets the maximum mana capacity for the player.
    * @param maxMana The new maximum mana value
    */
    public void setMaxMana(int maxMana) {
        this.maxMana = maxMana;
    }
    
    /**
    * Regenerates the player's mana over time based on the regeneration rate.
    * Also updates the attack availability based on mana threshold.
    * 
    * @param deltaTime The time elapsed since the last update
    */
    public void regenerateMana(float deltaTime) {
        manaRegenAccumulator += manaRegenRate * deltaTime;
        if (manaRegenAccumulator >= 1.0f) {
            int manaToAdd = (int) manaRegenAccumulator;
            setCurrentMana(getCurrentMana() + manaToAdd);
            manaRegenAccumulator -= manaToAdd;
            
            canAttack = currentMana >= 30;
        }
    }
    /**
    * Sets the rate at which mana regenerates per second.
    * 
    * @param manaRegenRate The new mana regeneration rate
    */
    public void setManaRegenRate(float manaRegenRate) {
        this.manaRegenRate = manaRegenRate;
    }
    
    /**
    * Updates the sword HUD texture based on the equipped sword.
    * @param Sword The identifier of the sword to update the HUD for
    */
    public void updateSwordHUDTexturePath(String Sword) {
        String swordHUDTexture = ItemType.getSwordHUDTexturePath(Sword);
        
        if (swordHUDTexture != null) {
            context.updateEquippedSwordHUD(swordHUDTexture);
        }
    }
    
    /**
     * Checks if the player has a key.
     * @return True if the player has a key, false otherwise
     */
    public boolean hasKey() {
        return hasKey;
    }
    
    /**
    * Removes an item from the player's inventory at the specified index.
    * @param index The index of the item to remove
    */
    public void removeItem(int index) {
        if (index >= 0 && index < items.length) {
            items[index] = null;
        }
    }
    
    /**
    * Sets whether the player has a key.
    * @param hasKey True if the player has a key, false otherwise
    */
    public void setKey(Boolean hasKey) {
        this.hasKey = hasKey;
    }
    /**
    * Removes the key from the player's possession.
    */
    public void removeKey() {
        this.hasKey = false;
    }
    
    /**
     * Sets the player's mana to the specified value.
     * @param mana The new mana value to set
     */
    public void setMana(int mana) { this.currentMana = mana;}
    
    //Geter methods
    public GamePanel getContext() { return context;}
    public float getManaRegenRate() { return manaRegenRate;}
    public int getMaxHealth() { return maxHealth;}
    public int getMaxMana() { return maxMana;}
    public int getMana() { return currentMana;}
    public int getCurrentMana() { return currentMana;}
    public Inventory getInventory() {return inventory;}
    public DeathOverlay getDeathOverlay() { return deathOverlay;}
}