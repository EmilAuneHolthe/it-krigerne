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
    public Inventory getInventory() {return inventory;}

    public void renderDeathOverlay(SpriteBatch batch) {
        if (isDead && deathOverlay != null) {
            deathOverlay.render(batch);
        }
    }
    @Override
    protected boolean isActive() {
        return !isDead;
    }
    public void ItemPickup(ItemType item){inventory.pickUpItem(item);}

    public void movePlayerReleased(KeyHandler keyHandler, Keys key) {
        movement.handleInputRelease(key);
        animation.setMoving(movement.isMoving());
        animation.setDirection(movement.getDirection());
    }
    public void increaseDamage(int damage) {
        this.damage += damage;
    }
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

    public void setCurrentMana(int mana) {
        this.currentMana = Math.min(Math.max(0, mana), maxMana);
    }

    public void setMaxMana(int maxMana) {
        this.maxMana = maxMana;
    }


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

    public void setManaRegenRate(float manaRegenRate) {
        this.manaRegenRate = manaRegenRate;
    }


    public void updateSwordHUDTexturePath(String Sword) {
            
            String swordHUDTexture = ItemType.getSwordHUDTexturePath(Sword);
           
            if (swordHUDTexture != null) {
                context.updateEquippedSwordHUD(swordHUDTexture);
            }
        }

    public boolean hasKey() {
        return hasKey;
    }


    public void removeItem(int index) {
        if (index >= 0 && index < items.length) {
            items[index] = null;
        }
    }

    public void selectItem(int index) {
        if (index >= 0 && index < items.length) {
            selectedItemIndex = index;
        }
    }



    public GamePanel getContext() { return context;}
    public float getManaRegenRate() { return manaRegenRate;}
    public int getMaxHealth() { return maxHealth;}
    public int getMaxMana() { return maxMana;}
    public int getMana() { return currentMana;}
    public void setMana(int mana) { this.currentMana = mana;}
    public int getCurrentMana() { return currentMana;}

    public DeathOverlay getDeathOverlay() { return deathOverlay;}

    public void setKey(Boolean hasKey) {
        this.hasKey = hasKey;
    }
    public void removeKey() {
        this.hasKey = false;
    }
}